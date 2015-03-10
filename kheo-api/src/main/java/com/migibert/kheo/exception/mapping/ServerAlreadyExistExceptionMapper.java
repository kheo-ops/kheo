package com.migibert.kheo.exception.mapping;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.migibert.kheo.exception.ServerAlreadyExistException;

@Provider
public class ServerAlreadyExistExceptionMapper implements ExceptionMapper<ServerAlreadyExistException> {

    @Override
    public Response toResponse(ServerAlreadyExistException e) {
        return Response.status(Status.CONFLICT).build();
    }
}
