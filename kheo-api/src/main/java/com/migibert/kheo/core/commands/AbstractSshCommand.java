package com.migibert.kheo.core.commands;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.client.SshClient;
import com.migibert.kheo.core.Server;

public abstract class AbstractSshCommand<T> implements SshCommand<T> {
	
	private Logger logger;
	private String command;
	
	protected AbstractSshCommand(String command) {
		this.command = command;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
    
    public T executeAndParse(Server target) throws IOException {
        logger.info("Executing {} on {}", command, target.host);
    	String result = execute(target, command);    	
    	logger.info("Result is {}", result);
        return parse(result);
    }
    
    @Override
    public String execute(Server target, String command) throws IOException {
        if(target.sudo) {
        	logger.info("Executing remote command with sudo flag");
        	command = "echo " + target.password + " | sudo -kS " + command;
        }
    	return SshClient.execute(target, command);
    }
}
