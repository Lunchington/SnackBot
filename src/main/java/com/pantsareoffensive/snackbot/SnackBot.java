package com.pantsareoffensive.snackbot;

import com.pantsareoffensive.snackbot.Configuration.Config;
import com.pantsareoffensive.snackbot.Utils.MessageQueue;

public class SnackBot  {
    public static Bot bot;
    public static MessageQueue msgQ;

   public static void main(String[] args)
    {
        new Config();
        bot = new Bot();
        msgQ = new MessageQueue();
    }
}
