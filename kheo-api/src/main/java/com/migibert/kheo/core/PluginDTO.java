package com.migibert.kheo.core;

import com.migibert.kheo.core.plugin.KheoPlugin;

public class PluginDTO {
	public String name;
	public String propertyName;
	public String version;

	public PluginDTO(KheoPlugin<?> plugin) {
		name = plugin.getName();
		propertyName = plugin.getPropertyName();
		version = plugin.getVersion();
	}
}
