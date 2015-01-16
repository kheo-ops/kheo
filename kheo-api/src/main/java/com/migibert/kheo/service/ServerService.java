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
        return new ArrayList<>();
    }
}
