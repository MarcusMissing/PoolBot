package com.VOD.PoolBot.core;

import javax.security.auth.login.LoginException;

import com.VOD.PoolBot.commands.CmdHelloUser;
import com.VOD.PoolBot.listeners.CommandListener;
import com.VOD.PoolBot.listeners.ReadyListener;
import com.VOD.PoolBot.util.Constants;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;

public class Main {

	public static JDABuilder builder;

	public static void main(String[] args) {

		builder = new JDABuilder(AccountType.BOT);

		builder.setToken(Constants.getToken());
		builder.setAutoReconnect(true);
		builder.setStatus(OnlineStatus.ONLINE);

		addListener();
		addCommands();

		try {
			JDA jda = builder.buildBlocking();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * init commands
	 */
	public static void addCommands() {
		CommandHandler.commands.put("hello", new CmdHelloUser());
	}

	/*
	 * init Listeners
	 */
	public static void addListener() {
		builder.addEventListener(new CommandListener());
		builder.addEventListener(new ReadyListener());
	}
}
