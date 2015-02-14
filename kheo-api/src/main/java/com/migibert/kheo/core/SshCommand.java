package com.migibert.kheo.core;

import java.io.IOException;

public interface SshCommand<T> {

    String execute(Server target, String command) throws IOException;

    T parse(String result);
}
