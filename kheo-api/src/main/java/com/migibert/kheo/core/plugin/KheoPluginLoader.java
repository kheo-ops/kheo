package com.migibert.kheo.core.plugin;

import java.util.ArrayList;

import org.assertj.core.util.Lists;

import com.migibert.kheo.configuration.PluginConfiguration;
import com.migibert.kheo.core.plugin.network.interfaces.NetworkInterfacePlugin;
import com.migibert.kheo.core.plugin.network.process.ListeningProcessPlugin;
import com.migibert.kheo.core.plugin.os.OsPlugin;
import com.migibert.kheo.core.plugin.services.ServicePlugin;

public class KheoPluginLoader {

	public static ArrayList<KheoPlugin<? extends ServerProperty>> loadKheoPlugins(PluginConfiguration pluginConfiguration) {
		return Lists.newArrayList(new OsPlugin(), new ListeningProcessPlugin(), new ServicePlugin(), new NetworkInterfacePlugin());
	}
}
