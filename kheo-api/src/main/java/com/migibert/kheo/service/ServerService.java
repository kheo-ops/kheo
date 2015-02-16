package com.migibert.kheo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.migibert.kheo.core.Server;
import com.migibert.kheo.core.ServerEvent;
import com.migibert.kheo.core.plugin.KheoPlugin;
import com.migibert.kheo.core.plugin.ServerProperty;
import com.migibert.kheo.exception.ServerAlreadyExistException;
import com.migibert.kheo.exception.ServerConnectionException;
import com.migibert.kheo.exception.ServerNotFoundException;
import com.migibert.kheo.util.KheoPluginClassLoader;

public class ServerService {

	private Logger logger = LoggerFactory.getLogger(ServerService.class);
	private MongoCollection serverCollection;

	private List<KheoPlugin<? extends ServerProperty>> plugins;

	public ServerService(MongoCollection serverCollection, List<KheoPlugin<? extends ServerProperty>> plugins) {
		this.serverCollection = serverCollection;
		this.plugins = plugins;
	}

	public void create(Server server) {
		if (exists(server.host)) {
			throw new ServerAlreadyExistException(server);
		}

		List<String> pluginNames = getPluginsNames();
		for (String pluginName : pluginNames) {
			if (!server.discoverySettings.containsKey(pluginName)) {
				server.discoverySettings.put(pluginName, true);
			}
		}

		logger.info("Adding server {}", server.host);
		serverCollection.insert(server);

		logger.info("Initializing server {} data with first discovery", server.host);
		discover(server, true);
	}

	public Server read(String host) {
		Thread.currentThread().setContextClassLoader(KheoPluginClassLoader.getInstance());
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

			for (KheoPlugin<? extends ServerProperty> plugin : plugins) {
				if (server.discoverySettings.containsKey(plugin.getName()) && server.discoverySettings.get(plugin.getName())) {
					logger.info("{} discovery has been disabled", plugin.getName());
					discovered.serverProperties.addAll(plugin.getSshCommand().executeAndParse(server));
					if (firstDiscovery) {
						logger.info("First discovery for {}, no event generation", server.host);
					} else {
						List<ServerProperty> serverProperties = new ArrayList<ServerProperty>(Collections2.filter(server.serverProperties,
								Predicates.instanceOf(plugin.getEventGenerator().getPropertyClass())));
						List<ServerProperty> discoveredProperties = new ArrayList<ServerProperty>(Collections2.filter(discovered.serverProperties,
								Predicates.instanceOf(plugin.getEventGenerator().getPropertyClass())));
						List<ServerEvent> events = plugin.getEventGenerator().generateEvents(serverProperties, discoveredProperties);
						discovered.eventLog.addAll(events);
					}
				}
			}

			discovered.sshConnectionValidity = true;
			update(discovered);
			return discovered;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new ServerConnectionException(server.host);
		}
	}

	private boolean exists(String host) {
		return serverCollection.count("{host:#}", host) > 0;
	}

	public List<String> getPluginsNames() {
		return Lists.transform(plugins, new Function<KheoPlugin<?>, String>() {
			@Override
			public String apply(KheoPlugin<?> plugin) {
				return plugin.getName();
			}
		});
	}
}
