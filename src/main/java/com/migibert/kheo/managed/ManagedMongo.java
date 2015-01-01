package com.migibert.kheo.managed;

import io.dropwizard.lifecycle.Managed;

import com.mongodb.Mongo;

public class ManagedMongo implements Managed {

	private Mongo mongo;
	
	public ManagedMongo(Mongo mongo) {
		this.mongo = mongo;
	}
	
	@Override
	public void start() throws Exception {
	}
	
	@Override
	public void stop() throws Exception {
		this.mongo.close();
	}
	
}
