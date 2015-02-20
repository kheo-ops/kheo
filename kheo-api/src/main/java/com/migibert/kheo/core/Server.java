package com.migibert.kheo.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.migibert.kheo.configuration.ViewDetail;
import com.migibert.kheo.configuration.ViewList;
import com.migibert.kheo.core.plugin.ServerProperty;

public class Server {

    @JsonView({ ViewList.class, ViewDetail.class })
    @JsonProperty
    public String id;

    @JsonView({ ViewList.class, ViewDetail.class })
    @JsonProperty
    public String host;

    @JsonView({ ViewList.class, ViewDetail.class })
    public int sshPort;

    @JsonView({ ViewList.class, ViewDetail.class })
    @JsonProperty
    public String user;

    @JsonView({ ViewList.class, ViewDetail.class })
    @JsonProperty
    public String password;

    @JsonView({ ViewList.class, ViewDetail.class })
    @JsonProperty
    public String privateKey;

    @JsonView({ ViewList.class, ViewDetail.class })
    @JsonProperty
    public boolean sudo;

    @JsonView({ ViewDetail.class, ViewList.class })
    @JsonProperty
    public boolean sshConnectionValidity;

    @JsonView({ ViewDetail.class })
    @JsonProperty
    public int ram;

    @JsonView({ ViewDetail.class })
    @JsonProperty
    public int cpu;

    @JsonView({ ViewDetail.class })
    @JsonProperty
    public HashMap<String, Boolean> discoverySettings;

    @JsonView(ViewDetail.class)
    @JsonProperty
    public List<ServerProperty> serverProperties;

    @JsonView(ViewDetail.class)
    @JsonProperty
    public List<ServerEvent> eventLog;

    public Server() {
        this.id = "";
        this.host = "";
        this.user = "";
        this.password = "";
        this.sshPort = 22;
        this.privateKey = "";
        this.ram = 0;
        this.cpu = 0;
        this.discoverySettings = new HashMap<>();
        this.serverProperties = new ArrayList<ServerProperty>();
        this.eventLog = new ArrayList<>();
    }

    public Server(String host, String user, String password, String privateKey, int ram, int cpu) {
        this();
        this.id = UUID.randomUUID().toString();
        this.host = host;
        this.user = user;
        this.password = password;
        this.privateKey = privateKey;
        this.ram = ram;
        this.cpu = cpu;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}
