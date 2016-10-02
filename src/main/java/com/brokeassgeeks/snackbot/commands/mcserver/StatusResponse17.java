package com.brokeassgeeks.snackbot.commands.mcserver;
/*
    Author: zh32
    Edited by: Lunchington
    URL: https://github.com/zh32/TeleportSigns/blob/development/src/main/java/de/zh32/teleportsigns/server/status/StatusResponse.java
 */

import lombok.Data;

import java.util.List;

@Data
public class StatusResponse17 implements StatusResponse {
    private String description;
    private Players players;
    private Version version;
    private String favicon;
    private Mods modinfo;

    @Data
    public class Players {
        private int max;
        private int online;
        private List<Player> sample;
    }

    @Data
    public class Version {
        public String name;
        public String protocol;
    }


    @Override
    public Integer modCount() {
        if (modinfo == null)
            return 0;
        return modinfo.getModList().size();
    }

    @Override
    public Integer getOnlinePlayers() {
        return players.getOnline();
    }

    @Override
    public Integer getMaxPlayers() {
        return players.getOnline();
    }

    @Override
    public List<Player> getOnlinePlayersName() {
        return this.players.sample;
    }

    @Override
    public String getModList() {
        String output = null;

        for (Mods.ModInfo s: modinfo.getModList()) {
            output += s.getModid() + " ";
        }
        return output;
    }

    @Override
    public Mods.ModInfo getMod(String name) {
        for (Mods.ModInfo s: modinfo.getModList()) {
            if (s.getModid().equalsIgnoreCase(name))
                return s;
        }
        return null;
    }
}