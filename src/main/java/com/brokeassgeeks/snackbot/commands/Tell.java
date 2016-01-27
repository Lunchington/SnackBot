package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tell extends Command{


    public Tell(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void init() {
        triggers.add("tell");
    }

    @Override
    public void run() {
        String sender = event.getUser().getNick();
        String target = args[1];

        if (args.length < 3) {
            super.respond(String.format("<B><b>USAGE:<N> %s <USER> <MESSAGE>" ,args[0]));
            return;
        }

        if (sender.equalsIgnoreCase(target)) {
            super.respond(String.format("<B><b>%s<N> talking to yourself again?", sender));
            return;
        }

        if (target.equalsIgnoreCase(event.getBot().getNick())) {
            super.respond(String.format("<B><b>%s<N>you can just tell me directly.", sender));
            return;
        }


        if (event.getBot().getUserChannelDao().containsUser(target)) {
            super.respond( String.format("<B><b>%s<N> why dont you tell <B><b>%s yourself!", sender,target));
            return;
        }


        String tellMsg[] = event.getMessage().split(" ", 3);
        SnackBot.getSeenDataBase().addTell(target,String.format("<B><b>%s, %s <N>said: %s",target, sender,tellMsg[2]));
        super.respond(String.format("<B><b>%s <N>I will let <B><b>%s<N> know when i see them" ,event.getUser().getNick(),args[1]));


    }



}
