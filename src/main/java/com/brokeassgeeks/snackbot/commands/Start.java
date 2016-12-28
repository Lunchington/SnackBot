package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.Utils.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;

/**
 * Created by lunchington on 11/3/16.
 */
public class Start extends Command {
    public Start(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void processCommand() {
        if (args.length <= 1) {
            super.respond(String.format("<B><b>USAGE:<N> %s <SERVER>" ,args[0]));
            return;
        }

        if (Utils.isServer(args[1])) {
            sendStart(args[1]);
            super.respond(String.format("Start issued for %s",args[1]));
        }
        else
        {
            super.respond(String.format("<B><b>INVALID SERVER:<N> %s" ,args[1]));
        }

    }

    private void sendStart(String server) {
        String command = String.format("sudo -u mcservers mark2 start %s/%s ", Config.MC_SERVER_DIR,server);

        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Boolean isAdminCommand() { return true;}
}
