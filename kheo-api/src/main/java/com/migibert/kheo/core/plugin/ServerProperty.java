package com.migibert.kheo.core.plugin;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@kheo-class")
public abstract class ServerProperty {

	public String type = getClass().getSimpleName();
	
	public String key = getKey();
	
	@JsonProperty("@kheo-type")
	public String kheoType = getKheoType().name();
	
	public abstract String getKey();
	
	public abstract KheoType getKheoType();

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
