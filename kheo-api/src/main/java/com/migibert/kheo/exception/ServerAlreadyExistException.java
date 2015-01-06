package com.migibert.kheo.exception;

import com.migibert.kheo.core.Server;

public class ServerAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 6878389617365341375L;

	public ServerAlreadyExistException(Server server) {
		super("Server " + server.hostname + " already exists");
	}
}
