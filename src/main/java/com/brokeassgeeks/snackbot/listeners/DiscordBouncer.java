package com.brokeassgeeks.snackbot.listeners;

import ch.qos.logback.classic.Logger;
import com.brokeassgeeks.snackbot.SnackBot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.dv8tion.jda.entities.TextChannel;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by Lunchington on 10/4/2016.
 */
public class DiscordBouncer extends ListenerAdapter {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(DiscordBouncer.class);

    public static Map<String,String> channelBridges;

    public DiscordBouncer() {

            try {
                Gson gson = new Gson();
                File file = new File("data/channelbridge.json");
                if (!file.exists()) {
                    logger.info("Cannot load admin file ... creating default");

                }

                Reader jsonFile = new FileReader(file);

                Type collectionType = new TypeToken<Map<String,String>>(){}.getType();

                Map<String,String> s = gson.fromJson(jsonFile, collectionType);
                jsonFile.close();
                channelBridges = s;


            } catch (Exception e) {
                logger.error("Cannot load Channel Bridge config ...", e);
            }
    }


    public void onMessage(MessageEvent event) {
        String mirror = DiscordBouncer.getMirror(event.getChannel().getName());

        List<TextChannel> dChannels = SnackBot.getJda().getTextChannelsByName(mirror);
        TextChannel dChan = getChannelByName(dChannels, mirror);

        String msg = String.format("[%s] <%s> %s",event.getChannel().getName(),event.getUser().getNick(), event.getMessage());

        if (dChan != null)
            dChan.sendMessage(msg);

    }

     public TextChannel getChannelByName(List<TextChannel> channels, String chan) {

        for (int i = 0; i < channels.size(); i++) {
            TextChannel c = channels.get(i);
            if (c.getName().equalsIgnoreCase(chan))
                return c;
        }
        return  null;
    }

    public static String getMirror(String chan) {

        for (Map.Entry<String,String> entry : DiscordBouncer.channelBridges.entrySet()) {
            if (chan.equalsIgnoreCase( entry.getKey()))
                return entry.getValue();
            if (chan.equalsIgnoreCase( entry.getValue()))
                return entry.getKey();
        }

        return null;
    }
}
