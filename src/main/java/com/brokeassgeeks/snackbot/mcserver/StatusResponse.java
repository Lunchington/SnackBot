package com.brokeassgeeks.snackbot.mcserver;

import java.util.List;

public class StatusResponse {
    public String description;
    public Players players;
    public Version version;
    public String favicon;
    public Modinfo modinfo;

    public int modCount() {
        return modinfo.modList.size();
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

    public class Modinfo {
        public String type;
        public List<ModList> modList;

        private class ModList {
            public String modid;
            public String version;

        }
    }

}