package com.VOD.PoolBot.listeners;

import com.VOD.PoolBot.core.CommandHandler;
import com.VOD.PoolBot.core.CommandParser;
import com.VOD.PoolBot.core.CommandParser.CommandContainer;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent event) {

		char c = 0;
		
		if (!event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
			c = event.getMessage().getContentRaw().toCharArray()[0];

			if (c == Constants.getPrefix()) {

				CommandContainer parser = CommandParser.parser(event.getMessage().getContentRaw(), event);

				CommandHandler.handleCommand(parser, event);

			}
		}
	}
}
