package com.migibert.kheo.core.plugin.network.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.core.event.ServerEvent;
import com.migibert.kheo.core.plugin.EventGenerator;

public class ListeningProcessEventGenerator implements EventGenerator<ListeningProcessServerProperty> {

	private Logger logger = LoggerFactory.getLogger(ListeningProcessEventGenerator.class);

	@Override
	public List<ServerEvent> generateEvents(List<ListeningProcessServerProperty> original, List<ListeningProcessServerProperty> discovered) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<ListeningProcessServerProperty> getPropertyClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
