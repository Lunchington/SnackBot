package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.MinecraftServerUtils;
import com.brokeassgeeks.snackbot.commands.mcserver.MinecraftServer;
import com.brokeassgeeks.snackbot.commands.mcserver.ServerConnection;
import com.brokeassgeeks.snackbot.commands.mcserver.StatusResponse;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Mod extends Command {


    public Mod(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void run() {
        if (args.length <= 2) {
            super.respond(String.format("<B><b>USAGE:<N> %s <SERVER> <MOD>" , args[0]));
            return;
        }

        MinecraftServer s = MinecraftServerUtils.getServerbyName(SnackBot.getServers(),args[1]);

        if (s == null) {
            super.respond(String.format("<B><b>Invalid server:<N> %s" , args[1]));
            return;
        }

        StatusResponse response = new ServerConnection(s).getResponse();

        if (response == null) {
            super.respond(String.format("<B><r>%s is down!<N>", s.getName()));
            return;
        }

        String out;
        StatusResponse.Mods.ModInfo mod = response.getMod(args[2]);

        if (mod == null) {
           out = String.format("<B><b>%s<N> is not using <B><b>%s<N>" , s.getName(),args[2]);
        } else {
            out = String.format("<B><b>%s<N> is using version <B><b>%s<N> of <B><b>%s<N>" , s.getName(),mod.getVersion(),mod.getModid());
        }


        super.respond(out);
    }

}
