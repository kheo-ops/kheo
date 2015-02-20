package com.migibert.kheo.util;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.migibert.kheo.core.PluginDTO;
import com.migibert.kheo.core.plugin.KheoPlugin;
import com.migibert.kheo.core.plugin.ServerProperty;

public class KheoUtils {

    public static List<String> getPluginsNames(List<KheoPlugin<?>> plugins) {
        return Lists.transform(plugins, new Function<KheoPlugin<?>, String>() {
            @Override
            public String apply(KheoPlugin<?> plugin) {
                return plugin.getName() + plugin.getVersion();
            };
        });
    }

    public static List<PluginDTO> convertPluginsToPluginDTO(List<? extends KheoPlugin<? extends ServerProperty>> plugins) {
        return Lists.transform(plugins, new Function<KheoPlugin<?>, PluginDTO>() {
            @Override
            public PluginDTO apply(KheoPlugin<?> plugin) {
                return new PluginDTO(plugin);
            }
        });
    }

}
