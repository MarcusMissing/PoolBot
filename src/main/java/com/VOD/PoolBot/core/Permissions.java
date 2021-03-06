package com.VOD.PoolBot.core;

import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Permissions {

	public static boolean checkPermission(MessageReceivedEvent event) {

		boolean hasRole = false;
		for (Role role : event.getGuild().getMember(event.getAuthor()).getRoles())
			if (role.getName().equals(Constants.getClanLeader()))
				hasRole = true;

		if (hasRole == true)
			return true;
		else 
			event.getJDA().getTextChannelsByName(Constants.getOutput(), true).get(0)
					.sendMessage("You don't have the permission to do that.").queue();

		return false;
	}

}
