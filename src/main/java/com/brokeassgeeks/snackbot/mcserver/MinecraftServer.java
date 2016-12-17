package com.brokeassgeeks.snackbot.mcserver;

import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class MinecraftServer {
    private String name;
    private String pack;
    private String version;
    private String hostname;
    private int port;


    public MinecraftServer() {
        this.name = "";
        this.pack = "";
        this.version = "";
        this.hostname = "";
        this.port = 0;
    }

    public InetSocketAddress getHost() { return new InetSocketAddress(this.hostname,this.port); }

    public boolean isOnServer(String player) throws InvalidResponseException, IOException {
        IStatusResponse response =  new ServerConnection(this).getResponse();
        for (Player p : response.getOnlinePlayersName()) {
            if (p.getName().equalsIgnoreCase(player))
                return true;
        }
        return false;
    }

    public  boolean hostAvailabilityCheck() {
        Socket s = new Socket();
        try  {
            s.connect(getHost(),7000);

        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public String getSimplename() {
        String sName = this.name;
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match= pt.matcher(sName);

        while(match.find())
        {
            String s= match.group();
            sName=sName.replaceAll("\\"+s, "");
        }
        return sName.toUpperCase();
    }

}
