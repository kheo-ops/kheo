package com.migibert.kheo.service;

import java.io.IOException;
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
import com.migibert.kheo.exception.ServerAlreadyExistException;
import com.migibert.kheo.exception.ServerConnectionException;
import com.migibert.kheo.exception.ServerNotFoundException;

public class ServerService {

	private Logger logger = LoggerFactory.getLogger(ServerService.class);
    private MongoCollection collection;

    private IfconfigCommand ifconfigCommand = new IfconfigCommand();
    private UnameCommand unameCommand = new UnameCommand();
    private ServiceCommand serviceCommand = new ServiceCommand();
    private NetstatCommand netstatCommand = new NetstatCommand();
    
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
        	logger.info("OS discovery");
            server.os = discoverOperatingSystem(server);
            
            logger.info("Network interfaces discovery");
            server.networkInterfaces = discoverNetworkInterfaces(server);
            
            logger.info("Running services discovery");
            server.services = discoverServices(server);
            
            logger.info("Listening processes discovery");
            server.listeningProcesses = discoverListeningProcesses(server);
            return server;
        } catch (IOException e) {
        	logger.error(e.getMessage(), e);
            throw new ServerConnectionException(server.hostname);
        }
    }

    private boolean exists(String hostname) {
        return collection.count("{hostname:#}", hostname) > 0;
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
}
