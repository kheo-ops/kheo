package com.migibert.kheo;

import com.migibert.kheo.resources.ServerResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class KheoApplication extends Application<KheoConfiguration> {

	public static void main(String[] args) throws Exception {
		new KheoApplication().run(args);
	}

	@Override
	public void run(KheoConfiguration configuration, Environment environment) throws Exception {
		environment.jersey().register(new ServerResource());
	}

	@Override
	public String getName() {
		return "kheo";
	}
	
	@Override
	public void initialize(Bootstrap<KheoConfiguration> bootstrap) {
	}

}
