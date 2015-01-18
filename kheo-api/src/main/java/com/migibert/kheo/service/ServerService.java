package com.migibert.kheo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.migibert.kheo.core.ListeningProcess;
import com.migibert.kheo.core.NetworkInterface;
import com.migibert.kheo.core.OperatingSystem;
import com.migibert.kheo.core.Server;
import com.migibert.kheo.core.Service;
import com.migibert.kheo.core.commands.IfconfigCommand;
import com.migibert.kheo.core.commands.NetstatCommand;
import com.migibert.kheo.core.commands.ServiceCommand;
import com.migibert.kheo.core.commands.UnameCommand;
import com.migibert.kheo.core.event.EventType;
import com.migibert.kheo.core.event.ServerEvent;
import com.migibert.kheo.exception.ServerAlreadyExistException;
import com.migibert.kheo.exception.ServerConnectionException;
import com.migibert.kheo.exception.ServerNotFoundException;

public class ServerService {

	private Logger logger = LoggerFactory.getLogger(ServerService.class);
	private MongoCollection serverCollection;

	private IfconfigCommand ifconfigCommand = new IfconfigCommand();
	private UnameCommand unameCommand = new UnameCommand();
	private ServiceCommand serviceCommand = new ServiceCommand();
	private NetstatCommand netstatCommand = new NetstatCommand();

	public ServerService(MongoCollection serverCollection) {
		this.serverCollection = serverCollection;
	}

	public void create(Server server) {
		if (exists(server.hostname)) {
			throw new ServerAlreadyExistException(server);
		}
		serverCollection.insert(server);
	}

	public Server read(String hostname) {
		return serverCollection.findOne("{hostname:#}", hostname).as(Server.class);
	}

	public List<Server> readAll() {
		return Lists.newArrayList(serverCollection.find().as(Server.class).iterator());
	}

	public void update(Server server) {
		serverCollection.update("{hostname:#}", server.hostname).with(server);
	}

	public void delete(String hostname) {
		if (!exists(hostname)) {
			throw new ServerNotFoundException(hostname);
		}
		serverCollection.remove("{hostname:#}", hostname);
	}

	public Server discover(Server server) {
		try {
			Server discovered = new Server(server.hostname, server.host, server.user, server.password, server.privateKey, server.ram, server.cpu);
			discovered.id = server.id;
			discovered.eventLog = new ArrayList<ServerEvent>(server.eventLog);

			logger.info("OS discovery");
			discovered.os = discoverOperatingSystem(server);

			logger.info("Network interfaces discovery");
			discovered.networkInterfaces = discoverNetworkInterfaces(server);

			logger.info("Running services discovery");
			discovered.services = discoverServices(server);

			logger.info("Listening processes discovery");
			discovered.listeningProcesses = discoverListeningProcesses(server);

			logger.info("Adding new event to server event log");
			discovered.eventLog.addAll(generateEvents(server, discovered));

			return discovered;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new ServerConnectionException(server.hostname);
		}
	}

	private boolean exists(String hostname) {
		return serverCollection.count("{hostname:#}", hostname) > 0;
	}

	private List<NetworkInterface> discoverNetworkInterfaces(Server server) throws IOException {
		return ifconfigCommand.executeAndParse(server);
	}

	private OperatingSystem discoverOperatingSystem(Server server) throws IOException {
		return unameCommand.executeAndParse(server);
	}

	private List<Service> discoverServices(Server server) throws IOException {
		return serviceCommand.executeAndParse(server);
	}

	private List<ListeningProcess> discoverListeningProcesses(Server server) throws IOException {
		return netstatCommand.executeAndParse(server);
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

	private List<ServerEvent> generateNetworkInterfacesEvents(List<NetworkInterface> original, List<NetworkInterface> discovered) {
		List<ServerEvent> generatedEvents = new ArrayList<>();

		boolean found = false;
		for (NetworkInterface oitf : original) {
			found = false;
			for (NetworkInterface ditf : discovered) {
				if (oitf.name.equals(ditf.name)) {
					found = true;
					if (!oitf.broadcast.equals(ditf.broadcast)) {
						generatedEvents.add(new ServerEvent(EventType.NETWORK_INTERFACE_BROADCAST_CHANGED, oitf.broadcast, ditf.broadcast));
					} else if (!oitf.encapsulationType.equals(ditf.encapsulationType)) {
						generatedEvents.add(new ServerEvent(EventType.NETWORK_INTERFACE_ENCAPSULATION_TYPE_CHANGED, oitf.encapsulationType,
								ditf.encapsulationType));
					} else if (!oitf.inet6Address.equals(ditf.inet6Address)) {
						generatedEvents.add(new ServerEvent(EventType.NETWORK_INTERFACE_INET6_ADDRESS_CHANGED, oitf.inet6Address, ditf.inet6Address));
					} else if (!oitf.inetAddress.equals(ditf.inetAddress)) {
						generatedEvents.add(new ServerEvent(EventType.NETWORK_INTERFACE_INET4_ADDRESS_CHANGED, oitf.inetAddress, ditf.inetAddress));
					} else if (!oitf.macAddress.equals(ditf.macAddress)) {
						generatedEvents.add(new ServerEvent(EventType.NETWORK_INTERFACE_MAC_ADDRESS_CHANGED, oitf.macAddress, ditf.macAddress));
					} else if (!oitf.mask.equals(ditf.mask)) {
						generatedEvents.add(new ServerEvent(EventType.NETWORK_INTERFACE_MASK_CHANGED, oitf.mask, ditf.mask));
					}
				}
			}
			if (!found) {
				logger.info("Interface has been removed! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.NETWORK_INTERFACE_REMOVED, oitf, null));
			}
		}

		for (NetworkInterface itf : discovered) {
			if (!original.contains(itf)) {
				logger.info("Interface has been added! Generating event...");
				generatedEvents.add(new ServerEvent(EventType.NETWORK_INTERFACE_ADDED, null, itf));
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
