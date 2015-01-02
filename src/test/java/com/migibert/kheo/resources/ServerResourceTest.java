package com.migibert.kheo.resources;

import static org.assertj.core.api.Assertions.assertThat;
import io.dropwizard.testing.junit.DropwizardAppRule;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jersey.repackaged.com.google.common.collect.Lists;

import org.junit.ClassRule;
import org.junit.Test;

import com.migibert.kheo.KheoApplication;
import com.migibert.kheo.KheoConfiguration;
import com.migibert.kheo.core.NetworkInterface;
import com.migibert.kheo.core.Server;
import com.migibert.kheo.rules.MongoRule;

public class ServerResourceTest {

	@ClassRule
	public static final MongoRule MONGO_RULE = new MongoRule(12345);

	@ClassRule
	public static final DropwizardAppRule<KheoConfiguration> RULE = new DropwizardAppRule<KheoConfiguration>(
			KheoApplication.class, "config/kheo-api-test.yml");

	@Test
	public void crudOperations() {
		final NetworkInterface eth0 = new NetworkInterface("10.0.2.15", "fe80::a00:27ff:fe09:ac9d/64", "Ethernet",
				"eth0", "10.0.2.255", "255.255.255.0");
		final NetworkInterface lo = new NetworkInterface("127.0.0.1", "", "Local loopback", "lo", "", "255.0.0.0");
		final Server server = new Server("kheo-dev", 4096, 2, Lists.newArrayList(eth0, lo));
		final String hostname = "kheo-dev";
		Server updatedServer = new Server(hostname, 2048, 1, new ArrayList<NetworkInterface>());

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers");

		// Create server
		Response response = target.request().post(Entity.entity(server, MediaType.APPLICATION_JSON));
		assertThat(response.getStatus()).isEqualTo(Status.CREATED.getStatusCode());

		// Get servers list
		response = target.request().get();
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		List<Server> entity = response.readEntity(new GenericType<List<Server>>() {
		});
		assertThat(entity).containsExactly(server);

		// Get server
		Server readServer = target.path(hostname).request().get(Server.class);
		assertThat(readServer).isEqualTo(server);

		// Update server
		response = target.path(hostname).request().put(Entity.entity(updatedServer, MediaType.APPLICATION_JSON));
		assertThat(response.getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());
		response = target.path(hostname).request().get();
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(response.readEntity(Server.class)).isEqualTo(updatedServer);

		// Delete server
		response = target.path(hostname).request().delete();
		assertThat(response.getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());
		response = target.path(hostname).request().get();
		assertThat(response.getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
	}
}
