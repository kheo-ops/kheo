package com.migibert.kheo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.migibert.kheo.core.plugin.KheoPlugin;
import com.migibert.kheo.core.plugin.ServerProperty;

@Path("plugins")
public class PluginResource {
	private List<? extends KheoPlugin<? extends ServerProperty>> plugins;
	
	public PluginResource(List<? extends KheoPlugin<? extends ServerProperty>> plugins) {
		this.plugins = plugins;
	}
	
	@GET
	public Response listPlugins() {
		return Response.status(Status.OK.getStatusCode()).entity(plugins).build();
	}
	
}
