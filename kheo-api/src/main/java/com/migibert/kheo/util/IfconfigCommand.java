package com.migibert.kheo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.migibert.kheo.client.SshClient;
import com.migibert.kheo.core.NetworkInterface;
import com.migibert.kheo.core.Server;

public class IfconfigCommand extends AbstractSshCommand<List<NetworkInterface>> {

    @Override
    public List<NetworkInterface> parse(String result) {
        String[] line = result.split("\n");
        List<NetworkInterface> interfaces = new ArrayList<NetworkInterface>();
        if (line.length > 0) {
            String firstLine = line[0];
            String interfaceName = firstLine.substring(0, firstLine.indexOf(" "));
            NetworkInterface res = new NetworkInterface();
            res.name = interfaceName;
            interfaces.add(res);
        }

        return interfaces;
    }
    
    @Override
    public String execute(Server target, String command) throws IOException{
        return SshClient.execute(target, command);
    }
}
