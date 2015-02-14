package com.migibert.kheo.core.plugin.services;

import java.util.ArrayList;
import java.util.List;

import com.migibert.kheo.core.AbstractSshCommand;

public class ServiceCommand extends AbstractSshCommand<List<ServiceServerProperty>> {
	public ServiceCommand() {
		super("service --status-all");
	}

	@Override
	public List<ServiceServerProperty> parse(String result) {
		List<ServiceServerProperty> services = new ArrayList<>();

		String[] lines = result.split("\n");
		for (String line : lines) {
			String[] property = line.split("  ");
			ServiceServerProperty service = new ServiceServerProperty();
			service.name = property[1];
			services.add(service);
		}

		return services;
	}
}
