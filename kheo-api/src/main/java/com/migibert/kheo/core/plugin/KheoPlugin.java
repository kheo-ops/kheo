package com.migibert.kheo.core.plugin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.migibert.kheo.core.AbstractSshCommand;

public interface KheoPlugin<P extends ServerProperty> {
	
	AbstractSshCommand<List<P>> getSshCommand();
	
	AbstractEventGenerator<P> getEventGenerator();

	@JsonProperty
	String getName();
	
	@JsonProperty
	String getPropertyName();
	
	@JsonProperty
	String getVersion();
}
