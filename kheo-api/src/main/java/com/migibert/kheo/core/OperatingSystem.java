package com.migibert.kheo.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperatingSystem {

	@JsonProperty
	public String name;

	@JsonProperty
	public String kernelName;

	@JsonProperty
	public String kernelRelease;

	@JsonProperty
	public String hardwarePlatform;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hardwarePlatform == null) ? 0 : hardwarePlatform.hashCode());
		result = prime * result + ((kernelName == null) ? 0 : kernelName.hashCode());
		result = prime * result + ((kernelRelease == null) ? 0 : kernelRelease.hashCode());
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
		OperatingSystem other = (OperatingSystem) obj;
		if (hardwarePlatform == null) {
			if (other.hardwarePlatform != null)
				return false;
		} else if (!hardwarePlatform.equals(other.hardwarePlatform))
			return false;
		if (kernelName == null) {
			if (other.kernelName != null)
				return false;
		} else if (!kernelName.equals(other.kernelName))
			return false;
		if (kernelRelease == null) {
			if (other.kernelRelease != null)
				return false;
		} else if (!kernelRelease.equals(other.kernelRelease))
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
