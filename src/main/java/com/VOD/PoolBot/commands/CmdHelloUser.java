package com.VOD.PoolBot.commands;

import com.VOD.PoolBot.core.CommandParser.CommandContainer;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdHelloUser implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action(CommandContainer cmd, MessageReceivedEvent event) {
		event.getJDA().getTextChannelsByName(Constants.getOutput(), true).get(0)
				.sendMessage("Hello, " + event.getGuild().getMember(event.getAuthor()).getAsMention()).queue();

	}

	@Override
	public void executed(boolean sucess, MessageReceivedEvent event) {

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

}
