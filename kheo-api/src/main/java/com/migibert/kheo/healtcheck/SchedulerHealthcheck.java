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
        return scheduler.isStarted() ? Result.healthy() : Result.unhealthy("Scheduler is not started");
    }

}
