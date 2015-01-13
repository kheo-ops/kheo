package com.migibert.kheo.jobs;

import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.migibert.kheo.core.Server;

public class ConfigurationDiscoveryJob implements Job {

	private Client client = ClientBuilder.newClient();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Response getServersResponse = client.target("http://localhost:8080").path("servers").request().accept(MediaType.APPLICATION_JSON).get();
		if(getServersResponse.getStatus() != Status.OK.getStatusCode()) {
			return;
		}
		
		Set<Server> servers = getServersResponse.readEntity(new GenericType<Set<Server>>(){});
		for(Server server : servers) {
			Response discoveryResponse = client.target("http://localhost:8080").path("servers/" + server.hostname + "/discover").request().get();
			if(discoveryResponse.getStatus() == Status.OK.getStatusCode()) {
				Server discoveredServer = discoveryResponse.readEntity(Server.class);
				client.target("http://localhost:8080").path("servers/" + server.hostname).request().put(Entity.entity(discoveredServer, MediaType.APPLICATION_JSON));
			}
		}
	}
}
