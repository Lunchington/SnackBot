package com.pantsareoffensive.snackbot.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pantsareoffensive.snackbot.SnackBot;
import org.jibble.pircbot.Colors;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;


public class ServerStatus extends BotCommand {

    public Map<String, Map<String, String>> servers;

    public ServerStatus() {
        super("status");
    }


    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        String _output = "Server Status: ";
        for (String key : this.servers.keySet()) {
            boolean status = hostAvailabilityCheck((String) ((Map) this.servers.get(key)).get("host"), Integer.parseInt((String) ((Map) this.servers.get(key)).get("port")));
            _output = _output + ((Map) this.servers.get(key)).get("name") + ": ";
            _output = _output + (status ? Colors.GREEN + "Up! " + Colors.NORMAL : Colors.RED + "Down! " + Colors.NORMAL);
        }

            SnackBot.bot.sendMessage(target, _output);

    }

    public void loadJson() {

        try {
            Gson gson = new Gson();
            File file = new File("data/servers.json");

            System.out.println(file.getAbsolutePath());
            Reader jsonFile = new FileReader(file);

            Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();

            this.servers = gson.fromJson(jsonFile, type);

            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static boolean hostAvailabilityCheck(String _server, int _port) {
        try (Socket s = new Socket(_server, _port)) {
            return true;
        } catch (IOException ex) {
        /* ignore */
        }
        return false;
    }
}
