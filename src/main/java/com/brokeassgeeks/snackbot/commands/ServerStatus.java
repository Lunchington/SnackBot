package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.TimeDifference;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class ServerStatus extends Command {
    public ServerStatus(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void run() {

        if (args.length == 1) {
            super.respond(String.format("<B><b>Server Status:<N> %s", getServerStatus()));
            return;
        }

        MinecraftServer server = getServer(args[1]);
        String user = server.getLastactivity().getUser();
        long time = server.getLastactivity().getTime();
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

        for (MinecraftServer s : SnackBot.getServers()) {
            out += String.format("<B><b>%s<N> - %s: %s <N>",s.getName(),s.getPack(),(Utils.hostAvailabilityCheck(s.getHost()) ? "<g>Up! " : "<r>Down! "));
        }
        return out;
    }

    public MinecraftServer getServer(String string) {
        for (MinecraftServer s : SnackBot.getServers()) {
            if (s.getName().equalsIgnoreCase(string))
                return s;
        }
        return null;
    }

}
