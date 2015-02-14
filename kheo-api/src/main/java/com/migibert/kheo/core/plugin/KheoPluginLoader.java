package com.migibert.kheo.core.plugin;

import java.util.ArrayList;
import java.util.List;

import com.migibert.kheo.configuration.PluginConfiguration;
import com.migibert.kheo.core.commands.AbstractSshCommand;

public class KheoPluginLoader {

	public static List<KheoPlugin<ServerProperty, AbstractSshCommand<List<ServerProperty>>, EventGenerator<ServerProperty>>> loadKheoPlugins(PluginConfiguration pluginConfiguration) {
		return new ArrayList<>();
	}
}
