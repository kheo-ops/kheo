package com.migibert.kheo.jobs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SpecificConfigurationDiscoveryJob implements Job {

    private Logger logger = LoggerFactory.getLogger(GlobalConfigurationDiscoveryJob.class);
    private Client client = ClientBuilder.newClient();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Starting specific configuration discovery job");
        if(!context.getJobDetail().getJobDataMap().containsKey("host")) {
            logger.warn("Specific configuration discovery job needs a host");
            return;
        }
        String host = context.getJobDetail().getJobDataMap().getString("host");
        client.target("http://localhost:8080").path("servers/" + host + "/discover").request().get();        
        logger.info("Ending specific configuration discovery job");
    }
}