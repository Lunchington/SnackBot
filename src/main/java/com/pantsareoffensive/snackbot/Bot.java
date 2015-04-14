package com.pantsareoffensive.snackbot;

import com.pantsareoffensive.snackbot.Configuration.Config;
import com.pantsareoffensive.snackbot.Utils.Utils;
import com.pantsareoffensive.snackbot.commands.*;
import org.jibble.pircbot.PircBot;

import java.util.ArrayList;
import java.util.List;

public class Bot extends PircBot {
    public List<BotCommand> commands;
    public ServerStatus cmdServerStatus;
    public NonCommand nonCommand;
    public TwitchCommand twitch;
    public  Insult insult;

    public static long coolDown = Config.COMMAND_TIMEOUT * 1000;
    public static long currentTime;
    public static long lastAction;

    public Bot() {
        this.commands = new ArrayList<>();

        this.cmdServerStatus = new ServerStatus();
        this.nonCommand = new NonCommand();
        this.twitch = new TwitchCommand();
        this.insult = new Insult();

        this.setAutoNickChange(true);
        this.setVerbose(true);

        this.setName(Config.BOT_NICK);
        this.setLogin(Config.BOT_USER);

        this.setVersion(Config.VERSION);

        this.commands.add(new Mojang());
        this.commands.add((cmdServerStatus));
        this.commands.add(new OnlineUsers());
        this.commands.add(twitch);
        this.commands.add(insult);
        this.commands.add(new TeamSpeak());
        this.commands.add(new Helper());

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
        currentTime = System.currentTimeMillis();

        if (Utils.isBot(sender)) {
            message = message.replaceAll("\\<.*?>","");
        }

        SnackBot.msgQ.sendNewMsg();


        if (currentTime - lastAction >= coolDown) {
            message = message.trim();
            if (message.startsWith(Config.CATCH_CHAR)) {
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
                    lastAction = System.currentTimeMillis();
                    currentTime = 0;
                }


            } else {
                this.nonCommand.handleMessage(target, sender, message);
            }

        }

    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message)
    {
        if (message.startsWith("reloadall")) {
            for (BotCommand command : this.commands) {
                command.reload();
                SnackBot.msgQ.loadJson();
                SnackBot.config.load();
            }
            SnackBot.bot.sendMessage(sender,"All Reloaded");
            return;
        }

        for (BotCommand command : this.commands) {
            if (message.startsWith(command.getCommandName())) {
                message = message.substring(command.getCommandName().length()).trim();
                command.handlePrivateMessage( sender, login, hostname,message);
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
