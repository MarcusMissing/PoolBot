package com.VOD.PoolBot.listeners;

import com.VOD.PoolBot.core.CommandHandler;
import com.VOD.PoolBot.core.CommandParser;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	public void onMessageRecieved(MessageReceivedEvent event) {
		
		if (event.getMessage().getContentRaw().startsWith(Constants.getPrefix()) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
			CommandHandler.handleCommand(CommandParser.parser(event.getMessage().getContentRaw(), event));
		}
		
	}
	
}
