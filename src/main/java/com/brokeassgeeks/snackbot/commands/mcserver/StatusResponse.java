package com.brokeassgeeks.snackbot.commands.mcserver;

import java.util.List;

public interface StatusResponse {
    Integer getOnlinePlayers();

    Integer getMaxPlayers();

    String getDescription();

    List<Player> getOnlinePlayersName();

    public Integer modCount();

    Mods.ModInfo getMod(String arg);
    public String getModList();
}
