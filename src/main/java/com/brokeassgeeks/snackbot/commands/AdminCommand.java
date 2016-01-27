package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class AdminCommand extends Command{
    public AdminCommand(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void init() {
        triggers.add("admin");
        triggers.add("a");
    }

    @Override
    public void run() {
        if (!AdminUtils.isAdmin(event.getUser(), SnackBot.getAdmins())) {
            return;
        }

        if (args.length < 3) {
            super.respond(String.format("<B><b>USAGE:<N> %s add <USER>",args[0]));
        } else {

            if(event.getBot().getUserChannelDao().containsUser(args[2])) {
                User u = event.getBot().getUserChannelDao().getUser(args[2]);
                String uMask = String.format("%s!%s@%s",u.getNick(),u.getLogin(),u.getHostname());
                if(AdminUtils.isAdmin(u, SnackBot.getAdmins())) {
                    super.respond(String.format("<B><b>User:<N> %s is already an admin",u.getNick()));
                } else {
                    SnackBot.getAdmins().add(uMask);
                    AdminUtils.writeAdmins(SnackBot.getAdmins());
                    super.respond(String.format("<B><b>User:<N> %s added as BotAdmin",u.getNick()));
                }
            } else {
                super.respond(String.format("<B><b>Invalid user:<N> %s",args[2]));
            }
        }

    }
}
