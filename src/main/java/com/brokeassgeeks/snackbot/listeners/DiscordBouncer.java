package com.brokeassgeeks.snackbot.listeners;

import ch.qos.logback.classic.Logger;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.SnackBot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.dv8tion.jda.entities.TextChannel;
import org.pircbotx.Channel;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.QuitEvent;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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

    @Override
    public void onMessage(MessageEvent event) {
        String msg = String.format("<%s> %s",event.getUser().getNick(), event.getMessage());
        if (!event.getMessage().startsWith(Config.CATCH_CHAR))
            sendMessagetoMirror(event.getChannel().getName(),msg);
    }


    @Override
    public void onJoin(JoinEvent event) {
        String msg = String.format("%s joined",event.getUser().getNick());
        sendMessagetoMirror(event.getChannel().getName(),msg);
    }

    @Override
    public void onPart(PartEvent event) {
        String msg = String.format("%s left the channel",event.getUser().getNick());
        sendMessagetoMirror(event.getChannel().getName(),msg);
    }

    @Override
    public void onQuit(QuitEvent event) {
        String msg = String.format("%s quit (%s)",event.getUser().getNick(), event.getReason());
        for (Channel c : event.getUser().getChannels())
            sendMessagetoMirror(c.getName(),msg);
    }


    private void sendMessagetoMirror(String chan, String msg) {
        String mirror = getMirror(chan);
        TextChannel dChan = getChannelByName(mirror);

        if (dChan != null)
            dChan.sendMessage(msg);
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

    private static TextChannel getChannelByName(String chan) {

        List<TextChannel> dChannels = SnackBot.getJda().getTextChannelsByName(chan);

        for (TextChannel c : dChannels) {
            if (c.getName().equalsIgnoreCase(chan))
                return c;
        }
        return  null;
    }


}
