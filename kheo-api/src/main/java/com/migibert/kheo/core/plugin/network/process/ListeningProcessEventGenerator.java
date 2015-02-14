package com.migibert.kheo.core.plugin.network.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.core.ServerEvent;
import com.migibert.kheo.core.plugin.AbstractEventGenerator;

public class ListeningProcessEventGenerator extends AbstractEventGenerator<ListeningProcessServerProperty> {

	private Logger logger = LoggerFactory.getLogger(ListeningProcessEventGenerator.class);

	@Override
	public List<ServerEvent> generateSpecificEvents(List<ListeningProcessServerProperty> original, List<ListeningProcessServerProperty> discovered) {
		List<ServerEvent> generatedEvents = new ArrayList<>();
		for (ListeningProcessServerProperty lp : original) {
			if (!discovered.contains(lp)) {
				logger.info("Listening process has been added! Generating event...");
				generatedEvents.add(new ServerEvent(ListeningProcessEventType.LISTENING_PROCESS_REMOVED.name(), null, lp));
			}
		}
		for (ListeningProcessServerProperty lp : discovered) {
			if (!original.contains(lp)) {
				logger.info("Listening has been added! Generating event...");
				generatedEvents.add(new ServerEvent(ListeningProcessEventType.LISTENING_PROCESS_ADDED.name(), null, lp));
			}
		}
		return generatedEvents;
	}

	@Override
	public Class<ListeningProcessServerProperty> getPropertyClass() {
		return ListeningProcessServerProperty.class;
	}
}
