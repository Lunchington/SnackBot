package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.MinecraftServerUtils;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class Console extends Command {

    public Console(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) {
        super(ircEvent, discordEvent, args);
    }

    @Override
    public void processCommand() {

        if (args.length <= 2) {
            super.respond(String.format("<B><b>USAGE:<N> %s <SERVER> <COMMAND>", args[0]));
            return;
        }

        MinecraftServer s = MinecraftServerUtils.getServerbyName(SnackBot.getServers(), args[1]);

        if (s == null) {
            super.respond(String.format("<B><b>Invalid server:<N> %s", args[1]));
            return;
        }

        String a = String.join(" ", args);
        String msg[] = a.split(" ", 3);

        String command = String.format("mark2 send -n %s %s", msg[1], msg[2]);
        try {

            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();

            // Grab output and print to display
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
