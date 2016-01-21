package com.brokeassgeeks.snackbot.mcserver;

import java.net.InetSocketAddress;

public class MinecraftServer {
    public String name;
    public String pack;
    private String host;
    public int port;
    public int time;
    public InetSocketAddress getHost() { return new InetSocketAddress(this.host,this.port); }
}
