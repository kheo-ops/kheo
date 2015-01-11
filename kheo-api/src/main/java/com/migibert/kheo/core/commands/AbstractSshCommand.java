package com.migibert.kheo.core.commands;

import java.io.IOException;

import com.migibert.kheo.client.SshClient;
import com.migibert.kheo.core.Server;

public abstract class AbstractSshCommand<T> implements SshCommand<T> {
	
	private String command;
	
	protected AbstractSshCommand(String command) {
		this.command = command;
	}
    
    public T executeAndParse(Server target) throws IOException {
        return parse(execute(target, command));
    }
    
    @Override
    public String execute(Server target, String command) throws IOException {
        return SshClient.execute(target, command);
    }
}
