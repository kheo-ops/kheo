package com.migibert.kheo;

import io.dropwizard.Configuration;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.migibert.kheo.configuration.CorsConfiguration;
import com.migibert.kheo.configuration.MongoConfiguration;
import com.migibert.kheo.configuration.SchedulerConfiguration;

public class KheoConfiguration extends Configuration {

	@JsonProperty
	@Valid
	public MongoConfiguration mongo = new MongoConfiguration();
	
	@JsonProperty
	@Valid
	public SchedulerConfiguration scheduler = new SchedulerConfiguration();

	@JsonProperty
	@Valid
	public CorsConfiguration cors = new CorsConfiguration();
}
