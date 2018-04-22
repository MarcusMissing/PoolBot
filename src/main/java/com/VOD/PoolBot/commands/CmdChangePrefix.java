package com.VOD.PoolBot.commands;

import java.awt.Color;

import com.VOD.PoolBot.core.Permissions;
import com.VOD.PoolBot.core.CommandParser.CommandContainer;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdChangePrefix implements Command {

	EmbedBuilder error = new EmbedBuilder().setColor(Color.RED);

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action(CommandContainer cmd, MessageReceivedEvent event) {

		if (Permissions.checkPermission(event) == true) {
			
			Constants.setPrefix(cmd.args[0].toCharArray()[0]);
			event.getJDA().getTextChannelsByName(Constants.getOutput(), true).get(0)
					.sendMessage("Prefix was changed to " + Constants.getPrefix()).queue();
		}
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
