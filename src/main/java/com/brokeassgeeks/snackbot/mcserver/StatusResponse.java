package com.brokeassgeeks.snackbot.mcserver;

import lombok.Data;

import java.util.List;


public class StatusResponse implements IStatusResponse{
    private Players players;
    private Version version;
    private String favicon;
    private Mods modinfo;

    @Override
    public Integer getOnlinePlayers() {
        return players.getOnline();
    }

    @Override
    public Integer getMaxPlayers() {
        return players.getMax();
    }

    @Override
    public String getDescription() {
        return null;
    }


    @Override
    public List<Player> getOnlinePlayersName() {
        return this.players.sample;
    }


    @Override
    public Integer modCount() {
        if (modinfo == null)
            return 0;
        return modinfo.getModList().size();
    }

    @Override
    public Mods.ModInfo getMod(String name) {
        for (Mods.ModInfo s: modinfo.getModList()) {
            if (s.getModid().equalsIgnoreCase(name))
                return s;
        }
        return null;
    }

    @Override
    public String getModList() {
        String output = null;

        for (Mods.ModInfo s: modinfo.getModList()) {
            output += s.getModid() + " ";
        }
        return output;
    }

    @Data
    public class Description {
        private String text;
    }

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
}
