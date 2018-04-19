package com.VOD.PoolBot.commands;

import com.VOD.PoolBot.core.Permissions;
import com.VOD.PoolBot.core.CommandParser.CommandContainer;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdOutput implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action(CommandContainer cmd, MessageReceivedEvent event) {

		if (Permissions.checkPermission(event) == true) {

			if (event.getGuild().getTextChannelsByName(cmd.args[0], true) != null
					&& !event.getGuild().getTextChannelsByName(cmd.args[0], true).isEmpty()
					&& event.getGuild().getTextChannelsByName(cmd.args[0], true).get(0) != null) {

				Constants.setOutput(cmd.args[0]);
				event.getJDA().getTextChannelsByName(Constants.getOutput(), true).get(0)
						.sendMessage("Now I'm writing right here.").queue();
			}

		} else {
			event.getJDA().getTextChannelsByName(Constants.getOutput(), true).get(0)
					.sendMessage("You don't have the permission to do that.").queue();
		}
	}

	@Override
	public void executed(boolean sucess, MessageReceivedEvent event) {
		System.out.println("[INFO] Command " + this.getClass().getSimpleName() + "was execuded.");

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

}
