package com.migibert.kheo.core.plugin.os;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.migibert.kheo.core.plugin.ServerProperty;

public class OsServerProperty extends ServerProperty {
	@JsonProperty
	public String name;

	@JsonProperty
	public String kernelName;

	@JsonProperty
	public String kernelRelease;

	@JsonProperty
	public String hardwarePlatform;

	public OsServerProperty() {
		this.name = "";
		this.kernelName = "";
		this.kernelRelease = "";
		this.hardwarePlatform = "";
	}
}
