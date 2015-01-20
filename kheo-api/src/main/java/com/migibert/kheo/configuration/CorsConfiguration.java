package com.migibert.kheo.configuration;

import org.hibernate.validator.constraints.NotEmpty;

public class CorsConfiguration {
	@NotEmpty
	public String allowedMethods = "GET,PUT,POST,DELETE,OPTIONS";

	@NotEmpty
	public String allowedOrigins = "*";

	@NotEmpty
	public String accessControlAllowOrigin = "*";

	@NotEmpty
	public String allowedHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";

	@NotEmpty
	public String allowCredentials = "true";
}
