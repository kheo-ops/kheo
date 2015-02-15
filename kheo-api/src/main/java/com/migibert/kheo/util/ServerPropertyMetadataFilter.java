package com.migibert.kheo.util;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.migibert.kheo.core.Server;

public class ServerPropertyMetadataFilter {
	public static JsonNode filter(Server server) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonValue = mapper.writeValueAsString(server);
			JsonNode rootNode = mapper.readTree(jsonValue);
			JsonNode properties = rootNode.get("serverProperties");
			Iterator<JsonNode> iterator = properties.elements();
			while (iterator.hasNext()) {
				ObjectNode property = (ObjectNode) iterator.next();
				property.remove("@kheo-class");
			}
			return rootNode;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
