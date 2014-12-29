package com.migibert.kheo.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkInterface {
	@JsonProperty
	private final String inetAddress;

	@JsonProperty
	private final String inet6Address;

	@JsonProperty
	private final String encapsulationType;

	@JsonProperty
	private final String name;

	@JsonProperty
	private final String broadcast;

	@JsonProperty
	private final String mask;

	public NetworkInterface() {
		this.inetAddress = "";
		this.inet6Address = "";
		this.encapsulationType = "";
		this.name = "";
		this.broadcast = "";
		this.mask = "";
	}

	public NetworkInterface(String inetAddress, String inet6Address, String encapsulationType, String name,
			String broadcast, String mask) {
		this.inetAddress = inetAddress;
		this.inet6Address = inet6Address;
		this.encapsulationType = encapsulationType;
		this.name = name;
		this.broadcast = broadcast;
		this.mask = mask;
	}

	public String getEncapsulationType() {
		return encapsulationType;
	}

	public String getInet6Address() {
		return inet6Address;
	}

	public String getInetAddress() {
		return inetAddress;
	}

	public String getName() {
		return name;
	}

	public String getBroadcast() {
		return broadcast;
	}

	public String getMask() {
		return mask;
	}
}
