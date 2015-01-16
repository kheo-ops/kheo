package com.migibert.kheo.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.migibert.kheo.core.event.ServerEvent;

public class Server {

    @JsonProperty
    public String id;

    @JsonProperty
    public String hostname;

    @JsonProperty
    public String host;

    @JsonProperty
    public String user;

    @JsonProperty
    public String password;

    @JsonProperty
    public String privateKey;

    @JsonProperty
    public int ram;

    @JsonProperty
    public int cpu;

    @JsonProperty
    public OperatingSystem os;

    @JsonProperty
    public List<NetworkInterface> networkInterfaces;

    @JsonProperty
    public List<Service> services;

    @JsonProperty
    public List<ListeningProcess> listeningProcesses;

    @JsonProperty
    public List<ServerEvent> eventLog;

    public Server() {
        this.id = "";
        this.hostname = "";
        this.host = "";
        this.user = "";
        this.password = "";
        this.privateKey = "";
        this.ram = 0;
        this.cpu = 0;
        this.os = new OperatingSystem();
        this.networkInterfaces = new ArrayList<>();
        this.services = new ArrayList<>();
        this.listeningProcesses = new ArrayList<>();
        this.eventLog = new ArrayList<>();
    }

    public Server(String hostname, String host, String user, String password, String privateKey, int ram, int cpu) {
        this();
        this.id = UUID.randomUUID().toString();
        this.hostname = hostname;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cpu;
        result = prime * result + ((eventLog == null) ? 0 : eventLog.hashCode());
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((listeningProcesses == null) ? 0 : listeningProcesses.hashCode());
        result = prime * result + ((networkInterfaces == null) ? 0 : networkInterfaces.hashCode());
        result = prime * result + ((os == null) ? 0 : os.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((privateKey == null) ? 0 : privateKey.hashCode());
        result = prime * result + ram;
        result = prime * result + ((services == null) ? 0 : services.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        Server other = (Server) obj;
        if (cpu != other.cpu)
            return false;
        if (eventLog == null) {
            if (other.eventLog != null)
                return false;
        } else if (!eventLog.equals(other.eventLog))
            return false;
        if (host == null) {
            if (other.host != null)
                return false;
        } else if (!host.equals(other.host))
            return false;
        if (hostname == null) {
            if (other.hostname != null)
                return false;
        } else if (!hostname.equals(other.hostname))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (listeningProcesses == null) {
            if (other.listeningProcesses != null)
                return false;
        } else if (!listeningProcesses.equals(other.listeningProcesses))
            return false;
        if (networkInterfaces == null) {
            if (other.networkInterfaces != null)
                return false;
        } else if (!networkInterfaces.equals(other.networkInterfaces))
            return false;
        if (os == null) {
            if (other.os != null)
                return false;
        } else if (!os.equals(other.os))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (privateKey == null) {
            if (other.privateKey != null)
                return false;
        } else if (!privateKey.equals(other.privateKey))
            return false;
        if (ram != other.ram)
            return false;
        if (services == null) {
            if (other.services != null)
                return false;
        } else if (!services.equals(other.services))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}
