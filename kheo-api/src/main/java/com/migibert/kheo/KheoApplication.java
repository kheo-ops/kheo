package com.migibert.kheo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.quartz.impl.StdSchedulerFactory;

import com.google.common.base.Joiner;
import com.migibert.kheo.core.plugin.KheoPlugin;
import com.migibert.kheo.core.plugin.KheoPluginLoader;
import com.migibert.kheo.core.plugin.ServerProperty;
import com.migibert.kheo.exception.mapping.ServerAlreadyExistExceptionMapper;
import com.migibert.kheo.exception.mapping.ServerNotFoundExceptionMapper;
import com.migibert.kheo.healtcheck.MongoHealthcheck;
import com.migibert.kheo.healtcheck.SchedulerHealthcheck;
import com.migibert.kheo.jobs.GlobalConfigurationDiscoveryJob;
import com.migibert.kheo.managed.ManagedMongo;
import com.migibert.kheo.managed.ManagedScheduler;
import com.migibert.kheo.resources.PluginResource;
import com.migibert.kheo.resources.ServerResource;

public class KheoApplication extends Application<KheoConfiguration> {

	public static void main(String[] args) throws Exception {
		new KheoApplication().run(args);
	}

	@Override
	public void run(KheoConfiguration configuration, Environment environment) throws Exception {

		ManagedMongo managedMongo = new ManagedMongo(configuration.mongo);
		ManagedScheduler managedScheduler = new ManagedScheduler(StdSchedulerFactory.getDefaultScheduler());

		Dynamic filter = environment.servlets().addFilter("cors", new CrossOriginFilter());
		filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		String allowedOrigins = configuration.cors.allowedOrigins.contains("all") ? "*" : Joiner.on(",").join(configuration.cors.allowedOrigins);
		filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, allowedOrigins);
		filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, Joiner.on(",").join(configuration.cors.allowedMethods));		
		String accessControlAllowOrigin = configuration.cors.accessControlAllowOrigin.equals("all") ? "*" : configuration.cors.accessControlAllowOrigin;
		filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, accessControlAllowOrigin);
		filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, Joiner.on(",").join(configuration.cors.allowedHeaders));
		filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, configuration.cors.allowCredentials);

		environment.lifecycle().manage(managedMongo);
		environment.lifecycle().manage(managedScheduler);

		environment.jersey().register(ServerAlreadyExistExceptionMapper.class);
		environment.jersey().register(ServerNotFoundExceptionMapper.class);				
		
		List<KheoPlugin<? extends ServerProperty>> plugins = KheoPluginLoader.loadKheoPlugins(configuration.plugin);
		environment.jersey().register(new ServerResource(managedMongo.getJongo().getCollection(configuration.mongo.serverCollection), managedScheduler, plugins));
		environment.jersey().register(new PluginResource(plugins));

		environment.healthChecks().register("Mongo connection", new MongoHealthcheck(managedMongo.getJongo()));
		environment.healthChecks().register("Scheduler", new SchedulerHealthcheck(managedScheduler.getScheduler()));

		managedScheduler.registerJob(configuration.scheduler.cronExpression, GlobalConfigurationDiscoveryJob.class);
	}

	@Override
	public String getName() {
		return "kheo";
	}

	@Override
	public void initialize(Bootstrap<KheoConfiguration> bootstrap) {
	}
}
