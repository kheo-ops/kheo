package com.migibert.kheo.core.plugin.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.migibert.kheo.core.plugin.ServerProperty;

public class ServiceServerProperty extends ServerProperty {
	@JsonProperty
	public String name;

	public ServiceServerProperty() {
		this.name = "";
	}
}
