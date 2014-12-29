package com.migibert.kheo.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Server {

	@JsonProperty
	private final String hostname;

	@JsonProperty
	private final int ram;

	@JsonProperty
	private final int cpu;

	@JsonProperty
	private final List<NetworkInterface> networkInterfaces;

	public Server() {
		this.hostname = "";
		this.ram = 0;
		this.cpu = 0;
		this.networkInterfaces = new ArrayList<>();
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
}
