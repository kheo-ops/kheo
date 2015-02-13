package com.migibert.kheo.core.plugin.network.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.core.event.ServerEvent;
import com.migibert.kheo.core.plugin.EventGenerator;

public class NetworkInterfaceEventGenerator implements EventGenerator<NetworkInterfaceServerProperty> {
	
	private Logger logger = LoggerFactory.getLogger(NetworkInterfaceEventGenerator.class);

	@Override
	public List<ServerEvent> generateEvents(List<NetworkInterfaceServerProperty> original, List<NetworkInterfaceServerProperty> discovered) {
		List<ServerEvent> generatedEvents = new ArrayList<>();

		boolean found = false;
		for (NetworkInterfaceServerProperty oitf : original) {
			found = false;
			for (NetworkInterfaceServerProperty ditf : discovered) {
				if (oitf.name.equals(ditf.name)) {
					found = true;
					if (!oitf.broadcast.equals(ditf.broadcast)) {
						generatedEvents.add(new ServerEvent(NetworkInterfaceEventType.NETWORK_INTERFACE_BROADCAST_CHANGED.name(), oitf.broadcast, ditf.broadcast));
					} else if (!oitf.encapsulationType.equals(ditf.encapsulationType)) {
						generatedEvents.add(new ServerEvent(NetworkInterfaceEventType.NETWORK_INTERFACE_ENCAPSULATION_TYPE_CHANGED.name(), oitf.encapsulationType,
								ditf.encapsulationType));
					} else if (!oitf.inet6Address.equals(ditf.inet6Address)) {
						generatedEvents.add(new ServerEvent(NetworkInterfaceEventType.NETWORK_INTERFACE_INET6_ADDRESS_CHANGED.name(), oitf.inet6Address, ditf.inet6Address));
					} else if (!oitf.inetAddress.equals(ditf.inetAddress)) {
						generatedEvents.add(new ServerEvent(NetworkInterfaceEventType.NETWORK_INTERFACE_INET4_ADDRESS_CHANGED.name(), oitf.inetAddress, ditf.inetAddress));
					} else if (!oitf.macAddress.equals(ditf.macAddress)) {
						generatedEvents.add(new ServerEvent(NetworkInterfaceEventType.NETWORK_INTERFACE_MAC_ADDRESS_CHANGED.name(), oitf.macAddress, ditf.macAddress));
					} else if (!oitf.mask.equals(ditf.mask)) {
						generatedEvents.add(new ServerEvent(NetworkInterfaceEventType.NETWORK_INTERFACE_MASK_CHANGED.name(), oitf.mask, ditf.mask));
					}
				}
			}
			if (!found) {
				logger.info("Interface has been removed! Generating event...");
				generatedEvents.add(new ServerEvent(NetworkInterfaceEventType.NETWORK_INTERFACE_REMOVED.name(), oitf, null));
			}
		}

		for (NetworkInterfaceServerProperty itf : discovered) {
			if (!original.contains(itf)) {
				logger.info("Interface has been added! Generating event...");
				generatedEvents.add(new ServerEvent(NetworkInterfaceEventType.NETWORK_INTERFACE_ADDED.name(), null, itf));
			}
		}

		return generatedEvents;
	}

	@Override
	public Class<NetworkInterfaceServerProperty> getPropertyClass() {
		return NetworkInterfaceServerProperty.class;
	}
}
