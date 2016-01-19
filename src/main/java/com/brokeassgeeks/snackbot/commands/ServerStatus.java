package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Utils.Utils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;


public class ServerStatus extends BotCommand {

    private ServerInfo[] servers;

    public ServerInfo[] getServers() {
        return this.servers;
    }

    public ServerStatus() {
        super("status");
        this.setDesc("Show server Status");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
            super.sendMessage(target, String.format("<B>Server Status:<N> %s", getServerStatus()));
    }

    public String getServerStatus() {
        String _output="";

        for (ServerInfo s : this.servers) {
            _output += String.format("%s - %s: %s <N>",s.name,s.pack,(Utils.hostAvailabilityCheck(s.host, s.port) ? "<g>Up! " : "<r>Down! "));
        }

        return _output;
    }

    @Override
    public void loadJson() {

        try {
            Gson gson = new Gson();
            File file = new File("data/servers.json");

            Reader jsonFile = new FileReader(file);


            this.servers = gson.fromJson(jsonFile, ServerInfo[].class);

            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }



    public class ServerInfo {
        public String name;
        public String pack;
        public String host;
        public int port;
    }
}
