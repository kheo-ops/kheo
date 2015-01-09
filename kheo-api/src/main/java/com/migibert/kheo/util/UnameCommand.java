package com.migibert.kheo.util;

import java.io.IOException;

import com.migibert.kheo.client.SshClient;
import com.migibert.kheo.core.OperatingSystem;
import com.migibert.kheo.core.Server;

public class UnameCommand extends AbstractSshCommand<OperatingSystem> {

    @Override
    public OperatingSystem parse(String result) {
        String[] data = result.split(" ");
        if(data.length != 4) {
            throw new IllegalArgumentException("uname -srio result might have exactly 4 arguments: kernel-name, hardware-platform, ");
        }
        
        OperatingSystem os = new OperatingSystem();        
        os.kernelName = data[0];
        os.kernelRelease = data[1];
        os.hardwarePlatform = data[2];
        os.name = data[3];
        
        return os;
    }
    
    @Override
    public String execute(Server target, String command) throws IOException {
        return SshClient.execute(target, command);
    }

}
