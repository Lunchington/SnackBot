package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.DataManager;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.TimeDifference;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.mcserver.LastActivity;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import javax.xml.crypto.Data;

public class Status extends Command {
    public Status(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void processCommand() {

        if (args.length == 1) {
            super.respond(String.format("<B><b>Server Status:<N> %s", getServerStatus()));
            return;
        }

        MinecraftServer server = DataManager.getInstance().getServerbyName(args[1]);
        LastActivity l = DataManager.getInstance().getLastActivity(server);

        String user = l.getUser();
        long time = l.getTime();
        long now = System.currentTimeMillis();

        String out=String.format("<B><b>There is no activity for %s",server.getName());

        if (time != 0) {
            TimeDifference diff = new TimeDifference(Utils.getTime(now), Utils.getTime(time));
            out =  String.format("<B>%s:<N> Last activity was <B><b>%s<N> by <B><b>%s<B>",server.getName(),diff.getDifferenceString(),user);
        }
        super.respond(out);
    }

    private String getServerStatus() {
        String out = "";

        for (MinecraftServer s : DataManager.getInstance().getServers()){
            out += String.format("<B><b>%s<N> - %s %s: %s <N>",s.getName(),s.getPack(),s.getVersion(),(s.hostAvailabilityCheck() ? "<g>Up! " : "<r>Down! "));
        }
        return out;
    }



}
