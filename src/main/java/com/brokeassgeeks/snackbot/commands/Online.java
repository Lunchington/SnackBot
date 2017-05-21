package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.DataManager;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.MessageUtils;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.mcserver.*;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;

public class Online extends Command {
    public Online(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void processCommand() {


        String out ="";
        String serverPlayers;
        String DiscordOut = "";

        for (MinecraftServer s : DataManager.getInstance().getServers()) {
            serverPlayers ="";

            IStatusResponse response = null;

            try {
                response = new ServerConnection(s).getResponse();
            }
            catch (IOException | InvalidResponseException e) {
                e.printStackTrace();
            }

            if (s.hostAvailabilityCheck()) {
                out += String.format("<B><b>%s<N> - %s %s: ", s.getName(), s.getPack(), s.getVersion());

                if (response.modCount() > 0) {
                    out += String.format("<B><b>Num Mods:<N> %s (+%s libraries)", response.modCountActual(), response.modCount()  -  response.modCountActual() );
                }

               if (response.getOnlinePlayers() > 0) {
                    for (Player player : response.getOnlinePlayersName()) {
                        serverPlayers += String.format("%s ",player.getName());
                    }
                }
                if (!Utils.isEmpty(serverPlayers)) {
                    out += String.format("\r\n        <B><b>Users Online: <N><g>%s<N>", serverPlayers);
                }

            } else {
                out += String.format("<B><r>%s is down!<N>", s.getName());
            }
            if (isFromDiscord())
                 out += "\r\n\r\n";

        }
        if (!isFromDiscord())
            super.respond(DiscordOut);
        else
            super.respond(out);


    }
}
