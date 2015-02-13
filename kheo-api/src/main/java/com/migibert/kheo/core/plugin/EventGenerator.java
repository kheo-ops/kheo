package com.migibert.kheo.core.plugin;

import java.util.List;

import com.migibert.kheo.core.event.ServerEvent;

public interface EventGenerator<S extends ServerProperty> {
	List<ServerEvent> generateEvents(List<S> original, List<S> discovered);
	
	Class<S> getPropertyClass();
}
