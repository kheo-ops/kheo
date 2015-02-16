package com.migibert.kheo.util;

import java.net.URL;
import java.net.URLClassLoader;

public class KheoPluginClassLoader {
	private static URLClassLoader instance;
	
	private KheoPluginClassLoader() {}
	
	public static URLClassLoader getInstance(URL[] urls) {
		if(instance == null) {
			instance = new URLClassLoader(urls);
		}
		return instance;
	}
	
	public static URLClassLoader getInstance() {
		if(instance == null) {
			throw new NullPointerException();
		}
		return instance;
	}

}
