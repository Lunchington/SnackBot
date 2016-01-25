package com.brokeassgeeks.snackbot;


import com.brokeassgeeks.snackbot.Seen.SeenDataBase;
import com.brokeassgeeks.snackbot.Twitch.Twitch;
import com.brokeassgeeks.snackbot.commands.*;
import com.brokeassgeeks.snackbot.commands.fun.EightBall;
import com.brokeassgeeks.snackbot.commands.fun.Fortune;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.Utils.MinecraftServerUtils;
import com.brokeassgeeks.snackbot.listeners.CommandListener;
import com.brokeassgeeks.snackbot.listeners.UrlParserListener;
import com.brokeassgeeks.snackbot.listeners.ServerActivityListener;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;

import lombok.Getter;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.managers.ThreadedListenerManager;

import java.io.*;
import java.util.logging.Logger;

public class SnackBot {
    private static final Logger logger = Logger.getLogger(SnackBot.class.getName());
    private static PircBotX bot;
    private static Configuration configuration;
    private static ThreadedListenerManager listenerManager;

    @Getter private static SeenDataBase seenDataBase = new SeenDataBase();
    @Getter private static MinecraftServer[] servers;


    public static void main(String[] args) throws IOException, IrcException {
        logger.info("Bot Starting....");
        listenerManager = new ThreadedListenerManager();

        configuration = Config.getInstance().load()
                .setListenerManager(listenerManager)
                .buildConfiguration();

        listenerManager.addListener(new UrlParserListener());
        listenerManager.addListener(new CommandListener());
        listenerManager.addListener(new ServerActivityListener());

        CommandManager.getInstance().addCommand(SimpleCommand.class);
        CommandManager.getInstance().addCommand(EightBall.class);
        CommandManager.getInstance().addCommand(Fortune.class);
        CommandManager.getInstance().addCommand(ServerStatus.class);
        CommandManager.getInstance().addCommand(ServerOnlineUsers.class);
        CommandManager.getInstance().addCommand(Mod.class);
        CommandManager.getInstance().addCommand(Mojang.class);
        CommandManager.getInstance().addCommand(Time.class);
        CommandManager.getInstance().addCommand(Seen.class);
        CommandManager.getInstance().addCommand(TwitchCommand.class);

        servers = MinecraftServerUtils.loadServers();

        bot = new PircBotX(configuration);
        bot.startBot();

    }

}
