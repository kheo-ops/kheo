package com.migibert.kheo.service;

import java.util.List;

import org.jongo.MongoCollection;

import com.google.common.collect.Lists;
import com.migibert.kheo.core.NetworkInterface;
import com.migibert.kheo.core.Server;
import com.migibert.kheo.exception.ServerAlreadyExistException;
import com.migibert.kheo.exception.ServerNotFoundException;

public class ServerService {
	private MongoCollection collection;

	public ServerService(MongoCollection collection) {
		this.collection = collection;	
	}

	public void create(Server server) {
		if (exists(server.hostname)) {
			throw new ServerAlreadyExistException(server);
		}
		collection.insert(server);
	}

	public Server read(String hostname) {
		return collection.findOne("{hostname:#}", hostname).as(Server.class);
	}

	public List<Server> readAll() {
		return Lists.newArrayList(collection.find().as(Server.class).iterator());
	}

	public void update(Server server) {
		collection.update("{hostname:#}", server.hostname).with(server);
	}

	public void delete(String hostname) {
		if (!exists(hostname)) {
			throw new ServerNotFoundException(hostname);
		}
		collection.remove("{hostname:#}", hostname);
	}
		
	private boolean exists(String hostname) {
		return collection.count("{hostname:#}", hostname) > 0;
	}
	
	private List<NetworkInterface> findNetworkInterfaces(String hostname) {
		Server server = read(hostname);
		return null;
	}
}
