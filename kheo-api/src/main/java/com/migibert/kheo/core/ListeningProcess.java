package com.migibert.kheo.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListeningProcess {

    @JsonProperty
    public String programName;

    @JsonProperty
    public String pid;

    @JsonProperty
    public String port;

    @JsonProperty
    public String protocol;

    public ListeningProcess() {
        this.programName = "";
        this.pid = "";
        this.port = "";
        this.protocol = "";
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
