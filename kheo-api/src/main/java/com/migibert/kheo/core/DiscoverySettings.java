package com.migibert.kheo.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscoverySettings {
	
	@JsonProperty
	public Map<String, Boolean> pluginSettings = new HashMap<>();
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
