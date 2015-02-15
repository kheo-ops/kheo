package com.migibert.kheo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.migibert.kheo.core.plugin.KheoPlugin;
import com.migibert.kheo.core.plugin.ServerProperty;

public class DiscoveryResource {
	
	private List<String> settings;

	public DiscoveryResource(List<? extends KheoPlugin<? extends ServerProperty>> plugins) {
		 settings = Lists.transform(plugins, new Function<KheoPlugin<?>, String>() {
			@Override
			public String apply(KheoPlugin<?> plugin) {
				return plugin.getName();
			}
		});
	}
	
	@GET
	public Response listDiscoverySettings() {
		return Response.status(Status.OK.getStatusCode()).entity(settings).build();
	}
}
