package com.brokeassgeeks.snackbot.commands.admin;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import com.brokeassgeeks.snackbot.Utils.MinecraftServerUtils;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class ServerAdmin extends Command {
    public ServerAdmin(GenericMessageEvent event, String[] args) { super(event, args); }
    public ServerAdmin(MessageReceivedEvent event, String[] args) { super(event, args); }

    @Override
    public void run() {
        if (!AdminUtils.isAdmin(ircEvent.getUser(), SnackBot.getAdmins()) &&
                !AdminUtils.isAdmin(discordEvent.getAuthor().getUsername(), SnackBot.getAdmins())) {
            return;
        }

        if (args.length < 4 ) {
            super.respond(String.format("<B><b>USAGE:<N> %s <SERVER> <OPTION> <SETTING>" ,args[0]));
            return;
        }
        String msg[] = ircEvent.getMessage().split(" ", 4);

        if(MinecraftServerUtils.setValue(msg[1],msg[2],msg[3])) {
            super.respond(String.format("<B><b>%s<N> set to <B><b>%s<N> for <B><b>%s<N>",msg[2],msg[3],msg[1]));
        }
        else {
            super.respond("<B><b>ERROR IN SETTINGS<N>");
        }




    }
}
