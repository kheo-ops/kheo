package com.migibert.kheo.util;

import java.io.IOException;

import com.migibert.kheo.client.SshClient;
import com.migibert.kheo.core.Server;

public abstract class AbstractSshCommand<T> implements SshCommand<T> {
    
    public T executeAndParse(Server target, String command) throws IOException {
        return parse(execute(target, command));
    }
    
    @Override
    public String execute(Server target, String command) throws IOException {
        return SshClient.execute(target, command);
    }
}
