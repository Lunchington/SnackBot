package com.brokeassgeeks.snackbot;

import com.brokeassgeeks.snackbot.Utils.SeenDataBase;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.*;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.commands.fun.*;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import java.util.ArrayList;
import java.util.List;

public class Bot extends PircBot {
    public List<BotCommand> commands;
    public ServerStatus cmdServerStatus;
    public NonCommand nonCommand;
    public TwitchCommand twitch;
    public Insult insult;
    public SeenDataBase seenDataBase;

    public static long coolDown = Config.COMMAND_TIMEOUT * 1000;
    public static long currentTime;
    public static long lastAction;

    public Bot() {
        this.seenDataBase =  new SeenDataBase();
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
        this.commands.add(new Discord());
        this.commands.add(new Time());

        this.commands.add(new Helper());
        this.commands.add(new Seen());
        this.commands.add(new EightBall());
        this.commands.add(new Mod());
        this.commands.add(new Fortune());

        try
        {
            connect(Config.SERVER);
            for(String s: Config.CHANNEL)
                joinChannel(s);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private BotCommand getBotCmd(String args)
    {
        if(args.startsWith(Config.CATCH_CHAR))
            args = args.substring(Config.CATCH_CHAR.length());

        for (BotCommand command : this.commands) {
            if (args.toLowerCase().startsWith(command.name)) {
                return command;
            }
        }
        return null;
    }

    protected void onMessage(String target, String sender, String login, String hostname, String message)
    {
        currentTime = System.currentTimeMillis();
        message = message.trim();

        if (Utils.isBot(sender)) {
            String bot = sender;
            boolean update = false;

            if (message.startsWith("<") && message.contains(">")) {
                sender = message.substring(message.indexOf("<") + 1, message.indexOf(">"));
                message = message.replaceAll("^<.*?>", "").trim();
                update = true;
            }

            if (message.endsWith("the game") && !update) {
                String arr[] = message.split(" ", 2);
                sender =arr [0];
                message = arr[1];
                update = true;
            }

            if(update)
                SnackBot.bot.cmdServerStatus.updateServerActivity(bot, sender, System.currentTimeMillis());
        }

        if (currentTime - lastAction >= coolDown) {
            if(!message.startsWith(Config.CATCH_CHAR)) {
                this.nonCommand.handleMessage(target, sender, message);
                return;
            }

            BotCommand cmd = getBotCmd(message);
            if (cmd != null) {
                message = message.substring(cmd.name.length()+1).trim();
                cmd.handleMessage(target, sender, login, hostname, message);
                lastAction = System.currentTimeMillis();
                currentTime = 0;
            }
        }
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message)
    {
        if (message.startsWith("reloadall")) {
            for (BotCommand command : this.commands) {
                command.reload();
                SnackBot.config.load();
            }
            SnackBot.bot.sendMessage(sender,"All Reloaded");
            return;
        }

        for (BotCommand command : this.commands) {
            if (message.startsWith(command.name)) {
                message = message.substring(command.name.length()).trim();
                command.handlePrivateMessage( sender, login, hostname,message);
            }
        }
    }

    @Override
    public void onDisconnect() {
        while(!isConnected()) {
            try {
                reconnect();
                for(String s: Config.CHANNEL)
                    joinChannel(s);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    @Override
    public void onPart(String channel, String sender, String login, String hostname) {
        seenDataBase.processUserSeenRecord(channel, sender, login,hostname, System.currentTimeMillis());
    }


    @Override
    public void onJoin(String channel, String sender, String login, String hostname) {
        seenDataBase.processUserSeenRecord(channel, sender,login, hostname, System.currentTimeMillis());
    }

    @Override
    public void onNickChange(String oldNick, String login, String hostname, String newNick) {
        seenDataBase.processUserSeenRecord("",newNick, login, hostname, System.currentTimeMillis() );

    }

    @Override
    public void onQuit(String nick, String login, String hostname, String reason) {
        seenDataBase.processUserSeenRecord("", nick,login, hostname, System.currentTimeMillis() );
    }

    public boolean isUserInChannel(String channel, String user) {
        User[] users = SnackBot.bot.getUsers(channel);
        for(User u: users) {
            if(u.getNick().equalsIgnoreCase(user))
                return true;
        }
        return false;
    }


}
