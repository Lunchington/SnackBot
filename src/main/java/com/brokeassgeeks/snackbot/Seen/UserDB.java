package com.brokeassgeeks.snackbot.Seen;

import lombok.Data;

@Data
public class UserDB {

    private long id;
    private String lastNick;
    private String login;

    private String hostName;
    private long timeSeen;

    public UserDB(long id, String lastNick, String login, String hostName, long timeSeen) {
        this.id = id;
        this.lastNick = lastNick;
        this.login = login;
        this.hostName = hostName;
        this.timeSeen = timeSeen;
    }


}