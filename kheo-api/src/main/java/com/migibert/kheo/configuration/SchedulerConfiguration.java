package com.migibert.kheo.configuration;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

public class SchedulerConfiguration {

	@NotEmpty
	@Valid
	public String cronExpression = "0 0 12 * * ?";
}
