package com.VOD.PoolBot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdHelloUser implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		event.getJDA().getTextChannels().get(0).sendMessage("Hello, " + event.getAuthor().getName()).queue();
		
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
