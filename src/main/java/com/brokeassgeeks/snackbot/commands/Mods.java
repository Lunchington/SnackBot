package com.brokeassgeeks.snackbot.commands;

import lombok.Data;

import java.util.List;

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
