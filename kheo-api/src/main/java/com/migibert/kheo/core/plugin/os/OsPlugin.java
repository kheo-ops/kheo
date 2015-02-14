package com.migibert.kheo.core.plugin.os;

import com.migibert.kheo.core.plugin.KheoPlugin;

public class OsPlugin implements KheoPlugin<OsServerProperty, OsCommand, OsEventGenerator>{

	@Override
	public OsCommand getSshCommand() {
		return new OsCommand();
	}

	@Override
	public OsEventGenerator getEventGenerator() {
		return new OsEventGenerator();
	}

}
