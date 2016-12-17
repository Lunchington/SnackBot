package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.Utils.Pastebin;
import com.brokeassgeeks.snackbot.Utils.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.File;
import java.nio.file.Files;

public class Log extends Command {
    public Log(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void processCommand() {
        String serverDir = Config.MC_SERVER_DIR;

        if (args.length < 2 ) {
            super.respond(String.format("<B><b>USAGE:<N> %s <SERVER> <TYPE>" ,args[0]));
            return;
        }

        if (!Utils.isServer(args[1])) {
            super.respond(String.format("<B><b>%s <N>is an invalid server", args[1]));
            return;
        }
        File file;
        if (args[2].equalsIgnoreCase("crash")) {
            serverDir += args[1].toUpperCase() + "/crash-reports";
            file = getLatestFilefromDir(serverDir);
        }
        else {
            serverDir += "/logs/latest.txt";
            file = new File(serverDir);
        }


        try {
            String contents = new String(Files.readAllBytes(file.toPath()));
           String out = Pastebin.putPaste(args[1], contents);
            super.respond("Log: " + out);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private File getLatestFilefromDir(String dirPath){
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }

    @Override
    public Boolean isAdminCommand() { return true;}

}
