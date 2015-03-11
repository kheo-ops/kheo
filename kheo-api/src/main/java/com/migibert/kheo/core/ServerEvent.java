package com.migibert.kheo.core;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.migibert.kheo.util.DateJsonDeserializer;
import com.migibert.kheo.util.DateJsonSerializer;

public class ServerEvent {

    @JsonSerialize(using = DateJsonSerializer.class)
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonProperty
    public Date date;

    @JsonProperty
    public String eventType;

    @JsonProperty
    public Object oldValue;

    @JsonProperty
    public Object newValue;

    public ServerEvent() {
    }

    public ServerEvent(String eventType, Object oldValue, Object newValue) {
        this.date = new Date();
        this.eventType = eventType;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
        result = prime * result + ((newValue == null) ? 0 : newValue.hashCode());
        result = prime * result + ((oldValue == null) ? 0 : oldValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServerEvent other = (ServerEvent) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (eventType == null) {
            if (other.eventType != null)
                return false;
        } else if (!eventType.equals(other.eventType))
            return false;
        if (newValue == null) {
            if (other.newValue != null)
                return false;
        } else if (!newValue.equals(other.newValue))
            return false;
        if (oldValue == null) {
            if (other.oldValue != null)
                return false;
        } else if (!oldValue.equals(other.oldValue))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
