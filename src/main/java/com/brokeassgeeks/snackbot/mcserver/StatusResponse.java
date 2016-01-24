package com.brokeassgeeks.snackbot.mcserver;
/*
    Author: zh32
    Edited by: Lunchington
    URL: https://github.com/zh32/TeleportSigns/blob/development/src/main/java/de/zh32/teleportsigns/server/status/StatusResponse.java
 */
import lombok.Data;

import java.util.List;

@Data
public class StatusResponse {
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
        @Data
        public class Player {
            private String name;
            private String id;

        }
    }

    @Data
    public class Version {
        public String name;
        public String protocol;
    }

    @Data
    public class Mods {
        private String type;
        private List<ModInfo> modList;

        @Data
        public class ModInfo {
            private String modid;
            private String version;

        }
    }

    public int modCount() {
        return modinfo.modList.size();
    }

    public List<Players.Player> getOnlinePlayers() {
        return this.players.sample;
    }

    public String getModList() {
        String output ="";

        for (Mods.ModInfo s: modinfo.modList) {
            output += s.modid + " ";
        }
        return output;
    }

    public Mods.ModInfo getMod(String name) {
        for (Mods.ModInfo s: modinfo.modList) {
            if (s.modid.equalsIgnoreCase(name))
                return s;
        }
        return null;
    }
}