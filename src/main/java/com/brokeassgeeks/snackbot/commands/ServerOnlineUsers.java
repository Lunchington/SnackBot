package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;
import com.brokeassgeeks.snackbot.mcserver.ServerConnection;
import com.brokeassgeeks.snackbot.mcserver.StatusResponse;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class ServerOnlineUsers extends Command{
    public ServerOnlineUsers(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void init() {
        triggers.add("online");
        triggers.add("o");
    }

    @Override
    public void run() {
        if (!(event instanceof MessageEvent))
            return;

        String out;
        String serverPlayers;

        for (MinecraftServer s : SnackBot.getServers()) {
            serverPlayers = "";
            StatusResponse response = new ServerConnection(s).getResponse();

            if (response == null) {
                super.respond(String.format("<B><r>%s is down!<N>", s.getName()));
            } else {

                if (response.getOnlinePlayers() != null) {
                    for (StatusResponse.Players.Player player : response.getOnlinePlayers()) {
                        serverPlayers += String.format("%s ",player.getName());
                    }
                }
                System.out.println(response.getPlayers().getOnline());
                out = String.format("<B><b>%s<N> - %s: ", s.getName(), response.getDescription());

                if (response.getPlayers().getOnline() > 0) {
                    out += "<B>";
                }
                out += String.format("%s<N>/%s ", response.getPlayers().getOnline(), response.getPlayers().getMax());
                out += String.format("<B><b>Num Mods:<N> %s ", response.modCount());

                if (!Utils.isEmpty(serverPlayers)) {
                    out += String.format("<B><b>Users Online: <N><g>%s<N>", serverPlayers);
                }
                super.respond(out);
            }
        }

    }
}
