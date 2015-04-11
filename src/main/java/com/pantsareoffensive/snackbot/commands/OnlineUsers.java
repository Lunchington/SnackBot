package com.pantsareoffensive.snackbot.commands;

import com.pantsareoffensive.snackbot.SnackBot;
import com.pantsareoffensive.snackbot.Utils.ServerList;
import com.pantsareoffensive.snackbot.Utils.Utils;
import org.jibble.pircbot.Colors;

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
            list.setAddress(new InetSocketAddress(s.host,Integer.parseInt(s.port)));

            try {
                ServerList.StatusResponse response = list.fetchData();

                List<ServerList.Player> players = response.getPlayers().getSample();
                if (players != null) {
                    for (ServerList.Player player: players) {
                        serverPlayers += player.getName() + " ";
                    }
                }
                _output = Colors.BOLD + s.name + ": " +Colors.NORMAL;

                if (response.getPlayers().getOnline() >0){
                    _output += Colors.BOLD;
                }
                _output += response.getPlayers().getOnline() + Colors.NORMAL + "/" + response.getPlayers().getMax();

                if (!Utils.isEmpty(serverPlayers)) {
                _output += Colors.BOLD + " Users Online: " +Colors.GREEN + serverPlayers + Colors.NORMAL;
            }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!Utils.isEmpty(_output)) {
                SnackBot.bot.sendMessage(target, _output);
            } else {
                SnackBot.bot.sendMessage(target, Colors.BOLD + s.name +  " is Down!");
            }




        }

    }
}
