package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;
import com.brokeassgeeks.snackbot.mcserver.ServerConnection;
import com.brokeassgeeks.snackbot.mcserver.StatusResponse;

/**
 * Created by abeha.
 */
public class Mod extends BotCommand {
    public Mod() {
        super("mod");
        setDesc("Get mod info from server");
    }
    @Override
    public void handleMessage(String channel, String sender, String login, String hostname, String args) {

        if (args.length() == 0) {
            super.sendMessage(channel, String.format("<b>USAGE:<N> %s <SERVER> <MOD>" , this.getFullCmd()));
            return;
        }
        String[] cmd = Utils.splitWords(args);

        MinecraftServer s = SnackBot.bot.cmdServerStatus.getServer(cmd[0]);

        if (s == null) {
            super.sendMessage(channel, String.format("<r>Invalid server: %s" , cmd[0]));
            return;
        }

        StatusResponse response = new ServerConnection(s).getResponse();

        if (cmd.length ==1) {
            super.sendMessage(channel, String.format("<B>Modlist for %s:<N> %s" , s.name, response.getModList()));
        } else {
            StatusResponse.Mods.ModInfo mod = response.getMod(cmd[1]);
            if (mod == null) {
                super.sendMessage(channel, String.format("<r>No such mod <B>%s<N><b> on %s" , cmd[1],cmd[0]));
            } else {
                super.sendMessage(channel, String.format("<B>%s<N> is using version <B><b>%s<N> of <B><b>%s<N>" , s.name,mod.version,mod.modid));

            }


        }
    }
}
