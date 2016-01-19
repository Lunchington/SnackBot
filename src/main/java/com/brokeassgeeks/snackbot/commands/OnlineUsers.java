package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.ServerList;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;


public class OnlineUsers extends BotCommand {
    public OnlineUsers() {

        super("online");
        this.setDesc("Show users on servers");
    }


    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        String _output;
        String serverPlayers;

        for (ServerStatus.ServerInfo s : SnackBot.bot.cmdServerStatus.getServers()) {
            serverPlayers = "";
            _output = "";

            ServerList list = new ServerList();
            list.setAddress(new InetSocketAddress(s.host,s.port));

            try {
                ServerList.StatusResponse response = list.fetchData();

                List<ServerList.Player> players = response.getPlayers().getSample();
                if (players != null) {
                    for (ServerList.Player player: players) {
                        serverPlayers += player.getName() + " ";
                    }
                }
                _output = String.format("<B>%s<N> - %s: ",s.name,response.getDescription());

                if (response.getPlayers().getOnline() >0){
                    _output += "<B>";
                }
                _output += String.format("%s<N>/%s", response.getPlayers().getOnline(),response.getPlayers().getMax());

                if (!Utils.isEmpty(serverPlayers)) {
                _output += String.format("<B> Users Online: <N><g>%s<N>", serverPlayers);
            }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!Utils.isEmpty(_output)) {
                super.sendMessage(target, _output);
            } else {
                super.sendMessage(target, String.format("<B>%s is down!<N>",s.name));
            }




        }

    }
}
