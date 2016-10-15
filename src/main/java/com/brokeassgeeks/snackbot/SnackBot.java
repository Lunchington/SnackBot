package com.brokeassgeeks.snackbot;



import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.brokeassgeeks.snackbot.Seen.SeenDataBase;
import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.listeners.*;
import com.brokeassgeeks.snackbot.Utils.MinecraftServerUtils;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;

import lombok.Getter;

import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import org.slf4j.LoggerFactory;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.managers.ThreadedListenerManager;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.ArrayList;

public class SnackBot {
    public static final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    @Getter public static JDA jda;

    @Getter private static SeenDataBase seenDataBase;

    @Getter private static PircBotX bot;
    @Getter private static CommandListener commandListener;

    public static void main(String[] args) throws IOException, IrcException, LoginException, InterruptedException {
        seenDataBase = new SeenDataBase();
        commandListener = new CommandListener();

        logger.setLevel(Level.INFO);

        logger.info("Bot Starting....");
        ThreadedListenerManager listenerManager = new ThreadedListenerManager();

        Configuration configuration = Config.getInstance().load()
                .setListenerManager(listenerManager)
                .buildConfiguration();

        listenerManager.addListener(new UrlParserListener());
        listenerManager.addListener(commandListener);
        listenerManager.addListener(new ServerActivityListener());
        listenerManager.addListener(new SeenActivityListener());
        listenerManager.addListener(new DiscordBouncer());

        bot = new PircBotX(configuration);

        jda = new JDABuilder().setBotToken(Config.DISCORD_TOKEN).addListener(new SnackbotDiscord()).buildBlocking();
        bot.startBot();


    }

    public static MinecraftServer[] getServers() {
        return MinecraftServerUtils.loadServers();
    }

    public static ArrayList<String> getAdmins() {
        return AdminUtils.loadAdmins();
    }

}
