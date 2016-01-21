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
            _output = "";

            StatusResponse response = new ServerConnection(s).getResponse();
            if (response == null) {
                super.sendMessage(target, String.format("<B>%s is down!<N>", s.name));
            } else {

                if (response.players.sample != null) {
                    for (StatusResponse.Players.Player player : response.players.sample) {
                        serverPlayers += String.format("%s ",player.name);
                    }
                }
                _output = String.format("<B>%s<N> - %s: ", s.name, response.description);

                if (response.players.online > 0) {
                    _output += "<B>";
                }
                _output += String.format("%s<N>/%s ", response.players.online, response.players.max);
                _output += String.format("<B>Num Mods:<N> %s ", response.modCount());

                if (!Utils.isEmpty(serverPlayers)) {
                    _output += String.format("<B>Users Online: <N><g>%s<N>", serverPlayers);
                }
                super.sendMessage(target, _output);

            }

        }
    }
}
