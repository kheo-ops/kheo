package com.migibert.kheo.service;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.migibert.kheo.core.ListeningProcess;
import com.migibert.kheo.core.OperatingSystem;
import com.migibert.kheo.core.Server;
import com.migibert.kheo.core.Service;
import com.migibert.kheo.core.commands.AbstractSshCommand;
import com.migibert.kheo.core.commands.NetstatCommand;
import com.migibert.kheo.core.commands.ServiceCommand;
import com.migibert.kheo.core.commands.UnameCommand;
import com.migibert.kheo.core.event.EventType;
import com.migibert.kheo.core.event.ServerEvent;
import com.migibert.kheo.core.plugin.EventGenerator;
import com.migibert.kheo.core.plugin.KheoPlugin;
import com.migibert.kheo.core.plugin.ServerProperty;
import com.migibert.kheo.exception.ServerAlreadyExistException;
import com.migibert.kheo.exception.ServerConnectionException;
import com.migibert.kheo.exception.ServerNotFoundException;

public class ServerService {

	private Logger logger = LoggerFactory.getLogger(ServerService.class);
	private MongoCollection serverCollection;

	private List<KheoPlugin<ServerProperty, AbstractSshCommand<List<ServerProperty>>, EventGenerator<ServerProperty>>> plugins;

	private UnameCommand unameCommand = new UnameCommand();
	private ServiceCommand serviceCommand = new ServiceCommand();
	private NetstatCommand netstatCommand = new NetstatCommand();

	public ServerService(MongoCollection serverCollection,
			List<KheoPlugin<ServerProperty, AbstractSshCommand<List<ServerProperty>>, EventGenerator<ServerProperty>>> plugins) {
		this.serverCollection = serverCollection;
		this.plugins = plugins;
	}

	public void create(Server server) {
		if (exists(server.host)) {
			throw new ServerAlreadyExistException(server);
		}

		logger.info("Adding server {}", server.host);
		serverCollection.insert(server);

		logger.info("Initializing server {} data with first discovery", server.host);
		Server discoveredServer = discover(server, true);
		update(discoveredServer);
	}

	public Server read(String host) {
		return serverCollection.findOne("{host:#}", host).as(Server.class);
	}

	public List<Server> readAll() {
		return Lists.newArrayList(serverCollection.find().as(Server.class).iterator());
	}

	public void update(Server server) {
		serverCollection.update("{host:#}", server.host).with(server);
	}

	public void delete(String host) {
		if (!exists(host)) {
			throw new ServerNotFoundException(host);
		}
		serverCollection.remove("{host:#}", host);
	}

	public Server discover(Server server, boolean firstDiscovery) {
		try {
			Server discovered = new Server(server.host, server.user, server.password, server.privateKey, server.ram, server.cpu);
			discovered.sshPort = server.sshPort;
			discovered.sudo = server.sudo;
			discovered.sshConnectionValidity = false;
			discovered.eventLog = new ArrayList<ServerEvent>(server.eventLog);
			discovered.discoverySettings = server.discoverySettings;

			logger.info("OS discovery for server {}", server.host);
			discovered.os = discoverOperatingSystem(server);

			for (KheoPlugin<ServerProperty, AbstractSshCommand<List<ServerProperty>>, EventGenerator<ServerProperty>> plugin : plugins) {
				discovered.serverProperties.addAll(plugin.getSshCommand().executeAndParse(server));
				if (firstDiscovery) {
					logger.info("First discovery for {}, no event generation", server.host);
				} else {
					logger.info("Adding new event to server event log");
					List<ServerProperty> serverProperties = new ArrayList<ServerProperty>(Collections2.filter(server.serverProperties, Predicates.instanceOf(plugin.getEventGenerator().getPropertyClass())));
					List<ServerProperty> discoveredProperties = new ArrayList<ServerProperty>(Collections2.filter(discovered.serverProperties, Predicates.instanceOf(plugin.getEventGenerator().getPropertyClass())));
					List<ServerEvent> events = plugin.getEventGenerator().generateEvents(serverProperties, discoveredProperties);
					discovered.eventLog.addAll(events);
				}
			}

			logger.info("Running services discovery for server {}", server.host);
			discovered.services = discoverServices(server);

			logger.info("Listening processes discovery for server {}", server.host);
			discovered.listeningProcesses = discoverListeningProcesses(server);

			discovered.sshConnectionValidity = true;
			return discovered;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new ServerConnectionException(server.host);
		}
	}

	private boolean exists(String host) {
		return serverCollection.count("{host:#}", host) > 0;
	}

	private OperatingSystem discoverOperatingSystem(Server server) throws IOException {
		if (server.discoverySettings.discoverOperatingSystem) {
			return unameCommand.executeAndParse(server);
		}
		return new OperatingSystem();
	}

	private List<Service> discoverServices(Server server) throws IOException {
		if (server.discoverySettings.discoverServices) {
			return serviceCommand.executeAndParse(server);
		}
		return new ArrayList<>();
	}

	private List<ListeningProcess> discoverListeningProcesses(Server server) throws IOException {
		if (server.discoverySettings.discoverListeningProcesses) {
			return netstatCommand.executeAndParse(server);
		}
		return new ArrayList<>();
	}

	private List<ServerEvent> generateEvents(Server original, Server discovered) {
		List<ServerEvent> generatedEvents = new ArrayList<>();

		generatedEvents.addAll(generateOsEvents(original.os, discovered.os));
		generatedEvents.addAll(generateNetworkInterfacesEvents(original.networkInterfaces, discovered.networkInterfaces));
		generatedEvents.addAll(generateServicesEvents(original.services, discovered.services));
		generatedEvents.addAll(generateListeningProcessesEvents(original.listeningProcesses, discovered.listeningProcesses));

		return generatedEvents;
	}

	private List<ServerEvent> generateOsEvents(OperatingSystem original, OperatingSystem discovered) {
		List<ServerEvent> generatedEvents = new ArrayList<>();

		if (!original.equals(discovered)) {
			if (!original.hardwarePlatform.equals(discovered.hardwarePlatform)) {
				logger.info("OS Hardware platform changed! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.OS_HARDWARE_PLATFORM_CHANGED, original.hardwarePlatform, discovered.hardwarePlatform));
			}
			if (!original.kernelName.equals(discovered.kernelName)) {
				logger.info("OS Kernel name changed! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.OS_KERNEL_NAME_CHANGED, original.kernelName, discovered.kernelName));
			}
			if (!original.kernelRelease.equals(discovered.kernelRelease)) {
				logger.info("OS Kernel release changed! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.OS_KERNEL_RELEASE_CHANGED, original.kernelRelease, discovered.kernelRelease));
			}
			if (!original.name.equals(discovered.name)) {
				logger.info("OS name platform changed! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.OS_NAME_CHANGED, original.name, discovered.name));
			}
		}
		return generatedEvents;
	}

	private List<ServerEvent> generateServicesEvents(List<Service> original, List<Service> discovered) {
		List<ServerEvent> generatedEvents = new ArrayList<>();

		for (Service svc : original) {
			if (!discovered.contains(svc)) {
				logger.info("Service has been removed! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.SERVICE_REMOVED, svc, null));
			}
		}

		for (Service svc : discovered) {
			if (!original.contains(svc)) {
				logger.info("Service has been added! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.SERVICE_ADDED, null, svc));
			}
		}

		return generatedEvents;
	}

	private List<ServerEvent> generateListeningProcessesEvents(List<ListeningProcess> original, List<ListeningProcess> discovered) {
		List<ServerEvent> generatedEvents = new ArrayList<>();

		for (ListeningProcess lp : original) {
			if (!discovered.contains(lp)) {
				logger.info("Listening process has been added! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.LISTENING_PROCESS_REMOVED, null, lp));
			}
		}

		for (ListeningProcess lp : discovered) {
			if (!original.contains(lp)) {
				logger.info("Listening has been added! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.LISTENING_PROCESS_ADDED, null, lp));
			}
		}

		return generatedEvents;
	}
}
