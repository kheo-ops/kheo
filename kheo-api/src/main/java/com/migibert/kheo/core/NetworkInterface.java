package com.migibert.kheo.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkInterface {
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

	public String macAddress;

	public NetworkInterface() {
		this("", "", "", "", "", "", "");
	}

	public NetworkInterface(String inetAddress, String inet6Address, String encapsulationType, String name, String broadcast, String mask,
			String macAddress) {
		this.inetAddress = inetAddress;
		this.inet6Address = inet6Address;
		this.encapsulationType = encapsulationType;
		this.name = name;
		this.broadcast = broadcast;
		this.mask = mask;
		this.macAddress = macAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((broadcast == null) ? 0 : broadcast.hashCode());
		result = prime * result + ((encapsulationType == null) ? 0 : encapsulationType.hashCode());
		result = prime * result + ((inet6Address == null) ? 0 : inet6Address.hashCode());
		result = prime * result + ((inetAddress == null) ? 0 : inetAddress.hashCode());
		result = prime * result + ((mask == null) ? 0 : mask.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		NetworkInterface other = (NetworkInterface) obj;
		if (broadcast == null) {
			if (other.broadcast != null)
				return false;
		} else if (!broadcast.equals(other.broadcast))
			return false;
		if (encapsulationType == null) {
			if (other.encapsulationType != null)
				return false;
		} else if (!encapsulationType.equals(other.encapsulationType))
			return false;
		if (inet6Address == null) {
			if (other.inet6Address != null)
				return false;
		} else if (!inet6Address.equals(other.inet6Address))
			return false;
		if (inetAddress == null) {
			if (other.inetAddress != null)
				return false;
		} else if (!inetAddress.equals(other.inetAddress))
			return false;
		if (mask == null) {
			if (other.mask != null)
				return false;
		} else if (!mask.equals(other.mask))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
