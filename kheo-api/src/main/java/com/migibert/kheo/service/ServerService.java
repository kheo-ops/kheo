package com.migibert.kheo.service;

import java.io.IOException;
import java.util.List;

import org.jongo.MongoCollection;

import com.google.common.collect.Lists;
import com.migibert.kheo.core.NetworkInterface;
import com.migibert.kheo.core.OperatingSystem;
import com.migibert.kheo.core.Server;
import com.migibert.kheo.exception.ServerAlreadyExistException;
import com.migibert.kheo.exception.ServerConnectionException;
import com.migibert.kheo.exception.ServerNotFoundException;
import com.migibert.kheo.util.IfconfigCommand;
import com.migibert.kheo.util.UnameCommand;

public class ServerService {

    private MongoCollection collection;

    private IfconfigCommand ifconfigCommand = new IfconfigCommand();
    private UnameCommand unameCommand = new UnameCommand();

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

    public Server discover(Server server) {
        try {
            server.os = discoverOperatingSystem(server);
            server.networkInterfaces = discoverNetworkInterfaces(server);
            return server;
        } catch (IOException e) {
            throw new ServerConnectionException(server.hostname);
        }
    }

    private boolean exists(String hostname) {
        return collection.count("{hostname:#}", hostname) > 0;
    }

    private List<NetworkInterface> discoverNetworkInterfaces(Server server) throws IOException {
        return ifconfigCommand.executeAndParse(server, "ifconfig -a");
    }

    private OperatingSystem discoverOperatingSystem(Server server) throws IOException {
        return unameCommand.executeAndParse(server, "uname -srio");
    }
}
