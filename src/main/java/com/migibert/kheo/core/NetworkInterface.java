package com.migibert.kheo.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkInterface {
	@JsonProperty
	private final String inetAddress;
	
	@JsonProperty
	private final String inet6Address;
	
	@JsonProperty
	private final String encapsulationType;
	
	public NetworkInterface() {
		this.inetAddress = "";
		this.inet6Address = "";
		this.encapsulationType = "";
	}
	
	public NetworkInterface(String inetAddress, String inet6Address, String encapsulationType) {
		this.inetAddress = inetAddress;
		this.inet6Address = inet6Address;
		this.encapsulationType = encapsulationType;
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
}
