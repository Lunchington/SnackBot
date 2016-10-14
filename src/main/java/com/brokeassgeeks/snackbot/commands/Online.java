package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.mcserver.*;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;

public class Online extends Command {
    public Online(GenericMessageEvent event, String[] args) {
        super(event, args);
    }
    public Online(MessageReceivedEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void run() {
        if (!(ircEvent instanceof MessageEvent)  && !isFromDiscord())
            return;

        String out;
        String serverPlayers;
        String DiscordOut = "";

        for (MinecraftServer s : SnackBot.getServers()) {
            serverPlayers ="";

            IStatusResponse response = null;

            try {
                response = new ServerConnection(s).getResponse();
            }
            catch (IOException e) {  }
            catch (InvalidResponseException e) { }

            if (response != null) {
                out = String.format("<B><b>%s<N> - %s %s: ", s.getName(), s.getPack(), s.getVersion());
                out += String.format("<B><b>Num Mods:<N> %s ", response.modCount());

               if (response.getOnlinePlayers() > 0) {
                    for (Player player : response.getOnlinePlayersName()) {
                        serverPlayers += String.format("%s ",player.getName());
                    }
                }
                if (!Utils.isEmpty(serverPlayers)) {
                    out += String.format("<B><b>Users Online: <N><g>%s<N>", serverPlayers);
                }

            } else {
                out = String.format("<B><r>%s is down!<N>", s.getName());
            }

            if (!isFromDiscord())
                super.respond(out);
            else
                DiscordOut += Utils.replaceTagsDiscord(out) +"\n";
        }
        if (isFromDiscord()) {
            super.respond(DiscordOut);
        }

    }
}
