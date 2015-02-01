package com.migibert.kheo.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscoverySettings {
	
	@JsonProperty
	public boolean discoverListeningProcesses = true;
	
	@JsonProperty
	public boolean discoverNetworkInterfaces = true;
	
	@JsonProperty
	public boolean discoverOperatingSystem = true;
	
	@JsonProperty
	public boolean discoverServices = true;
}
