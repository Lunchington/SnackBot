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
        String msg[] = event.getMessage().split(" ", 4);

        if(MinecraftServerUtils.setValue(msg[1],msg[2],msg[3])) {
            super.respond(String.format("<B><b>%s<N> set to <B><b>%s<N> for <B><b>%s<N>",msg[2],msg[3],msg[1]));
        }
        else {
            super.respond("<B><b>ERROR IN SETTINGS<N>");
        }




    }
}
