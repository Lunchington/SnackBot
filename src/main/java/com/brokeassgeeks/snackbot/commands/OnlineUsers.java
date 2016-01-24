package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;
import com.brokeassgeeks.snackbot.mcserver.ServerConnection;
import com.brokeassgeeks.snackbot.mcserver.StatusResponse;

public class OnlineUsers extends BotCommand {
    public OnlineUsers() {
        super("online");
        this.setDesc("Show users on servers");

    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        String _output;
        String serverPlayers;

        for (MinecraftServer s : SnackBot.bot.cmdServerStatus.getServers()) {
            serverPlayers = "";

            StatusResponse response = new ServerConnection(s).getResponse();
            if (response == null) {
                super.sendMessage(target, String.format("<B><r>%s is down!<N>", s.getName()));
            } else {

                if (response.getOnlinePlayers() != null) {
                    for (StatusResponse.Players.Player player : response.getOnlinePlayers()) {
                        serverPlayers += String.format("%s ",player.getName());
                    }
                }
                _output = String.format("<B><b>%s<N> - %s: ", s.getName(), response.getDescription());

                if (response.getPlayers().getOnline() > 0) {
                    _output += "<B>";
                }
                _output += String.format("%s<N>/%s ", response.getOnlinePlayers(), response.getPlayers().getMax());
                _output += String.format("<B><b>Num Mods:<N> %s ", response.modCount());

                if (!Utils.isEmpty(serverPlayers)) {
                    _output += String.format("<B><b>Users Online: <N><g>%s<N>", serverPlayers);
                }
                super.sendMessage(target, _output);

            }

        }
    }
}
