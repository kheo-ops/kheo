package com.migibert.kheo.jobs;

import javax.ws.rs.client.Client;

import io.dropwizard.client.JerseyClientBuilder;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.migibert.kheo.managed.ManagedScheduler;

public class ConfigurationDiscoveryJob implements Job {

	private Client client;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
	}

}
