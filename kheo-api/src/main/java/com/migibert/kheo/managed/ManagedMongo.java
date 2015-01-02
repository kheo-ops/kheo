package com.migibert.kheo.managed;

import io.dropwizard.lifecycle.Managed;

import org.jongo.Jongo;

import com.migibert.kheo.configuration.MongoConfiguration;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class ManagedMongo implements Managed {

	private Mongo mongo;
	private DB db;
	private Jongo jongo;

	public ManagedMongo(MongoConfiguration configuration) throws Exception {
		this.mongo = new MongoClient(configuration.host, configuration.port);
		this.db = mongo.getDB(configuration.db);
		this.jongo = new Jongo(db);
	}

	@Override
	public void start() throws Exception {
	}

	@Override
	public void stop() throws Exception {
		this.mongo.close();
	}

	public Jongo getJongo() {
		return jongo;
	}
}
