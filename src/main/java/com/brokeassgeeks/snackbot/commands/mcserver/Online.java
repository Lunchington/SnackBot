package com.brokeassgeeks.snackbot.commands.mcserver;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.Command;
import com.brokeassgeeks.snackbot.commands.mcserver.MinecraftServer;
import com.brokeassgeeks.snackbot.commands.mcserver.ServerConnection;
import com.brokeassgeeks.snackbot.commands.mcserver.StatusResponse;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Online extends Command {
    public Online(GenericMessageEvent event, String[] args) {
        super(event, args);
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

                out = String.format("<B><b>%s<N> - %s %s: ", s.getName(), s.getName(), s.getVersion());
                out += String.format("<B><b>Num Mods:<N> %s ", response.modCount());

                if (!Utils.isEmpty(serverPlayers)) {
                    out += String.format("<B><b>Users Online: <N><g>%s<N>", serverPlayers);
                }
                super.respond(out);
            }
        }

    }
}
