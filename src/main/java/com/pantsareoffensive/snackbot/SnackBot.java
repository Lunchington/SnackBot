package com.pantsareoffensive.snackbot;

import com.pantsareoffensive.snackbot.Configuration.Config;

public class SnackBot  {
    public static Bot bot;

   public static void main(String[] args)
    {
        new Config();
        bot = new Bot();
    }
}
