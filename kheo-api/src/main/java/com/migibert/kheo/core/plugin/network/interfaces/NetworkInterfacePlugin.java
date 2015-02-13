package com.migibert.kheo.core.plugin.network.interfaces;

import com.migibert.kheo.core.plugin.KheoPlugin;

public class NetworkInterfacePlugin implements KheoPlugin<NetworkInterfaceServerProperty, NetworkInterfaceCommand, NetworkInterfaceEventGenerator>{

	@Override
	public NetworkInterfaceCommand getSshCommand() {
		return new NetworkInterfaceCommand();
	}

	@Override
	public NetworkInterfaceEventGenerator getEventGenerator() {
		return new NetworkInterfaceEventGenerator();
	}

}
