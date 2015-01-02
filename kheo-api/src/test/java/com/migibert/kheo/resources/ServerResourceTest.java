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

import org.jongo.MongoCollection;
import org.junit.After;
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

	@After
	public void setup() {
		MONGO_RULE.getServerCollection().remove();
	}

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

	@Test
	public void when_post_servers_ok_then_201() {
		// Adapt
		NetworkInterface eth0 = new NetworkInterface("10.0.2.15", "fe80::a00:27ff:fe09:ac9d/64", "Ethernet", "eth0",
				"10.0.2.255", "255.255.255.0");
		NetworkInterface lo = new NetworkInterface("127.0.0.1", "", "Local loopback", "lo", "", "255.0.0.0");
		Server server = new Server("kheo-test-creation", 4096, 2, Lists.newArrayList(eth0, lo));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers");

		// Act
		Response response = target.request().post(Entity.entity(server, MediaType.APPLICATION_JSON));

		// Assert
		MongoCollection serverCollection = MONGO_RULE.getServerCollection();
		assertThat(response.getStatus()).isEqualTo(Status.CREATED.getStatusCode());
		assertThat(serverCollection.count()).isEqualTo(1);
		assertThat(serverCollection.find().as(Server.class).iterator()).containsExactly(server);
	}

	@Test
	public void when_post_servers_with_existing_hostname_then_409() {

		// Adapt
		NetworkInterface eth0 = new NetworkInterface("10.0.2.15", "fe80::a00:27ff:fe09:ac9d/64", "Ethernet", "eth0",
				"10.0.2.255", "255.255.255.0");
		NetworkInterface lo = new NetworkInterface("127.0.0.1", "", "Local loopback", "lo", "", "255.0.0.0");
		Server server = new Server("kheo-test-conflict", 4096, 2, Lists.newArrayList(eth0, lo));
		Server serverConflict = new Server("kheo-test-conflict", 2048, 1, Lists.newArrayList(eth0, lo));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers");

		// Act
		target.request().post(Entity.entity(server, MediaType.APPLICATION_JSON));
		Response response = target.request().post(Entity.entity(serverConflict, MediaType.APPLICATION_JSON));

		// Assert
		MongoCollection serverCollection = MONGO_RULE.getServerCollection();
		assertThat(response.getStatus()).isEqualTo(Status.CONFLICT.getStatusCode());
		assertThat(serverCollection.find().as(Server.class).iterator()).doesNotContain(serverConflict);
		assertThat(serverCollection.count()).isEqualTo(1);
		assertThat(serverCollection.find().as(Server.class).iterator()).containsExactly(server);
	}

	@Test
	public void when_get_servers_on_non_empty_collection_then_200() {

		// Adapt
		NetworkInterface eth0 = new NetworkInterface("10.0.2.15", "fe80::a00:27ff:fe09:ac9d/64", "Ethernet", "eth0",
				"10.0.2.255", "255.255.255.0");
		NetworkInterface lo = new NetworkInterface("127.0.0.1", "", "Local loopback", "lo", "", "255.0.0.0");
		Server server1 = new Server("kheo-test-1", 4096, 2, Lists.newArrayList(eth0, lo));
		Server server2 = new Server("kheo-test-2", 2048, 1, new ArrayList<NetworkInterface>());

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers");

		// Act
		target.request().post(Entity.entity(server1, MediaType.APPLICATION_JSON));
		target.request().post(Entity.entity(server2, MediaType.APPLICATION_JSON));
		Response response = target.request().get();

		// Assert
		List<Server> responseEntity = response.readEntity(new GenericType<List<Server>>() {
		});
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(responseEntity).isNotEmpty();
		assertThat(responseEntity).contains(server1, server2);
	}

	@Test
	public void when_get_servers_on_empty_collection_then_200() {

		// Adapt
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers");

		// Act
		Response response = target.request().get();

		// Assert
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(response.readEntity(new GenericType<List<Server>>() {
		})).isEmpty();
	}

	@Test
	public void when_get_existing_server_then_200() {

		// Adapt
		NetworkInterface eth0 = new NetworkInterface("10.0.2.15", "fe80::a00:27ff:fe09:ac9d/64", "Ethernet", "eth0",
				"10.0.2.255", "255.255.255.0");
		NetworkInterface lo = new NetworkInterface("127.0.0.1", "", "Local loopback", "lo", "", "255.0.0.0");
		Server server = new Server("kheo-test", 4096, 2, Lists.newArrayList(eth0, lo));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers");

		// Act
		target.request().post(Entity.entity(server, MediaType.APPLICATION_JSON));
		Response response = target.path("kheo-test").request().get();

		// Assert
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(response.readEntity(Server.class)).isEqualTo(server);
	}

	@Test
	public void when_get_non_existing_server_then_404() {

		// Adapt
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers/non-existing-server");

		// Act
		Response response = target.request().get();

		// Assert
		assertThat(response.getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
	}

	@Test
	public void when_delete_existing_server_then_204() {
		// Adapt
		NetworkInterface eth0 = new NetworkInterface("10.0.2.15", "fe80::a00:27ff:fe09:ac9d/64", "Ethernet", "eth0",
				"10.0.2.255", "255.255.255.0");
		NetworkInterface lo = new NetworkInterface("127.0.0.1", "", "Local loopback", "lo", "", "255.0.0.0");
		Server server = new Server("kheo-test", 4096, 2, Lists.newArrayList(eth0, lo));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers");

		// Act
		target.request().post(Entity.entity(server, MediaType.APPLICATION_JSON));
		Response response = target.path("kheo-test").request().delete();

		// Assert
		assertThat(response.getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());
		assertThat(MONGO_RULE.getServerCollection().find().as(Server.class).iterator()).isEmpty();
	}
	
	@Test
	public void when_delete_non_existing_server_then_404() {

		// Adapt
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers/non-existing-server");

		// Act
		Response response = target.request().delete();

		// Assert
		assertThat(response.getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
	}
	
	@Test
	public void when_put_servers_then_200() {

		// Adapt
		NetworkInterface eth0 = new NetworkInterface("10.0.2.15", "fe80::a00:27ff:fe09:ac9d/64", "Ethernet", "eth0",
				"10.0.2.255", "255.255.255.0");
		NetworkInterface lo = new NetworkInterface("127.0.0.1", "", "Local loopback", "lo", "", "255.0.0.0");
		Server server = new Server("kheo-test", 4096, 2, Lists.newArrayList(eth0, lo));
		Server serverUpdate = new Server("kheo-test", 2048, 1, Lists.newArrayList(eth0, lo));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers");

		// Act
		target.request().post(Entity.entity(server, MediaType.APPLICATION_JSON));
		Response response = target.path("kheo-test").request().put(Entity.entity(serverUpdate, MediaType.APPLICATION_JSON));

		// Assert
		assertThat(response.getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());
		assertThat(MONGO_RULE.getServerCollection().find().as(Server.class).iterator()).containsExactly(serverUpdate);
	}
	
	@Test
	public void when_put_servers_with_different_hostname_then_400() {

		// Adapt
		NetworkInterface eth0 = new NetworkInterface("10.0.2.15", "fe80::a00:27ff:fe09:ac9d/64", "Ethernet", "eth0",
				"10.0.2.255", "255.255.255.0");
		NetworkInterface lo = new NetworkInterface("127.0.0.1", "", "Local loopback", "lo", "", "255.0.0.0");
		Server server = new Server("kheo-test-conflict", 4096, 2, Lists.newArrayList(eth0, lo));
		Server serverConflict = new Server("kheo-test-conflict", 2048, 1, Lists.newArrayList(eth0, lo));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers");

		// Act
		target.request().post(Entity.entity(server, MediaType.APPLICATION_JSON));
		Response response = target.path("test").request().put(Entity.entity(serverConflict, MediaType.APPLICATION_JSON));

		// Assert
		assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
		assertThat(MONGO_RULE.getServerCollection().find().as(Server.class).iterator()).containsExactly(server);
	}
	
	@Test
	public void when_put_non_existing_server_then_400() {

		// Adapt
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/servers/non-existing-server");

		// Act
		Response response = target.request().put(Entity.entity(new Server("test-put", 256, 1, new ArrayList<NetworkInterface>()), MediaType.APPLICATION_JSON));

		// Assert
		assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
	}
}