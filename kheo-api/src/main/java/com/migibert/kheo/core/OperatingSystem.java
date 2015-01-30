package com.migibert.kheo.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperatingSystem {

    @JsonProperty
    public String name;

    @JsonProperty
    public String kernelName;

    @JsonProperty
    public String kernelRelease;

    @JsonProperty
    public String hardwarePlatform;

    public OperatingSystem() {
        this.name = "";
        this.kernelName = "";
        this.kernelRelease = "";
        this.hardwarePlatform = "";
    }
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
