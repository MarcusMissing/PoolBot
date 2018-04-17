package com.VOD.PoolBot.listeners;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter{

	public void onReady(ReadyEvent event) {
		String out = "\nThis Bot is running on following servers: \n";
				
		for (Guild g : event.getJDA().getGuilds()) {
			out += g.getName();
		}
		
		System.out.println(out);
	}
	
}
