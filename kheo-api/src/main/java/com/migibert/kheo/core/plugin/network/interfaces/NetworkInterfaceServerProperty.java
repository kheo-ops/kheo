package com.migibert.kheo.core.plugin.network.interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.migibert.kheo.core.plugin.ServerProperty;

public class NetworkInterfaceServerProperty extends ServerProperty {
	@JsonProperty
	public String inetAddress;

	@JsonProperty
	public String inet6Address;

	@JsonProperty
	public String encapsulationType;

	@JsonProperty
	public String name;

	@JsonProperty
	public String broadcast;

	@JsonProperty
	public String mask;

	@JsonProperty
	public String macAddress;

	public NetworkInterfaceServerProperty() {
		this("", "", "", "", "", "", "");
	}

	public NetworkInterfaceServerProperty(String inetAddress, String inet6Address, String encapsulationType, String name, String broadcast,
			String mask, String macAddress) {
		this.inetAddress = inetAddress;
		this.inet6Address = inet6Address;
		this.encapsulationType = encapsulationType;
		this.name = name;
		this.broadcast = broadcast;
		this.mask = mask;
		this.macAddress = macAddress;
	}

}
