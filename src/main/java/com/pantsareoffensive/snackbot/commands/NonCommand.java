package com.pantsareoffensive.snackbot.commands;

import com.pantsareoffensive.snackbot.SnackBot;
import com.pantsareoffensive.snackbot.Utils.Utils;

public class NonCommand {
    private String[]  blacklist = { "goo.gl", "pastebin", "redd.it"};

    public void handleMessage( String target, String sender, String args) {
        String str = args.trim();
        for (String key : SnackBot.bot.cmdServerStatus.servers.keySet())
        {
            if ((sender.equalsIgnoreCase(key)) || sender.toLowerCase().startsWith("tom")) {

                System.out.println("BOT DETECTED!: " + key);
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


