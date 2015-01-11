package com.migibert.kheo.core.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.migibert.kheo.core.ListeningProcess;

public class NetstatCommand extends AbstractSshCommand<List<ListeningProcess>> {

	public NetstatCommand() {
		super("netstat -ntlp");
	}

	@Override
	public List<ListeningProcess> parse(String result) {
		List<ListeningProcess> listeningProcesses = new ArrayList<ListeningProcess>();
		String[] lines = result.split("\n");
		for(String line: lines) {
			
		}
		return listeningProcesses;
	}

	private String extractPort(String networkData) {
		String data = networkData.trim();
		int portDelimiterIndex = data.indexOf(":");
		if (portDelimiterIndex != -1) {
			return data.substring(portDelimiterIndex + 1);
		}
		return "";
	}

	private String extractPid(String processData) {
		String data = processData.trim();
		int portDelimiterIndex = data.indexOf("/");
		if (portDelimiterIndex != -1) {
			return data.substring(0, portDelimiterIndex);
		}
		return "";
	}

}
