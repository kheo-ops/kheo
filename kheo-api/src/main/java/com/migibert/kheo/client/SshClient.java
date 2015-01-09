package com.migibert.kheo.client;

import java.io.IOException;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import com.migibert.kheo.core.Server;

public class SshClient {

    public static String execute(Server server, String command) throws IOException {
        try (SSHClient ssh = new SSHClient();) {
            ssh.addHostKeyVerifier(new PromiscuousVerifier());
            ssh.connect(server.host);
            ssh.loadKnownHosts();
            ssh.authPassword(server.user, server.password);
            try (Session session = ssh.startSession()) {
                Command cmd = session.exec(command);
                return IOUtils.readFully(cmd.getInputStream()).toString();
            }
        }
    }
}
