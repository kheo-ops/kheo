package com.migibert.kheo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.migibert.kheo.exception.mapping.ServerAlreadyExistExceptionMapper;
import com.migibert.kheo.exception.mapping.ServerNotFoundExceptionMapper;
import com.migibert.kheo.healtcheck.MongoHealtcheck;
import com.migibert.kheo.managed.ManagedMongo;
import com.migibert.kheo.managed.ManagedScheduler;
import com.migibert.kheo.resources.ServerResource;

public class KheoApplication extends Application<KheoConfiguration> {

	public static void main(String[] args) throws Exception {
		new KheoApplication().run(args);
	}

	@Override
	public void run(KheoConfiguration configuration, Environment environment) throws Exception {

		ManagedMongo managedMongo = new ManagedMongo(configuration.mongo);
		ManagedScheduler managedScheduler = new ManagedScheduler();
				
		environment.lifecycle().manage(managedMongo);
		environment.lifecycle().manage(managedScheduler);

		environment.jersey().register(ServerAlreadyExistExceptionMapper.class);
		environment.jersey().register(ServerNotFoundExceptionMapper.class);
		environment.jersey().register(new ServerResource(managedMongo.getJongo().getCollection(configuration.mongo.serverCollection)));

		environment.healthChecks().register("Mongo connection", new MongoHealtcheck(managedMongo.getJongo()));
	}

	@Override
	public String getName() {
		return "kheo";
	}

	@Override
	public void initialize(Bootstrap<KheoConfiguration> bootstrap) {
	}

}
