package com.VOD.PoolBot.core;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import com.VOD.PoolBot.commands.CmdChangePrefix;
import com.VOD.PoolBot.commands.CmdHelloUser;
import com.VOD.PoolBot.commands.CmdHelp;
import com.VOD.PoolBot.commands.CmdOutput;
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

		try {
			builder.setToken(Constants.readToken());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		CommandHandler.commands.put("prefix", new CmdChangePrefix());
		CommandHandler.commands.put("help", new CmdHelp());
		CommandHandler.commands.put("output", new CmdOutput());
	}

	/*
	 * init Listeners
	 */
	public static void addListener() {
		builder.addEventListener(new CommandListener());
//		builder.addEventListener(new ReadyListener());
	}
}
