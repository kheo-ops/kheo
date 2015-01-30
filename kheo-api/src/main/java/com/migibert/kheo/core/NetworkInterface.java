package com.migibert.kheo.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkInterface {

    @JsonProperty
    public String inetAddress;

    @JsonProperty
    public String inet6Address;

    @JsonProperty
    public String encapsulationType;

    @JsonProperty
    public String name;

    @JsonProperty
    public String broadcast;

    @JsonProperty
    public String mask;

    @JsonProperty
    public String macAddress;

    public NetworkInterface() {
        this("", "", "", "", "", "", "");
    }

    public NetworkInterface(String inetAddress, String inet6Address, String encapsulationType, String name, String broadcast, String mask,
            String macAddress) {
        this.inetAddress = inetAddress;
        this.inet6Address = inet6Address;
        this.encapsulationType = encapsulationType;
        this.name = name;
        this.broadcast = broadcast;
        this.mask = mask;
        this.macAddress = macAddress;
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
