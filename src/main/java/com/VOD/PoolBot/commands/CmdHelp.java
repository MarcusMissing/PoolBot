package com.VOD.PoolBot.commands;

import com.VOD.PoolBot.core.CommandHandler;
import com.VOD.PoolBot.core.CommandParser.CommandContainer;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdHelp implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action(CommandContainer cmd, MessageReceivedEvent event) {

		StringBuilder sb = new StringBuilder();

		sb.append("List of commands:\n");

		for (String s : CommandHandler.commands.keySet())
			sb.append("-" + s + "\n");

		event.getJDA().getTextChannelsByName(Constants.getOutput(), true).get(0).sendMessage(sb.toString()).queue();

	}

	@Override
	public void executed(boolean sucess, MessageReceivedEvent event) {
		System.out.println("[INFO] Command was execuded.");

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

}
