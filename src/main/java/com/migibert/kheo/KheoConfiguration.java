package com.migibert.kheo;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class KheoConfiguration extends Configuration {
	
	@JsonProperty
	public String dbHost;
	
	@JsonProperty
	public String dbPort;
	
	@JsonProperty
	public String dbName;
	
}
