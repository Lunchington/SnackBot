package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.Utils.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lunchington on 11/3/16.
 */
public class Stop extends Command {
    public Stop(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void processCommand() {
        if (args.length <= 1) {
            super.respond(String.format("<B><b>USAGE:<N> %s <SERVER>" ,args[0]));
            return;
        }

        if (Utils.isServer(args[1])) {
            sendStop(args[1]);
            super.respond(String.format("Stop issued for %s",args[1]));
        }
        else
        {
            super.respond(String.format("<B><b>INVALID SERVER:<N> %s" ,args[1]));
        }

    }

    private void sendStop(String server) {
        String command = String.format("mark2 stop -n %s ", server);

        try {
            Runtime.getRuntime().exec(command);
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Boolean isAdminCommand() { return true;}
}
