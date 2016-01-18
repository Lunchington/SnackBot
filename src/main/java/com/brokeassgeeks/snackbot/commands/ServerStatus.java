package com.brokeassgeeks.snackbot.commands;

import com.google.gson.Gson;
import com.brokeassgeeks.snackbot.SnackBot;
import org.jibble.pircbot.Colors;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.Socket;


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
        String _output = "Server Status: ";
        for (ServerInfo s : this.servers) {
            boolean status = hostAvailabilityCheck(s.host, Integer.parseInt(s.port));
            _output = _output + s.name + " " + s.pack +": ";
            _output = _output + (status ? Colors.GREEN + "Up! " + Colors.NORMAL : Colors.RED + "Down! " + Colors.NORMAL);
        }

            SnackBot.bot.sendMessage(target, _output);

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

    public boolean hostAvailabilityCheck(String _server, int _port) {
        try (Socket s = new Socket(_server, _port)) {
            return true;
        } catch (IOException ex) {
        /* ignore */
        }
        return false;
    }

    public class ServerInfo {
        public String name;
        public String pack;
        public String host;
        public String port;
    }
}
