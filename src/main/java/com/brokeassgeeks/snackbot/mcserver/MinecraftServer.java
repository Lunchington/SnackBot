package com.brokeassgeeks.snackbot.mcserver;

import java.net.InetSocketAddress;

public class MinecraftServer {
    public String name;
    public String pack;
    private String host;
    public int port;
    public LastActivity lastactivity;


    public MinecraftServer() {
        this.name = "";
        this.pack = "";
        this.host = "";
        this.port = 0;
        this.lastactivity = new LastActivity();
    }
    public InetSocketAddress getHost() { return new InetSocketAddress(this.host,this.port); }

    public boolean isOnServer(String player) {
        StatusResponse response =  new ServerConnection(this).getResponse();
        for (StatusResponse.Players.Player p : response.players.sample) {
            if (p.name.equalsIgnoreCase(player))
                return true;
        }

        return false;
    }

    public class LastActivity {
        public String user;
        public long time;
        public LastActivity() {
            this.user ="";
            this.time = 0;
        }

    }
}
