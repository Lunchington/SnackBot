package com.brokeassgeeks.snackbot.commands.admin;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import com.brokeassgeeks.snackbot.Utils.MinecraftServerUtils;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.Command;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class ServerAdmin extends Command {
    public ServerAdmin(GenericMessageEvent event, String[] args) { super(event, args); }

    @Override
    public void run() {
        if (!AdminUtils.isAdmin(event.getUser(), SnackBot.getAdmins()) || Utils.isBot(sender))
            return;

        if (args.length < 4 ) {
            super.respond(String.format("<B><b>USAGE:<N> %s <SERVER> <OPTION> <SETTING>" ,args[0]));
            return;
        }

        if(MinecraftServerUtils.setValue(args[1],args[2],args[3])) {
            super.respond(String.format("<B><b>%s<N> set to <B><b>%s<N> for <B><b>%s<N>",args[2],args[3],args[1]));
        }
        else {
            super.respond("<B><b>ERROR IN SETTINGS<N>");
        }




    }
}
