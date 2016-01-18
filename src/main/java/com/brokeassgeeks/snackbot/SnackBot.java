package com.brokeassgeeks.snackbot;

import com.brokeassgeeks.snackbot.Configuration.Config;

public class SnackBot  {
    public static Bot bot;
    public static  Config config;

   public static void main(String[] args)
    {
        config = new Config();
        bot = new Bot();
    }
}
