package com.migibert.kheo.configuration;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class PluginConfiguration {
	
	@NotNull
	@NotEmpty
	public String pluginDirectory = ".kheo";

}
