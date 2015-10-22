package com.pantsareoffensive.snackbot.commands;

import com.pantsareoffensive.snackbot.SnackBot;
import com.pantsareoffensive.snackbot.Utils.Utils;

public class NonCommand {
    private String[]  blacklist = { "goo.gl", "pastebin", "redd.it"};
    private String[] tomcomplaints = { ":(", "that's rude", "abuse!"};

    public void handleMessage( String target, String sender, String args) {
        String str = args.trim();
        if (sender.equalsIgnoreCase("tom")) {
            if (Utils.stringContainsItemFromList(str, this.tomcomplaints)) {
                SnackBot.bot.sendMessage(target, "tom: dont be such a " + SnackBot.bot.insult.getInsult());
                return;
            }
        }

        if (!Utils.stringContainsItemFromList(str, this.blacklist)) {
            String string = Utils.parseforHTML(str);
            if (!Utils.isEmpty(string)) {
                SnackBot.bot.currentTime = 0;
                SnackBot.bot.lastAction = System.currentTimeMillis();
                SnackBot.bot.sendMessage(target, string);
            }
        }
    }






}


