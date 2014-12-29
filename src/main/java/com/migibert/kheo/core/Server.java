package com.migibert.kheo.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Server {

	@JsonProperty
	private final int ram;

	@JsonProperty
	private final int cpu;

	@JsonProperty
	private final List<NetworkInterface> networkInterfaces;

	public Server() {
		this.ram = 0;
		this.cpu = 0;
		this.networkInterfaces = new ArrayList<>();
	}

	public Server(int ram, int cpu, List<NetworkInterface> networkInterfaces) {
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
}
