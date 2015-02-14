package com.migibert.kheo.core.plugin.services;

import com.migibert.kheo.core.plugin.KheoPlugin;

public class ServicePlugin implements KheoPlugin<ServiceServerProperty, ServiceCommand, ServiceEventGenerator>{

	@Override
	public ServiceCommand getSshCommand() {
		return new ServiceCommand();
	}

	@Override
	public ServiceEventGenerator getEventGenerator() {
		return new ServiceEventGenerator();
	}

}
