package com.migibert.kheo.healtcheck;

import org.jongo.Jongo;

import com.codahale.metrics.health.HealthCheck;

public class MongoHealtcheck extends HealthCheck {

	private Jongo jongo;

	public MongoHealtcheck(Jongo jongo) {
		super();
		this.jongo = jongo;
	}

	@Override
	protected Result check() throws Exception {
		jongo.getDatabase().getCollectionNames();
		return Result.healthy();
	}
}
