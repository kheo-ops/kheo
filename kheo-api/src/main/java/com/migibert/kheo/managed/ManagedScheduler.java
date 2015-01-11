package com.migibert.kheo.managed;

import io.dropwizard.lifecycle.Managed;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.migibert.kheo.jobs.ConfigurationDiscoveryJob;

public class ManagedScheduler implements Managed {

	private Scheduler scheduler;

	@Override
	public void start() throws Exception {
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		registerJobs();
	}

	@Override
	public void stop() throws Exception {
		scheduler.shutdown();
	}

	private void registerJobs() {
		JobDetail job = newJob(ConfigurationDiscoveryJob.class).build();
		Trigger trigger = newTrigger().withSchedule(simpleSchedule().withIntervalInHours(24)).build();
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
