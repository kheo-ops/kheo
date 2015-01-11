package com.migibert.kheo.core.commands;

import java.util.ArrayList;
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
		for (int i = 0; i < lines.length; i++) {
			// Ignore headers line
			if (i == 0 || i == 1) {
				continue;
			}
			String[] data = findWords(lines[i]);
			ListeningProcess process = new ListeningProcess();
			process.protocol = data[0];
			process.port = extractPort(data[3]);
			process.pid = extractPid(data[6]);
			process.programName = extractProgramName(data[6]);
			listeningProcesses.add(process);
		}
		return listeningProcesses;
	}

	private String extractPort(String networkData) {
		String data = networkData.trim();
		int portDelimiterIndex = data.lastIndexOf(":");
		if (portDelimiterIndex != -1) {
			return data.substring(portDelimiterIndex + 1);
		}
		return "";
	}

	private String extractPid(String processData) {
		String data = processData.trim();
		int pidDelimiterIndex = data.indexOf("/");
		if (pidDelimiterIndex != -1) {
			return data.substring(0, pidDelimiterIndex);
		}
		return "";
	}

	private String extractProgramName(String processData) {
		String data = processData.trim();
		int pidDelimiterIndex = data.indexOf("/");
		if (pidDelimiterIndex != -1) {
			return data.substring(pidDelimiterIndex);
		}
		return "";
	}

	private int findFirstNonEmptyCharacterIndex(String string) {
		char[] characters = string.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			if (characters[i] != ' ') {
				return i;
			}
		}
		return -1;
	}

	private String[] findWords(String line) {
		ArrayList<String> words = new ArrayList<>();
		String dataLine = line;
		boolean end = findFirstNonEmptyCharacterIndex(dataLine) == -1;
		while (!end) {
			int wordStart = findFirstNonEmptyCharacterIndex(dataLine);
			int spaceIndex = dataLine.substring(wordStart).indexOf(" ");
			int wordEnd = spaceIndex == -1 ? dataLine.length() : wordStart + spaceIndex;
			words.add(dataLine.substring(wordStart, wordEnd));
			dataLine = dataLine.substring(wordEnd);
			end = dataLine.trim().isEmpty();
		}

		return words.toArray(new String[0]);
	}
}
