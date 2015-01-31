package com.migibert.kheo.core.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.core.OperatingSystem;

public class UnameCommand extends AbstractSshCommand<OperatingSystem> {

	private Logger logger = LoggerFactory.getLogger(UnameCommand.class);
	
	public UnameCommand() {
		super("uname -srio");
	}

	@Override
	public OperatingSystem parse(String result) {
		logger.info(result);
		String[] data = result.split(" ");
		if (data.length != 4) {
			throw new IllegalArgumentException("uname -srio result might have exactly 4 arguments: kernel-name, hardware-platform, ");
		}

		OperatingSystem os = new OperatingSystem();
		os.kernelName = data[0];
		os.kernelRelease = data[1];
		os.hardwarePlatform = data[2];
		os.name = data[3];

		return os;
	}
}
