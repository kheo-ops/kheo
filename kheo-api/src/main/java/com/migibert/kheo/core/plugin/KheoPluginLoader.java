package com.migibert.kheo.core.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.MyClassLoader;
import com.migibert.kheo.configuration.PluginConfiguration;

public class KheoPluginLoader {
	private static final Logger logger = LoggerFactory.getLogger(KheoPluginLoader.class);

	public static List<KheoPlugin<? extends ServerProperty>> loadKheoPlugins(PluginConfiguration pluginConfiguration) {
		File pluginDirectory = new File(pluginConfiguration.directory);
		File[] plugins = pluginDirectory.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".jar");
			}
		});

		URL[] urls = new URL[plugins.length];
		for(int i=0 ;i<plugins.length; i++) {
			try {
				urls[i] = plugins[i].toURI().toURL();	
			} catch(MalformedURLException e) {
				logger.error(e.getMessage(), e);
			}
			
		}
		URLClassLoader classLoader = new URLClassLoader(urls, KheoPluginLoader.class.getClassLoader());
		MyClassLoader.instance = classLoader;
		List<KheoPlugin<?>> result = new ArrayList<KheoPlugin<?>>();
		for (int i = 0; i < plugins.length; i++) {
			try(JarFile plugin = new JarFile(plugins[i])) {				
				Enumeration<JarEntry> entries = plugin.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					if(entry.getName().endsWith(".class")) {
						String tmp = entry.getName().substring(0, entry.getName().length() - 6).replaceAll("/", ".");
						Class<?> clazz = Class.forName(tmp, true, classLoader);
						if(KheoPlugin.class.isAssignableFrom(clazz)) {
							result.add((KheoPlugin<?>) clazz.getConstructor().newInstance());
						}
					}
				}
			} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				logger.warn("Unable to load plugin at {}", plugins[i].getAbsolutePath());
				e.printStackTrace();
			}
		}

		logger.info("Plugin loaded : {}", result.toString());
		return result;
	}
}
