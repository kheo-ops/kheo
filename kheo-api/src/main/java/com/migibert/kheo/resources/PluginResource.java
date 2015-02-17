package com.migibert.kheo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.migibert.kheo.core.PluginDTO;
import com.migibert.kheo.core.plugin.KheoPlugin;
import com.migibert.kheo.core.plugin.ServerProperty;

@Path("plugins")
@Produces(MediaType.APPLICATION_JSON)
public class PluginResource {
	private List<? extends KheoPlugin<? extends ServerProperty>> plugins;
	
	public PluginResource(List<? extends KheoPlugin<? extends ServerProperty>> plugins) {
		this.plugins = plugins;
	}
	
	@GET
	public Response listPlugins() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<PluginDTO> pluginsName = Lists.transform(plugins, new Function<KheoPlugin<?>, PluginDTO>() {
				@Override
				public PluginDTO apply(KheoPlugin<?> plugin) {
					return new PluginDTO(plugin);
				}
			});
			String value = mapper.writeValueAsString(pluginsName);
			return Response.status(Status.OK.getStatusCode()).entity(value).build();
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}		
	}
	
}
