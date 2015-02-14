package com.migibert.kheo.core.plugin.network.process;

import com.migibert.kheo.core.plugin.KheoPlugin;

public class ListeningProcessPlugin implements KheoPlugin<ListeningProcessServerProperty, ListeningProcessCommand, ListeningProcessEventGenerator>{

	@Override
	public ListeningProcessCommand getSshCommand() {
		return new ListeningProcessCommand();
	}

	@Override
	public ListeningProcessEventGenerator getEventGenerator() {
		return new ListeningProcessEventGenerator();
	}

}
