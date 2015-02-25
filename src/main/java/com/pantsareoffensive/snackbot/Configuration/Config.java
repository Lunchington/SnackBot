package com.pantsareoffensive.snackbot.Configuration;

import java.io.*;
import java.util.Properties;

public class Config {
    private Properties prop;

    private File file;
    private InputStream input = null;
    private FileOutputStream output = null;

    public static String CATCH_CHAR;

    public static String BOT_NICK;
    public static String BOT_USER;

    public static String BOT_REALNAME;
    public static String SERVER;
    public static String PORT;
    public static String CHANNEL;
    public static String VERSION;
    public static String AUTHSERV;
    public static String AUTHPASSWORD;
    public static String NICKCOMPLETE;
    public static int MESSAGECOUNT;

    public static int COMMAND_TIMEOUT;



    public Config() {
        prop = new Properties();

        try {
            file = new File("bot.config");
            if (!file.exists()) {
                System.out.println("Configuration file does not exist. Creating..");
                file.createNewFile();
                setDefaults();
            } else {
                input = new FileInputStream(file);
                prop.load(input);
                load();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void setDefaults() {

        prop.setProperty("nick", "SnackBot");
        prop.setProperty("user", "SnackBot");
        prop.setProperty("realname", "SnackBot");
        prop.setProperty("server", "pinebox.no-ip.org");
        prop.setProperty("port", "6667");
        prop.setProperty("channel", "#Lunchtest");
        prop.setProperty("version", "-[ SnackBot by Lunchington ]-");
        prop.setProperty("catchchar", "`");
        prop.setProperty("authserv", "");
        prop.setProperty("authpassword", "password");
        prop.setProperty("nickcomplete", "false");
        prop.setProperty("cmdtimeout", "10");
        prop.setProperty("messagecount", "1");

        try {
            output = new FileOutputStream(file);
            prop.store(new FileOutputStream(file), null);
        } catch (IOException e) {

        } finally {
            load();
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void load() {
        BOT_NICK = prop.getProperty("nick");
        BOT_USER = prop.getProperty("user");
        BOT_REALNAME = prop.getProperty("realname");
        SERVER = prop.getProperty("server");
        PORT = prop.getProperty("port");
        CHANNEL = prop.getProperty("channel");

        VERSION = prop.getProperty("version");

        CATCH_CHAR = prop.getProperty("catchchar");

        AUTHSERV = prop.getProperty("authserv");
        AUTHPASSWORD = prop.getProperty("authpassword");
        NICKCOMPLETE = prop.getProperty("nickcomplete");
        COMMAND_TIMEOUT = Integer.parseInt(prop.getProperty("cmdtimeout"));

        MESSAGECOUNT = Integer.parseInt(prop.getProperty("messagecount"));


    }
}
