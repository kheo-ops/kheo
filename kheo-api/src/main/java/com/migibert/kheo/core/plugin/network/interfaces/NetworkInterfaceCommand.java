package com.migibert.kheo.core.plugin.network.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import com.migibert.kheo.core.commands.AbstractSshCommand;

public class NetworkInterfaceCommand extends AbstractSshCommand<List<NetworkInterfaceServerProperty>> {
	private static final String BROADCAST_TOKEN = "Bcast:";
	private static final String ENCAPSULATION_TYPE_TOKEN = "Link encap:";
	private static final String INET6_ADDR_TOKEN = "inet6 addr:";
	private static final String INET_ADDR_TOKEN = "inet addr:";
	private static final String HWADDR_TOKEN = "HWaddr";
	private static final String MASK_TOKEN = "Mask:";

	public NetworkInterfaceCommand() {
		super("ifconfig -a");
	}

	@Override
	public List<NetworkInterfaceServerProperty> parse(String result) {
		List<NetworkInterfaceServerProperty> interfaces = new ArrayList<>();
		String[] interfacesData = result.split("\n\n");
		for (String interfaceData : interfacesData) {
			interfaces.add(parseInterface(interfaceData));
		}
		return interfaces;
	}

	private NetworkInterfaceServerProperty parseInterface(String interfaceData) {
		NetworkInterfaceServerProperty result = new NetworkInterfaceServerProperty();
		int firstIndexOfSpace = interfaceData.indexOf(" ");
		result.name = interfaceData.substring(0, firstIndexOfSpace);

		String networkInterfaceData = interfaceData.substring(firstIndexOfSpace);
		for (String data : networkInterfaceData.split("\n")) {
			String[] lineProperties = data.trim().split("  ");
			for (String property : lineProperties) {
				result.broadcast = extractPropertyIfNull(property, BROADCAST_TOKEN, result.broadcast);
				result.encapsulationType = extractPropertyIfNull(property, ENCAPSULATION_TYPE_TOKEN, result.encapsulationType);
				result.inet6Address = extractPropertyIfNull(property, INET6_ADDR_TOKEN, result.inet6Address);
				result.inetAddress = extractPropertyIfNull(property, INET_ADDR_TOKEN, result.inetAddress);
				result.macAddress = extractPropertyIfNull(property, HWADDR_TOKEN, result.macAddress);
				result.mask = extractPropertyIfNull(property, MASK_TOKEN, result.mask);
			}
		}
		return result;
	}

	private static String extractPropertyIfNull(String data, String propertyKey, String actualValue) {

		if (!Strings.isNullOrEmpty(actualValue)) {
			return actualValue;
		}
		int propertyIndex = data.lastIndexOf(propertyKey);
		if (propertyIndex != -1) {
			return data.replace(propertyKey, "").trim();
		}
		return "";
	}

}
