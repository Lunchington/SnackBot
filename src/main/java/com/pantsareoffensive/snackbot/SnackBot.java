package com.pantsareoffensive.snackbot;

import com.pantsareoffensive.snackbot.Configuration.Config;
import org.jibble.pircbot.PircBot;

public class SnackBot  {
    public static PircBot bot;

   public static void main(String[] args)
    {
        Config config = new Config();
        bot = new Bot();
    }
}
