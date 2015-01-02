package com.migibert.kheo.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Server {

	@JsonProperty
	private String hostname;

	@JsonProperty
	private int ram;

	@JsonProperty
	private int cpu;

	@JsonProperty
	private List<NetworkInterface> networkInterfaces;

	public Server() {
		this("", 0, 0, new ArrayList<NetworkInterface>());
	}

	public Server(String hostname, int ram, int cpu, List<NetworkInterface> networkInterfaces) {
		this.hostname = hostname;
		this.ram = ram;
		this.cpu = cpu;
		this.networkInterfaces = networkInterfaces;
	}

	public int getCpu() {
		return cpu;
	}

	public int getRam() {
		return ram;
	}

	public List<NetworkInterface> getNetworkInterfaces() {
		return new ArrayList<NetworkInterface>(networkInterfaces);
	}

	public String getHostname() {
		return hostname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cpu;
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((networkInterfaces == null) ? 0 : networkInterfaces.hashCode());
		result = prime * result + ram;
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
		if (networkInterfaces == null) {
			if (other.networkInterfaces != null)
				return false;
		} else if (!networkInterfaces.equals(other.networkInterfaces))
			return false;
		if (ram != other.ram)
			return false;
		return true;
	}

}
