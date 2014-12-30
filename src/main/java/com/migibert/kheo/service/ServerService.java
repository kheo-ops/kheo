package com.migibert.kheo.service;

import java.util.ArrayList;
import java.util.List;

import com.migibert.kheo.core.Server;
import com.migibert.kheo.exception.ServerAlreadyExistException;
import com.migibert.kheo.exception.ServerNotFoundException;

public class ServerService {
	private static ServerService instance = new ServerService();
	private List<Server> serverStore = new ArrayList<>();

	private ServerService() {
	}

	public static ServerService getInstance() {
		return instance;
	}

	public void create(Server server) {
		if (exists(server.getHostname())) {
			throw new ServerAlreadyExistException(server);
		}
		serverStore.add(server);
	}

	public Server read(String hostname) {
		for (Server server : serverStore) {
			if (hostname.equals(server.getHostname())) {
				return server;
			}
		}
		return null;
	}

	public List<Server> readAll() {
		return serverStore;
	}

	public void update(String hostname, Server server) {
		delete(hostname);
		create(server);
	}

	public void delete(String hostname) {
		if (!exists(hostname)) {
			throw new ServerNotFoundException(hostname);
		}
		serverStore.remove(read(hostname));
	}

	private boolean exists(String hostname) {
		return read(hostname) != null;
	}
}
