package com.migibert.kheo.core.plugin.network.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.migibert.kheo.core.plugin.ServerProperty;

public class ListeningProcessServerProperty extends ServerProperty {
	@JsonProperty
	public String programName;

	@JsonProperty
	public String pid;

	@JsonProperty
	public String port;

	@JsonProperty
	public String protocol;

	public ListeningProcessServerProperty() {
		this.programName = "";
		this.pid = "";
		this.port = "";
		this.protocol = "";
	}
}
