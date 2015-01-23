package com.migibert.kheo.resources;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jongo.MongoCollection;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.migibert.kheo.configuration.ViewDetail;
import com.migibert.kheo.configuration.ViewList;
import com.migibert.kheo.core.Server;
import com.migibert.kheo.service.ServerService;

@Path("servers")
@Produces(MediaType.APPLICATION_JSON)
public class ServerResource {

    private ServerService service;

    public ServerResource(MongoCollection serverCollection) {
        this.service = new ServerService(serverCollection);
    }

    @GET
    @Timed
    @JsonView(ViewList.class)
    public Response getServers() {
        return Response.status(Status.OK).entity(service.readAll()).build();
    }

    @GET
    @Timed
    @Path("/{host}")
    @JsonView(ViewDetail.class)
    public Response getServer(@PathParam("host") String host) {
        Server server = service.read(host);
        if (server == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.status(Status.OK).entity(server).build();
    }

    @GET
    @Timed
    @Path("/{host}/discover")
    @JsonView(ViewDetail.class)
    public Response discoverServer(@PathParam("host") String host) {
        Server server = service.read(host);
        if (server == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        Server discoveredServer = service.discover(server, false);
        return Response.status(Status.OK).entity(discoveredServer).build();
    }

    @POST
    @Timed
    @JsonView(ViewDetail.class)
    public Response createServer(Server server) {
        service.create(server);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Timed
    @Path("/{host}")
    @JsonView(ViewDetail.class)
    public Response updateServer(@PathParam("host") String host, Server server) {
        if (!host.equals(server.host)) {
            throw new BadRequestException("Hosts does not match.");
        }
        service.update(server);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Timed
    @Path("/{host}")
    public Response deleteServer(@PathParam("host") String host) {
        service.delete(host);
        return Response.status(Status.NO_CONTENT).build();
    }

}
