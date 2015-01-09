package com.migibert.kheo.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.migibert.kheo.client.SshClient;
import com.migibert.kheo.util.IfconfigCommand;
import com.migibert.kheo.util.SshCommand;
import com.migibert.kheo.util.UnameCommand;

import de.flapdoodle.embed.mongo.config.Net;

public class Server {

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


    public Server() {
        this("", "", "", "", "", 0, 0, new ArrayList<NetworkInterface>());
    }

    public Server(String hostname, String host, String user, String password, String privateKey, int ram, int cpu,
            List<NetworkInterface> networkInterfaces) {
        this.hostname = hostname;
        this.host = host;
        this.user = user;
        this.password = password;
        this.privateKey = privateKey;
        this.ram = ram;
        this.cpu = cpu;
        this.networkInterfaces = networkInterfaces;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cpu;
        result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((networkInterfaces == null) ? 0 : networkInterfaces.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((privateKey == null) ? 0 : privateKey.hashCode());
        result = prime * result + ram;
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
        if (hostname == null) {
            if (other.hostname != null)
                return false;
        } else if (!hostname.equals(other.hostname))
            return false;
        if (host == null) {
            if (other.host != null)
                return false;
        } else if (!host.equals(other.host))
            return false;
        if (networkInterfaces == null) {
            if (other.networkInterfaces != null)
                return false;
        } else if (!networkInterfaces.equals(other.networkInterfaces))
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
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}
