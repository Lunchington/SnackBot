package com.brokeassgeeks.snackbot.commands.mcserver;

import lombok.Data;

import java.util.List;

/**
 * Created by Lunchington on 10/2/2016.
 */

@Data
public class StatusResponse19 implements StatusResponse {

    private Description description;
    private Players players;
    private Version version;
    private String favicon;
    private int time;
    private Mods modinfo;

    @Override
    public Integer getOnlinePlayers() {
        return players.getOnline();
    }

    @Override
    public Integer getMaxPlayers() {
        return players.getMax();
    }

    public String getDescription() {
        return description.getText();
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
        private String name;
        private int protocol;
    }


    @Override
    public String getModList() {
        String output = "";

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
