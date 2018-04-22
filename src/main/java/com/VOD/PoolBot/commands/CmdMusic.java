package com.VOD.PoolBot.commands;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.VOD.PoolBot.core.CommandParser.CommandContainer;
import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import audioCore.AudioInfo;
import audioCore.PlayerSendHandler;
import audioCore.TrackManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdMusic implements Command {

	private static final int playlistLimit = 100;
	private static Guild guild;
	private static final AudioPlayerManager manager = new DefaultAudioPlayerManager();
	private static final Map<Guild, Map.Entry<AudioPlayer, TrackManager>> players = new HashMap<>();

	public void Music() {
		AudioSourceManagers.registerRemoteSources(manager);
	}

	private AudioPlayer createPlayer(Guild g) {

		AudioPlayer p = manager.createPlayer();
		TrackManager m = new TrackManager(p);
		p.addListener(m);

		guild.getAudioManager().setSendingHandler(new PlayerSendHandler(p));

		players.put(g, new AbstractMap.SimpleEntry<>(p, m));

		return p;
	}

	private boolean hasPlayer(Guild g) {
		return players.containsKey(g);
	}

	private AudioPlayer getPlayer(Guild g) {
		if (hasPlayer(g))
			return players.get(g).getKey();
		else
			return createPlayer(g);
	}

	private TrackManager getManager(Guild g) {
		return players.get(g).getValue();
	}

	private boolean isIdle(Guild g) {
		return !hasPlayer(g) || getPlayer(g).getPlayingTrack() == null;
	}

	private void loadTrack(String identifier, Member author, Message msg) {

		Guild guild = author.getGuild();
		getPlayer(guild);

		manager.setFrameBufferDuration(1000);
		manager.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				getManager(guild).queue(track, author);

			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				for (int i = 0; i < (playlist.getTracks().size() > playlistLimit ? playlistLimit
						: playlist.getTracks().size()); i++) {
					getManager(guild).queue(playlist.getTracks().get(i), author);
				}

			}

			@Override
			public void noMatches() {
				// TODO Auto-generated method stub

			}

			@Override
			public void loadFailed(FriendlyException arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void skip(Guild g) {
		getPlayer(g).stopTrack();
	}

	private String getTimestamp(long milis) {
		long seconds = milis / 1000;
		long hours = Math.floorDiv(seconds, 3600);
		seconds = seconds - (hours * 3600);
		long mins = Math.floorDiv(seconds, 60);
		seconds = seconds - (mins * 60);
		return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
	}

	private String buildQueueMessage(AudioInfo info) {
		AudioTrackInfo trackInfo = info.getTrack().getInfo();
		String title = trackInfo.title;
		long length = trackInfo.length;
		return "`[ " + getTimestamp(length) + " ]` " + title + "\n";
	}

	private void sendErrorMsg(MessageReceivedEvent event, String content) {
		event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setDescription(content).build())
				.queue();
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action(CommandContainer cmd, MessageReceivedEvent event) {

		guild = event.getGuild();

		if (cmd.args.length < 1) {
			sendErrorMsg(event, help());
			return;
		}
		
		MusicCommands mCommand = MusicCommands.valueOf(cmd.args[0].toUpperCase());

		switch (mCommand) {

		case PLAY:
		case P:

			if (cmd.args.length < 2) {
				sendErrorMsg(event, "Please enter valid source!");
				return;
			}
			
			String input = Arrays.stream(cmd.args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);

			if (!(input.startsWith("http://") || input.startsWith("https://")))
				input = "ytsearch: " + input;

			loadTrack(input, event.getMember(), event.getMessage());

			break;

		case SKIP:
		case S:

			if (isIdle(guild))
				return;

			try {
				for (int i = (cmd.args.length > 1 ? Integer.parseInt(cmd.args[1]) : 1); i == 1; i++)
					skip(guild);
			} catch (Exception e) {
				e.printStackTrace();
				sendErrorMsg(event, "Enter a valid number of tracks to skip!");
			}
			break;

		case STOP:

			if (isIdle(guild))
				return;

			getManager(guild).purgeQueue();
			skip(guild);
			guild.getAudioManager().closeAudioConnection();

			break;

		case SHUFFLE:

			if (isIdle(guild))
				return;
			getManager(guild).shuffleQueue();

			break;

		case NOW:
		case INFO:

			if (isIdle(guild))
				return;

			AudioTrack track = getPlayer(guild).getPlayingTrack();
			AudioTrackInfo info = track.getInfo();

			event.getTextChannel()
					.sendMessage(new EmbedBuilder().setDescription("**CURRENT TRACK INFO:**")
							.addField("Title", info.title, false)
							.addField("Duration", "'[ " + getTimestamp(track.getPosition()) + "/ "
									+ getTimestamp(track.getDuration()) + " ]'", false)
							.addField("Author", info.author, false).build())
					.queue();

		case QUEUE:
		case Q:

			if (isIdle(guild))
				return;

			try {
				int sideNumb = cmd.args.length > 1 ? Integer.parseInt(cmd.args[1]) : 1;
				
				List<String> tracks = new ArrayList<>();
				List<String> trackSublist;
				
				getManager(guild).getQueue().forEach(AudioInfo -> tracks.add(buildQueueMessage(AudioInfo)));
				
				if (tracks.size() > 20)
					trackSublist = tracks.subList((sideNumb - 1) * 20, (sideNumb - 1) * 20 + 20);
				else
					trackSublist = tracks;
				
				String out = trackSublist.stream().collect(Collectors.joining("\n"));
				int sideNumbAll = tracks.size() >= 20 ? tracks.size() / 20 : 1;
				
				event.getTextChannel().sendMessage(
						new EmbedBuilder()
							.setDescription("**CURRENT QUEUE:**\n" + 
									"*[" + getManager(guild).getQueue().size() + " Tracks | Side " + sideNumb + " / " + sideNumbAll + "]*\n\n" +
									out
						).build()
				).queue();
				
				
			} catch (Exception e) {
				e.printStackTrace();
				sendErrorMsg(event, "Enter a valid number!");
			}
			
			
			break;

		case PAUSE:
        case RESUME:
            if (getPlayer(guild).isPaused()) {
                getPlayer(guild).setPaused(false);
                event.getTextChannel().sendMessage(
                        "Player resumed."
                ).queue();
            } else {
                getPlayer(guild).setPaused(true);
                event.getTextChannel().sendMessage(
                        "Player paused."
                ).queue();
            }
            break;
            
        case HELP:
        case H:
        	sendErrorMsg(event, help());
        	break;
            
        default:
        	sendErrorMsg(event, help());
        	
		}
	}

	@Override
	public void executed(boolean sucess, MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public String help() {
		return "Help will follow :joy:";
	}

}
