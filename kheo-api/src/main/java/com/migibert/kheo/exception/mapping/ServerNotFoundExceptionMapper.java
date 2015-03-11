package com.migibert.kheo.exception.mapping;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.migibert.kheo.exception.ServerNotFoundException;

@Provider
public class ServerNotFoundExceptionMapper implements ExceptionMapper<ServerNotFoundException> {

    @Override
    public Response toResponse(ServerNotFoundException arg0) {
        return Response.status(Status.NOT_FOUND).build();
    }

}
