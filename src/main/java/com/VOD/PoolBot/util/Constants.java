package com.VOD.PoolBot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Constants {

	private static char prefix = '?'; //default prefix
	private static String output = "bot-commands-admin";

	public static String readToken() throws IOException {
		
		String token;
		File file = new File("C:\\JavaDev\\PoolBot_Token.txt");
		FileReader reader = new FileReader(file.getAbsolutePath());
		try (BufferedReader br = new BufferedReader(reader)) {
			token = br.readLine();
		}
		return token;
	}
	
	private static String clanLeader = "[Clan Leitung]";
	
	/*
	 * Getter and setter
	 */
	public static char getPrefix() {
		return prefix;
	}

	public static void setPrefix(char prefix) {
		Constants.prefix = prefix;
	}

	public static String getOutput() {
		return output;
	}

	public static void setOutput(String output) {
		Constants.output = output;
	}

	public static String getClanLeader() {
		return clanLeader;
	}

	public static void setClanLeader(String clanLeader) {
		Constants.clanLeader = clanLeader;
	}
}
