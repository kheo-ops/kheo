package com.migibert.kheo.rules;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.rules.ExternalResource;

import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class MongoRule extends ExternalResource {

	private MongodProcess mongod;
	private MongoClient mongo;
	private int port;

	public MongoRule(int port) {
		this.port = port;
	}

	@Override
	protected void before() throws Throwable {
		MongodStarter starter = MongodStarter.getDefaultInstance();

		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(port, Network.localhostIsIPv6())).build();

		MongodExecutable mongodExecutable = null;
		mongodExecutable = starter.prepare(mongodConfig);
		mongod = mongodExecutable.start();
		mongo = new MongoClient("localhost", port);
	}

	@Override
	protected void after() {
		mongo.close();
		mongod.stop();
	}

	public MongoCollection getServerCollection() {
		return new Jongo(mongo.getDB("kheo")).getCollection("servers");
	}
}
