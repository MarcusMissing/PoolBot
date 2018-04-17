package com.VOD.PoolBot.listeners;

import com.VOD.PoolBot.core.CommandHandler;
import com.VOD.PoolBot.core.CommandParser.CommandContainer;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent event) {

		if (event.getMessage().getContentRaw().startsWith(Constants.getPrefix())) {
			if (event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {

				CommandContainer parser = CommandHandler.parse.parser(event.getMessage().getContentRaw(), event);

				CommandHandler.handleCommand(parser);

			}
		}
	}
}
