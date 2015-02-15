package com.migibert.kheo.core.plugin;

import java.util.List;

import com.migibert.kheo.core.AbstractSshCommand;

public interface KheoPlugin<P extends ServerProperty> {
	
	AbstractSshCommand<List<P>> getSshCommand();
	
	AbstractEventGenerator<P> getEventGenerator();

	String getName();
}
