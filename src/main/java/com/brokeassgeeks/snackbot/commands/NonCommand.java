package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Bot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.SnackBot;

public class NonCommand {
    private String[]  blacklist = { "goo.gl", "pastebin", "redd.it"};
    private String[] tomcomplaints = { ":(", "that's rude", "abuse!"};

    public void handleMessage( String target, String sender, String args) {
        String str = args.trim();
        if (sender.equalsIgnoreCase("tom")) {
            if (Utils.stringContainsItemFromList(str, this.tomcomplaints)) {
                SnackBot.bot.sendMessage(target, "tom: dont be such a " + SnackBot.bot.insult.getRandomInsult());
                return;
            }
        }

        if (!Utils.stringContainsItemFromList(str, this.blacklist)) {
            String string = Utils.parseforHTML(str);
            if (!Utils.isEmpty(string)) {
                Bot.currentTime = 0;
                Bot.lastAction = System.currentTimeMillis();
                SnackBot.bot.sendMessage(target, string);
            }
        }
    }






}


