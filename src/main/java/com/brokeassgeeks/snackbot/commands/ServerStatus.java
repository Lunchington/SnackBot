package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;


public class ServerStatus extends BotCommand {

    private MinecraftServer[] servers;

    public MinecraftServer[] getServers() {
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

        for (MinecraftServer s : this.servers) {
            _output += String.format("%s - %s: %s <N>",s.name,s.pack,(Utils.hostAvailabilityCheck(s.getHost()) ? "<g>Up! " : "<r>Down! "));
        }

        return _output;
    }
    public MinecraftServer getServer(String string) {
        for (MinecraftServer s : this.servers) {
            if (s.name.equalsIgnoreCase(string))
                return s;
        }
        return null;
    }

    @Override
    public void loadJson() {

        try {
            Gson gson = new Gson();
            File file = new File("data/servers.json");

            Reader jsonFile = new FileReader(file);


            this.servers = gson.fromJson(jsonFile, MinecraftServer[].class);

            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


}
