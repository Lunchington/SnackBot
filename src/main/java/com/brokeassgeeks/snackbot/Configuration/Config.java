package com.brokeassgeeks.snackbot.Configuration;

import ch.qos.logback.classic.Logger;
import org.pircbotx.Configuration;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Config.class);

    private static final String configFile = "bot.config";
    public static Config instance;

    public static String CATCH_CHAR;
    public static String BOT_NICK;
    public static String BOT_USER;
    public static String BOT_REALNAME;
    public static String HOSTNAME;
    public static String PORT;
    public static String[] CHANNELS;
    public static String VERSION;
    public static int MESSAGECOUNT;
    public static int COMMAND_TIMEOUT;
    public static String PASTEBIN_API;
    public static String DISCORD_TOKEN;
    public static String ADMIN_CHANNEL_DISCORD;
    public static String ADMIN_CHANNEL_IRC;
    public static String MC_SERVER_DIR;
    public static String TWITCH_CLIENT_ID;

    private Properties prop;

    public static Config getInstance() {
        if(instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private Config() {
        prop = new Properties();
        File config = new File(configFile);
        prop = new Properties();

        if (!config.exists()) {
            logger.error("Cannot load file creating...");
            try {
                final boolean newFile = config.createNewFile();
                if (newFile)
                    setDefaults();
            } catch (IOException e) {
                logger.error("Cannot create config ...", e);
            }
        }
    }

    private void setDefaults() {

        prop.setProperty("nick", "SnackBot");
        prop.setProperty("user", "SnackBot");
        prop.setProperty("realname", "SnackBot");
        prop.setProperty("server", "localhost");
        prop.setProperty("port", "6667");
        prop.setProperty("channel", "#Lunchtest");
        prop.setProperty("version", "-[ SnackBot by Lunchington ]-");
        prop.setProperty("catchchar", "`");
        prop.setProperty("authserv", "");
        prop.setProperty("authpassword", "password");
        prop.setProperty("nickcomplete", "false");
        prop.setProperty("cmdtimeout", "10");
        prop.setProperty("messagecount", "1");
        prop.setProperty("pastebinapi","");
        prop.setProperty("discordtoken", "");

        prop.setProperty("discordAdmin", "");
        prop.setProperty("ircAdmin", "");
        prop.setProperty("mcserverdir", "/home/mcservers");
        prop.setProperty("twitchclientid", "");

        save();
    }

    public Configuration.Builder load() {
        FileInputStream input;

        try {
            logger.info("Loading config...");
            input = new FileInputStream(new File(configFile));
            prop.load(input);

        } catch (IOException e) {
            logger.error("Cannot load config ...", e);
        }


        BOT_NICK = prop.getProperty("nick");
        BOT_USER = prop.getProperty("user");
        BOT_REALNAME = prop.getProperty("realname");
        HOSTNAME = prop.getProperty("server");
        PORT = prop.getProperty("port");

        CHANNELS = prop.getProperty("channel").split(",",-1);
        VERSION = prop.getProperty("version");
        CATCH_CHAR = prop.getProperty("catchchar");
        COMMAND_TIMEOUT = Integer.parseInt(prop.getProperty("cmdtimeout"));
        MESSAGECOUNT = Integer.parseInt(prop.getProperty("messagecount"));

        PASTEBIN_API = prop.getProperty("pastebinapi");

        DISCORD_TOKEN = prop.getProperty("discordtoken");

        ADMIN_CHANNEL_DISCORD = prop.getProperty("discordAdmin");
        ADMIN_CHANNEL_IRC = prop.getProperty("ircAdmin");

        MC_SERVER_DIR = prop.getProperty("mcserverdir");

        TWITCH_CLIENT_ID = prop.getProperty("twitchclientid");

        Configuration.Builder b = new Configuration.Builder()
                .setName(BOT_NICK)
                .setRealName(BOT_REALNAME)
                .setLogin(BOT_USER)
                .setVersion(VERSION)
                .addServer(HOSTNAME,Integer.parseInt(PORT))
                .setAutoReconnect(true);

        for (String s : CHANNELS) {
            b.addAutoJoinChannel(s);
        }

        return b;
    }

    private void save() {

        FileOutputStream out;
        try {
            out = new FileOutputStream(new File(configFile));
            prop.store(out,null);
            out.close();
        } catch (IOException e) {
            logger.error("Cannot save config ...", e);
        }
    }
}
