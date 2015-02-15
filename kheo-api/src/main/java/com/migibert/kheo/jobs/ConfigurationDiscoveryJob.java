package com.migibert.kheo.jobs;

import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.core.Server;

public class ConfigurationDiscoveryJob implements Job {

	private Logger logger = LoggerFactory.getLogger(ConfigurationDiscoveryJob.class);
	private Client client = ClientBuilder.newClient();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Starting discovery job!");
		Response getServersResponse = client.target("http://localhost:8080").path("servers").request().accept(MediaType.APPLICATION_JSON).get();
		if (getServersResponse.getStatus() != Status.OK.getStatusCode()) {
			return;
		}

		Set<Server> servers = getServersResponse.readEntity(new GenericType<Set<Server>>() {
		});
		for (Server server : servers) {
			client.target("http://localhost:8080").path("servers/" + server.host + "/discover").request().get();
		}
		logger.info("Ending discovery job");
	}
}
