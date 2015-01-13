package com.migibert.kheo.healtcheck;

import org.quartz.Scheduler;

import com.codahale.metrics.health.HealthCheck;

public class SchedulerHealthcheck extends HealthCheck {

	private Scheduler scheduler;

	public SchedulerHealthcheck(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	protected Result check() throws Exception {
		if (scheduler.isShutdown()) {
			return Result.unhealthy("Scheduler is shutdown");
		}
		return Result.healthy();
	}

}
