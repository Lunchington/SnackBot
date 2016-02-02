package com.brokeassgeeks.snackbot;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.brokeassgeeks.snackbot.Seen.SeenDataBase;
import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import com.brokeassgeeks.snackbot.commands.*;
import com.brokeassgeeks.snackbot.commands.fun.EightBall;
import com.brokeassgeeks.snackbot.commands.fun.Fortune;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.commands.fun.Lart;
import com.brokeassgeeks.snackbot.listeners.SeenActivityListener;
import com.brokeassgeeks.snackbot.Utils.MinecraftServerUtils;
import com.brokeassgeeks.snackbot.commands.fun.Insult;
import com.brokeassgeeks.snackbot.listeners.CommandListener;
import com.brokeassgeeks.snackbot.listeners.UrlParserListener;
import com.brokeassgeeks.snackbot.listeners.ServerActivityListener;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;

import lombok.Getter;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.managers.ThreadedListenerManager;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class SnackBot {
    private static final Logger logger = LoggerFactory.getLogger(SnackBot.class);

    @Getter private static SeenDataBase seenDataBase = new SeenDataBase();
    @Getter private static MinecraftServer[] servers;
    @Getter@Setter private static ArrayList<String> admins;

    @Getter private static PircBotX bot;

    public static void main(String[] args) throws IOException, IrcException {

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        logger.info("Bot Starting....");
        ThreadedListenerManager listenerManager = new ThreadedListenerManager();

        Configuration configuration = Config.getInstance().load()
                .setListenerManager(listenerManager)
                .buildConfiguration();

        listenerManager.addListener(new UrlParserListener());
        listenerManager.addListener(new CommandListener());
        listenerManager.addListener(new ServerActivityListener());
        listenerManager.addListener(new SeenActivityListener());

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
        CommandManager.getInstance().addCommand(Insult.class);
        CommandManager.getInstance().addCommand(Tell.class);
        CommandManager.getInstance().addCommand(AdminCommand.class);
        CommandManager.getInstance().addCommand(Lart.class);
        CommandManager.getInstance().addCommand(Help.class);


        servers = MinecraftServerUtils.loadServers();
        admins = AdminUtils.loadAdmins();

        bot = new PircBotX(configuration);
        bot.startBot();

    }

}
