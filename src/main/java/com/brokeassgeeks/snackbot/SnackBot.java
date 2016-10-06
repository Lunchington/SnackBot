package com.brokeassgeeks.snackbot;



import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.brokeassgeeks.snackbot.Seen.SeenDataBase;
import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import com.brokeassgeeks.snackbot.commands.*;
import com.brokeassgeeks.snackbot.commands.admin.Admin;
import com.brokeassgeeks.snackbot.commands.admin.ServerAdmin;
import com.brokeassgeeks.snackbot.commands.admin.TriggerAdmin;
import com.brokeassgeeks.snackbot.commands.fun.EightBall;
import com.brokeassgeeks.snackbot.commands.fun.Fortune;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.commands.fun.Lart;
import com.brokeassgeeks.snackbot.commands.mcserver.Online;
import com.brokeassgeeks.snackbot.commands.mcserver.Status;
import com.brokeassgeeks.snackbot.commands.admin.SimpleCommandAdmin;
import com.brokeassgeeks.snackbot.commands.twitch.Twitch;
import com.brokeassgeeks.snackbot.listeners.*;
import com.brokeassgeeks.snackbot.Utils.MinecraftServerUtils;
import com.brokeassgeeks.snackbot.commands.fun.Insult;
import com.brokeassgeeks.snackbot.commands.mcserver.MinecraftServer;

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

    public static void main(String[] args) throws IOException, IrcException, LoginException, InterruptedException {
        seenDataBase = new SeenDataBase();

        logger.setLevel(Level.INFO);

        logger.info("Bot Starting....");
        ThreadedListenerManager listenerManager = new ThreadedListenerManager();

        Configuration configuration = Config.getInstance().load()
                .setListenerManager(listenerManager)
                .buildConfiguration();

        listenerManager.addListener(new UrlParserListener());
        listenerManager.addListener(new CommandListener());
        listenerManager.addListener(new ServerActivityListener());
        listenerManager.addListener(new SeenActivityListener());
        listenerManager.addListener(new DiscordBouncer());

        CommandManager.getInstance().addCommand(EightBall.class);
        CommandManager.getInstance().addCommand(Fortune.class);
        CommandManager.getInstance().addCommand(Status.class);
        CommandManager.getInstance().addCommand(Online.class);
        CommandManager.getInstance().addCommand(Mod.class);
        CommandManager.getInstance().addCommand(Mojang.class);
        CommandManager.getInstance().addCommand(Time.class);
        CommandManager.getInstance().addCommand(Seen.class);
        CommandManager.getInstance().addCommand(Twitch.class);
        CommandManager.getInstance().addCommand(Insult.class);
        CommandManager.getInstance().addCommand(Tell.class);
        CommandManager.getInstance().addCommand(Admin.class);
        CommandManager.getInstance().addCommand(Lart.class);
        CommandManager.getInstance().addCommand(Help.class);
        CommandManager.getInstance().addCommand(ServerAdmin.class);
        CommandManager.getInstance().addCommand(SimpleCommandAdmin.class);
        CommandManager.getInstance().addCommand(TriggerAdmin.class);


        bot = new PircBotX(configuration);

        jda = new JDABuilder().setBotToken("MjMzMDM3NDkxNDIxMTg0MDAw.CtXthw.iYKX7Qd7HMEXhO20SArSMkRHNGE").addListener(new SnackbotDiscord()).buildBlocking();
        bot.startBot();


    }

    public static MinecraftServer[] getServers() {
        return MinecraftServerUtils.loadServers();
    }

    public static ArrayList<String> getAdmins() {
        return AdminUtils.loadAdmins();
    }

}
