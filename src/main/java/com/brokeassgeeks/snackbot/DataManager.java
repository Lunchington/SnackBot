package com.brokeassgeeks.snackbot;

import ch.qos.logback.classic.Logger;
import com.brokeassgeeks.snackbot.mcserver.LastActivity;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;

import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class DataManager {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(DataManager.class);
    private static DataManager instance;

    private ArrayList<MinecraftServer> servers;
    private ArrayList<String> admins;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    private DataManager() {
        servers = loadServers();
    }

    public ArrayList<MinecraftServer> loadServers() {
        File folder = new File("data/mcservers");
        ArrayList<MinecraftServer> s = new ArrayList<>();

        File[] fServers = folder.listFiles((dir, name) -> name.endsWith(".mc"));

        if (fServers != null) {
            for (File fServer : fServers) {
                s.add(readServerFile(fServer));
            }
        }
        return  s;
    }


    public File getServerFilebyName(String name) {
        return new File("data/mcservers/" + name + ".mc" );
    }

    public  MinecraftServer readServerFile(File server) {
        Properties p = new Properties();
        MinecraftServer s = new MinecraftServer();
        try {
            FileReader f = new FileReader(server);
            p.load(f);

            s.setHostname(p.getProperty("host"));
            s.setPort(Integer.parseInt(p.getProperty("port")));
            s.setName(p.getProperty("name"));
            s.setPack(p.getProperty("pack"));
            s.setVersion(p.getProperty("version"));

            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public LastActivity getLastActivity(MinecraftServer server) {
        return getLastActivity(server.getSimplename());
    }

    private LastActivity getLastActivity(String server) {
        LastActivity l = new LastActivity();
        File activityFile = new File("data/mcservers/" + server + ".activity");
        Properties p = new Properties();
        try {
            FileReader f = new FileReader(activityFile);
            p.load(f);

            l.setUser(p.getProperty("user"));
            l.setTime(Long.parseLong(p.getProperty("time")));

            f.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return l;
    }
    public void saveLastActivity(MinecraftServer server, String user, long time) {
        saveLastActivity(server.getSimplename(), user, time);
    }

    private void saveLastActivity(String server, String user, long time) {
        try {
            File activityFile = new File("data/mcservers/" + server + ".activity");
            FileWriter writer = new FileWriter(activityFile);

            Properties p = new Properties();
            p.setProperty("user", user);
            p.setProperty("time", String.valueOf(time));

            p.store(writer,server+ " server activity");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveServers() {
        for (MinecraftServer s : servers) {
            saveServer(s);
        }
    }

    private void saveServer(MinecraftServer s) {
        try {
            File configFile = new File("data/mcservers/" + s.getSimplename() + ".mc");
            FileWriter writer = new FileWriter(configFile);

            Properties p = new Properties();
            p.setProperty("name", s.getName());

            p.setProperty("host",s.getHostname());
            p.setProperty("port" , String.valueOf(s.getPort()));

            p.setProperty("pack" , s.getPack());
            p.setProperty("version" , s.getVersion());
            p.store(writer,s.getName() + " server settings");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public MinecraftServer getServerbyName(String name) {
        for (MinecraftServer s : servers) {
            if (s.getName().equalsIgnoreCase(name))
                return s;
        }
        return null;
    }


    public  boolean setValue(String server, String setting, String value) {

        MinecraftServer s = getServerbyName(server);

        if (s != null) {
            switch (setting.toLowerCase()) {
                case "pack":
                    s.setPack(value);
                    break;
                case "host":
                    s.setHostname(value);
                    break;
                case "port":
                    s.setPort(Integer.parseInt(value));
                case "version":
                    s.setVersion(value);
            }

            saveServer(s);
        }

        return s != null;
    }

    public  void updateServerActivity(String server, String sender, long time) {
        MinecraftServer s = getServerbyName(server);
        saveLastActivity(s,sender,time);
    }

    public ArrayList<MinecraftServer> getServers() { return servers ; }
    public ArrayList<String> getAdmins() { return admins; }
}
