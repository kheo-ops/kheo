package com.migibert.kheo.healtcheck;

import org.jongo.Jongo;

import com.codahale.metrics.health.HealthCheck;

public class MongoHealthcheck extends HealthCheck {

    private Jongo jongo;

    public MongoHealthcheck(Jongo jongo) {
        super();
        this.jongo = jongo;
    }

    @Override
    protected Result check() throws Exception {
        jongo.getDatabase().getCollectionNames();
        return Result.healthy();
    }
}
