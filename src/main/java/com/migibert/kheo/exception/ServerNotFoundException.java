package com.migibert.kheo.exception;


public class ServerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8929158381877118440L;

	public ServerNotFoundException(String hostname) {
		super("Server " + hostname + " does not exist");
	}

}
