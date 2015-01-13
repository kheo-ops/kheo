package com.migibert.kheo.managed;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import io.dropwizard.lifecycle.Managed;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.jobs.ConfigurationDiscoveryJob;

public class ManagedScheduler implements Managed {

	private Scheduler scheduler;
	private Logger logger = LoggerFactory.getLogger(ManagedScheduler.class);

	public ManagedScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void start() throws Exception {
		scheduler.start();
	}

	@Override
	public void stop() throws Exception {
		scheduler.shutdown();
	}

	public void registerJobs() {
		JobDetail job = newJob(ConfigurationDiscoveryJob.class).build();
		Trigger trigger = newTrigger().withSchedule(simpleSchedule().withIntervalInMinutes(5).repeatForever()).build();
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
}
