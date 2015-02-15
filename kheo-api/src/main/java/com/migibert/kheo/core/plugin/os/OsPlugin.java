package com.migibert.kheo.core.plugin.os;

import com.migibert.kheo.core.plugin.KheoPlugin;

public class OsPlugin implements KheoPlugin<OsServerProperty> {

	@Override
	public OsCommand getSshCommand() {
		return new OsCommand();
	}

	@Override
	public OsEventGenerator getEventGenerator() {
		return new OsEventGenerator();
	}
	
	@Override
	public String getName() {
		return OsPlugin.class.getSimpleName();
	}

}
