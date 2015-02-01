package com.migibert.kheo.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscoverySettings {
	
	@JsonProperty
	public boolean discoverListeningProcesses;
	
	@JsonProperty
	public boolean discoverNetworkInterfaces;
	
	@JsonProperty
	public boolean discoverOperatingSystem;
	
	@JsonProperty
	public boolean discoverServices;
}
