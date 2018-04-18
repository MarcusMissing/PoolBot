package com.VOD.PoolBot.commands;

import com.VOD.PoolBot.core.CommandParser.CommandContainer;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {

	boolean called(String[] args, MessageReceivedEvent event);
	void action(CommandContainer cmd, MessageReceivedEvent event);
	void executed(boolean sucess, MessageReceivedEvent event);
	String help();

}
