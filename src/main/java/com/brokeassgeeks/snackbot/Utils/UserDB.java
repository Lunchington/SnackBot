package com.brokeassgeeks.snackbot.Utils;


public class UserDB {
    public UserDB(long id, String lastNick, String hostName, long timeSeen) {
        this.id = id;
        this.lastNick = lastNick;
        this.hostName = hostName;
        this.timeSeen = timeSeen;
    }

    public long id;
    public String lastNick;
    public String hostName;
    public long timeSeen;
}