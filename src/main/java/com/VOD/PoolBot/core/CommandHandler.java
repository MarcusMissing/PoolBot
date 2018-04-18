package com.VOD.PoolBot.core;

import java.util.HashMap;

import com.VOD.PoolBot.commands.Command;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandHandler {
	
	
	public static HashMap<String, Command> commands = new HashMap<>();
	
	public static void handleCommand(CommandParser.CommandContainer cmd, MessageReceivedEvent event) {
		
		if (commands.containsKey(cmd.invoke)) {
			
			boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
			
			if (!safe) {
				commands.get(cmd.invoke).action(cmd, cmd.event);
				commands.get(cmd.invoke).executed(safe, cmd.event);
			} else {
				commands.get(cmd.invoke).executed(safe, cmd.event);
			}
			
		} else {
			 event.getJDA().getTextChannelsByName(Constants.getOutput(), true).get(0)
			.sendMessage("There is no command: " + cmd.invoke + "\nTry !help.").queue();
		}
		
	}
	
}
