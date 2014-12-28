package com.migibert.kheo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class KheoApplication extends Application<KheoConfiguration> {

	public static void main(String[] args) throws Exception {
		new KheoApplication().run(args);
	}

	@Override
	public void run(KheoConfiguration arg0, Environment arg1) throws Exception {

	}

	@Override
	public String getName() {
		return "kheo";
	}
	
	@Override
	public void initialize(Bootstrap<KheoConfiguration> bootstrap) {
	}

}
