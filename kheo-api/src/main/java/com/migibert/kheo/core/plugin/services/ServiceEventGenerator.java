package com.migibert.kheo.core.plugin.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.core.ServerEvent;
import com.migibert.kheo.core.plugin.EventGenerator;

public class ServiceEventGenerator implements EventGenerator<ServiceServerProperty> {

	private Logger logger = LoggerFactory.getLogger(ServiceEventGenerator.class);

	@Override
	public List<ServerEvent> generateEvents(List<ServiceServerProperty> original, List<ServiceServerProperty> discovered) {
		List<ServerEvent> generatedEvents = new ArrayList<>();

		for (ServiceServerProperty svc : original) {
			if (!discovered.contains(svc)) {
				logger.info("Service has been removed! Generating event...");
				generatedEvents.add(new ServerEvent(ServiceEventType.SERVICE_REMOVED.name(), svc, null));
			}
		}

		for (ServiceServerProperty svc : discovered) {
			if (!original.contains(svc)) {
				logger.info("Service has been added! Generating event...");
				generatedEvents.add(new ServerEvent(ServiceEventType.SERVICE_ADDED.name(), null, svc));
			}
		}

		return generatedEvents;

	}

	@Override
	public Class<ServiceServerProperty> getPropertyClass() {
		return ServiceServerProperty.class;
	}
}
