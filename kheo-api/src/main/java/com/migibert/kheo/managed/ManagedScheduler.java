package com.migibert.kheo.managed;

import io.dropwizard.lifecycle.Managed;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class ManagedScheduler implements Managed {
	
	private Scheduler scheduler;
	
	@Override
	public void start() throws Exception {
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
	}

	@Override
	public void stop() throws Exception {
		scheduler.shutdown();
	}
}
