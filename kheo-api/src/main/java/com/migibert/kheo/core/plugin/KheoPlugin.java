package com.migibert.kheo.core.plugin;

import java.util.List;

import com.migibert.kheo.core.AbstractSshCommand;

public interface KheoPlugin<P extends ServerProperty, C extends AbstractSshCommand<List<P>>, E extends EventGenerator<P>> {
	
	C getSshCommand();
	
	E getEventGenerator();
}
