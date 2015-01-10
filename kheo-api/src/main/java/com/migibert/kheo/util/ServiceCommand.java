package com.migibert.kheo.util;

import java.util.ArrayList;
import java.util.List;

import com.migibert.kheo.core.Service;

public class ServiceCommand extends AbstractSshCommand<List<Service>> {

	public ServiceCommand() {
		super("service --status-all");
	}

	@Override
	public List<Service> parse(String result) {
		List<Service> services = new ArrayList<Service>();

		String[] lines = result.split("\n");
		for (String line : lines) {
			String[] property = line.split("  ");
			Service service = new Service();
			service.name = property[1];
			services.add(service);
		}

		return services;
	}
}
