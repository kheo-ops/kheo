package com.migibert.kheo.core.plugin.os;

import java.util.List;

import org.assertj.core.util.Lists;

import com.migibert.kheo.core.AbstractSshCommand;

public class OsCommand extends AbstractSshCommand<List<OsServerProperty>> {

	public OsCommand() {
		super("uname -srio");
	}

	@Override
	public List<OsServerProperty> parse(String result) {
		String[] data = result.split(" ");
		if (data.length != 4) {
			throw new IllegalArgumentException("uname -srio result might have exactly 4 arguments: kernel-name, hardware-platform, ");
		}

		OsServerProperty os = new OsServerProperty();
		os.kernelName = data[0];
		os.kernelRelease = data[1];
		os.hardwarePlatform = data[2];
		os.name = data[3];

		return Lists.newArrayList(os);
	}
}
