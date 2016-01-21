package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Utils.TimeDifference;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


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
    public void handleMessage(String channel, String sender, String login, String hostname, String args) {
        if (args.length() == 0) {
            super.sendMessage(channel, String.format("<B>Server Status:<N> %s", getServerStatus()));
            writeJson();
            return;
        }

        MinecraftServer server = getServer(args);
        if (sender != null) {
            String user = server.lastactivity.user;

            long time = server.lastactivity.time;
            long now = System.currentTimeMillis();

            String _output=String.format("There is no activity for %s",server.name);

            if (time != 0) {
                TimeDifference diff = new TimeDifference(Utils.getTime(now), Utils.getTime(time));
                String s = diff.getDifferenceString();

                _output =  String.format("<b>Last activity on <B>%s<N> was <B>%s<N> by <B>%s<B>",server.name,diff.getDifferenceString(),user);
            }
            super.sendMessage(channel,_output);

        }
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
            if (!file.exists()) {
                MinecraftServer minecraftServer = new MinecraftServer();
                MinecraftServer[] servers = new MinecraftServer[1];
                servers[0] = minecraftServer;

                writeJson(servers);
            }
            Reader jsonFile = new FileReader(file);

            this.servers = gson.fromJson(jsonFile, MinecraftServer[].class);

            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public  void writeJson() {
        writeJson(servers);
    }

    public void writeJson(MinecraftServer[] servers) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(servers);

        try {
            String jsonFile = "data/servers.json";
            FileOutputStream out = new FileOutputStream(jsonFile);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(s);
            writer.close();
            out.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateServerActivity(String server, String sender, long time) {
        for (int i = 0; i < this.servers.length; i++)
        {
            if(this.servers[i].name.equalsIgnoreCase(server)) {
                this.servers[i].lastactivity.time = time;
                this.servers[i].lastactivity.user = sender;
            }
        }
        writeJson();
    }
}
