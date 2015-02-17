package com.pantsareoffensive.snackbot;

import com.pantsareoffensive.snackbot.Configuration.Config;
import com.pantsareoffensive.snackbot.commands.BotCommand;
import com.pantsareoffensive.snackbot.commands.Mojang;
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

        this.commands.add(new Mojang());

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

    private BotCommand getBotCmd(String args)
    {
        for (BotCommand command : this.commands) {
            if (args.toLowerCase().startsWith(command.getCommandName())) {
                return command;
            }
        }
        return null;
    }

    protected void onMessage(String target, String sender, String login, String hostname, String message)
    {
        message = message.trim();
        if (message.startsWith(Config.CATCH_CHAR))
        {
            message = message.substring(Config.CATCH_CHAR.length());
            BotCommand cmd = getBotCmd(message);
            if (cmd != null) {
                if (message.length() == cmd.getCommandName().length()) {
                    message = "";
                } else {
                    message = message.substring(cmd.getCommandName()
                            .length() + 1);
                }
                cmd.handleMessage(target, sender, login, hostname, message);
                System.out.println("COMMAND FIRED: " + cmd.getCommandName());
            }


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
