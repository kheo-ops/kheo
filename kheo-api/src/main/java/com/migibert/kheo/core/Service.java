package com.migibert.kheo.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Service {
	@JsonProperty
	public String name;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
