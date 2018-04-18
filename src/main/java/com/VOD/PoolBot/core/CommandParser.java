package com.VOD.PoolBot.core;

import java.util.ArrayList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandParser {

	public static CommandContainer parser(String raw, MessageReceivedEvent event) {
		
		char[] charArray = raw.toCharArray();
		char[] beheadedChar = new char[charArray.length - 1];
		System.arraycopy(charArray, 1, beheadedChar, 0, charArray.length - 1);
		
		StringBuilder sb = new StringBuilder();
		for (char c : beheadedChar)
			sb.append(c);
		String[] splitBeheaded = sb.toString().split(" ");
		String invoke = splitBeheaded[0];
		ArrayList<String> split = new ArrayList<>();
		
		for (String s : splitBeheaded)
			split.add(s);
		
		String[] args = new String[split.size() - 1];
		split.subList(1, split.size()).toArray(args);
		
		return new CommandContainer(raw, sb.toString(), splitBeheaded, invoke, args, event);
	}

	public static class CommandContainer {

		public final String raw;
		public final String beheaded;
		public final String[] splitBeheaded;
		public final String invoke;
		public final String[] args;
		public final MessageReceivedEvent event;

		public CommandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args,
				MessageReceivedEvent event) {
			
			this.raw = rw;
			this.beheaded = beheaded;
			this.splitBeheaded = splitBeheaded;
			this.invoke = invoke;
			this.args = args;
			this.event = event;
					
		}

	}

}
