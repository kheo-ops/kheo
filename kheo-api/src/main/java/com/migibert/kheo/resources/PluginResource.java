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
import com.migibert.kheo.core.PluginDTO;
import com.migibert.kheo.core.plugin.KheoPlugin;
import com.migibert.kheo.core.plugin.ServerProperty;
import com.migibert.kheo.util.KheoUtils;

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
            List<PluginDTO> pluginsDto = KheoUtils.convertPluginsToPluginDTO(plugins);
            String value = mapper.writeValueAsString(pluginsDto);
            return Response.status(Status.OK.getStatusCode()).entity(value).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
