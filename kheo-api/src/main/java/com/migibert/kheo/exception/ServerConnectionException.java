package com.migibert.kheo.exception;

public class ServerConnectionException extends RuntimeException {

    private static final long serialVersionUID = -160254829781017399L;

    public ServerConnectionException(String hostname) {
        super("SSH connection to " + hostname + " failed");
    }

}
