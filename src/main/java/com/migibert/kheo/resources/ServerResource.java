package com.migibert.kheo.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.migibert.kheo.core.Server;

@Path("servers")
@Produces(MediaType.APPLICATION_JSON)
public class ServerResource {
	
	private List<Server> serverStore = new ArrayList<>();

	@GET
	@Timed
	public List<Server> getServers() {
		return serverStore;
	}
	
	@GET
	@Timed
	@Path("/{hostname}")
	public Server getServer(@PathParam("hostname") String hostname) {
		for(Server server : serverStore) {
			if(hostname.equals(server.getHostname())) {
				return server;
			}
		}
		return null;
	}
	
	@POST
	@Timed
	public void createServer(Server server) {
		serverStore.add(server);
	}
	
	@PUT
	@Timed
	@Path("/{hostname}")
	public void updateServer(@PathParam("hostname") String hostname, Server server) {
		serverStore.remove(getServer(hostname));
		serverStore.add(server);
	}
	
	@DELETE
	@Timed
	@Path("/{hostname}")
	public void deleteServer(@PathParam("hostname") String hostname, Server server) {
		serverStore.remove(getServer(hostname));
	}
	
	
}
