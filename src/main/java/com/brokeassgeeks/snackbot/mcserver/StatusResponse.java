package com.brokeassgeeks.snackbot.mcserver;

import java.util.List;

public class StatusResponse {
    public String description;
    public Players players;
    public Version version;
    public String favicon;
    public Mods modinfo;

    public int modCount() {
        return modinfo.modList.size();
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

    public class Players {
        public int max;
        public int online;
        public List<Player> sample;

        public class Player {
            public String name;
            public String id;

        }
    }

    public class Version {
        public String name;
        public String protocol;
    }

    public class Mods {
        public String type;
        public List<ModInfo> modList;

        public class ModInfo {
            public String modid;
            public String version;

        }
    }

}