package com.migibert.kheo.core.commands;

import java.io.IOException;

import com.migibert.kheo.core.Server;

public interface SshCommand<T> {

    String execute(Server target, String command) throws IOException;

    T parse(String result);
}
