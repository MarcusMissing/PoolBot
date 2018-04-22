package com.VOD.PoolBot.commands;

import java.awt.Color;
import java.util.List;

import com.VOD.PoolBot.core.CommandParser.CommandContainer;
import com.VOD.PoolBot.core.Permissions;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdClear implements Command {

	EmbedBuilder error = new EmbedBuilder().setColor(Color.RED);

	private int getInt(String string) {

		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action(CommandContainer cmd, MessageReceivedEvent event) {

		if (Permissions.checkPermission(event) == true) {

			int numb = 0;

			if (cmd.args.length < 1)
				event.getJDA().getTextChannelsByName(Constants.getOutput(), true).get(0)
						.sendMessage(error.setDescription("Enter a number of messages you want to delete").build())
						.queue();
			else
				numb = getInt(cmd.args[0]);

			if (numb >= 1) {

				try {

					MessageHistory history = new MessageHistory(event.getTextChannel());
					List<Message> msgs;

					msgs = history.retrievePast(numb + 1).complete();
					event.getTextChannel().deleteMessages(msgs).queue();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void executed(boolean sucess, MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

}
