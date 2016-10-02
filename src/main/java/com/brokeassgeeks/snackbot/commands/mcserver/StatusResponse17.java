package com.brokeassgeeks.snackbot.commands.mcserver;
/*
    Author: zh32
    Edited by: Lunchington
    URL: https://github.com/zh32/TeleportSigns/blob/development/src/main/java/de/zh32/teleportsigns/server/status/StatusResponse.java
 */

import lombok.Data;

import java.util.List;

@Data
public class StatusResponse17 extends StatusResponse {

    private String description;

    @Override
    public String getDescription() {
        return description;
    }

}