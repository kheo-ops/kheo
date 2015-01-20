package com.migibert.kheo.configuration;

import java.util.List;

import jersey.repackaged.com.google.common.collect.Lists;

import org.hibernate.validator.constraints.NotEmpty;

public class CorsConfiguration {
	@NotEmpty
	public List<String> allowedMethods = Lists.newArrayList("GET", "PUT", "POST", "DELETE", "OPTIONS");

	@NotEmpty
	public List<String> allowedOrigins = Lists.newArrayList("*");

	@NotEmpty
	public String accessControlAllowOrigin = "*";

	@NotEmpty
	public List<String> allowedHeaders = Lists
			.newArrayList("Content-Type", "Authorization", "X-Requested-With", "Content-Length", "Accept", "Origin");

	@NotEmpty
	public String allowCredentials = "true";
}
