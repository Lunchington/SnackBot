package com.brokeassgeeks.snackbot.commands;

import lombok.Data;

import java.util.List;

/**
 * Created by Lunchington on 10/2/2016.
 */
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
