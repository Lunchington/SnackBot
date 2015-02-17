package com.pantsareoffensive.snackbot;

import com.pantsareoffensive.snackbot.Configuration.Config;
import com.pantsareoffensive.snackbot.commands.BotCommand;
import org.jibble.pircbot.PircBot;

import java.util.ArrayList;
import java.util.List;

public class Bot extends PircBot {
    public List<BotCommand> commands;

    public Bot() {
        this.commands = new ArrayList<BotCommand>();

        this.setAutoNickChange(true);
        this.setVerbose(true);

        this.setName(Config.BOT_NICK);
        this.setLogin(Config.BOT_USER);

        this.setVersion(Config.VERSION);

        try
        {
            connect(Config.SERVER);
            joinChannel(Config.CHANNEL);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void onDisconnect() {
        while(!isConnected()) {
            try {
                reconnect();
                joinChannel(Config.CHANNEL);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
}
