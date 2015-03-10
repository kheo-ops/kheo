package com.migibert.kheo.core.plugin;

import java.util.List;

import com.migibert.kheo.core.ServerEvent;

public interface EventGenerator<S extends ServerProperty> {

    List<ServerEvent> generateEvents(List<ServerProperty> original, List<ServerProperty> discovered);

    Class<S> getPropertyClass();
}
