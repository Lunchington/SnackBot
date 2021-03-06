package com.brokeassgeeks.snackbot.commands;


import com.brokeassgeeks.snackbot.Utils.MessageUtils;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.File;
import java.io.FileNotFoundException;

public class EightBall extends Command {

    public EightBall(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent, discordEvent, args); }
    @Override

    public void processCommand() {

        if (args.length == 1) {
            super.respond(String.format("<B><b>USAGE:<N> %s <QUESTION>", args[0]));
            return;
        }

        String s="";
        try {
            s = Utils.chooseRandomLine(new File("data/fun/8ball.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String out = String.format("shakes the magic 8 ball... %s",s);

        if (isFromDiscord())
            discordEvent.getChannel().sendMessage(MessageUtils.replaceTagsDiscord(out));
        else
            ((MessageEvent)ircEvent).getChannel().send().action(MessageUtils.replaceTags(out));
    }
}
