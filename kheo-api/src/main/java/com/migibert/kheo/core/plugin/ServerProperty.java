package com.migibert.kheo.core.plugin;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ServerProperty {
	
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
