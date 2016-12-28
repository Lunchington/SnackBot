package com.brokeassgeeks.snackbot.mcserver;

import java.util.List;

public interface IStatusResponse {
    Integer getOnlinePlayers();

    Integer getMaxPlayers();

    String getDescription();

    List<Player> getOnlinePlayersName();

    Integer modCount();
    Integer modCountActual();

    Mods.ModInfo getMod(String arg);

    String getModList();

}
