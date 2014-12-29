package com.migibert.kheo.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import com.migibert.kheo.core.NetworkInterface;
import com.migibert.kheo.core.Server;

@Path("servers")
@Produces(MediaType.APPLICATION_JSON)
public class ServerResource {

	@GET
	@Timed
	public List<Server> getServers() {
		return Lists.newArrayList(new Server(4096, 2, new ArrayList<NetworkInterface>()));
	}
}
