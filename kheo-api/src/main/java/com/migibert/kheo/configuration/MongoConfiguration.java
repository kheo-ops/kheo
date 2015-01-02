package com.migibert.kheo.configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MongoConfiguration {
	@NotNull
	public String host = "localhost";

	@Min(1)
	@Max(65535)
	public int port = 27017;

	@NotNull
	public String db = "kheo";
	
	@NotNull
	public String serverCollection = "servers";
	
}