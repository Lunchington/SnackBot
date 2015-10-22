package com.pantsareoffensive.snackbot.commands;

import com.pantsareoffensive.snackbot.SnackBot;
import com.wolfram.alpha.*;

public class WolframAlpha extends BotCommand {
    static public WAEngine engine;


    public WolframAlpha() {
        super("wa");
        setDesc("Sends a call to the Wolfram Alpha API.");
        engine = new WAEngine();
        engine.setAppID("XWVLT3-53WRPP4HQR");
        engine.addFormat("plaintext");
    }


    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {

        SnackBot.bot.sendMessage(target, getQuery(args));
    }

    private String getQuery(String message) {
        String output= "";

        WAQuery query = engine.createQuery();
        query.setInput(message);

        try {
            // This sends the URL to the Wolfram|Alpha server, gets the XML
            // result
            // and parses it into an object hierarchy held by the
            // WAQueryResult object.
            WAQueryResult queryResult = engine.performQuery(query);
            String result = "";

            if (queryResult.isError()) {
                result = "Query error, error code: "
                        + queryResult.getErrorCode() + ", error message: "
                        + queryResult.getErrorMessage();
            } else if (!queryResult.isSuccess()) {
                result = "Query was not understood; no results available.";
            } else {
                // Got a result.

                for (WAPod pod : queryResult.getPods()) {
                    if (!pod.isError()) {
                        if (pod.getID().equalsIgnoreCase("result")
                                || pod.getID().equalsIgnoreCase("solution")
                                || pod.getID().equalsIgnoreCase(
                                "DecimalApproximation"))
                            result += pod.getTitle() + ": ";
                        for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {
                                    if (pod.getID().equalsIgnoreCase(
                                            "result")
                                            || pod.getID()
                                            .equalsIgnoreCase(
                                                    "solution")
                                            || pod.getID()
                                            .equalsIgnoreCase(
                                                    "DecimalApproximation")) {
                                        result += (((WAPlainText) element)
                                                .getText()) + " ";
                                    }

                                }
                            }
                        }
                    }
                }
                if (!result.isEmpty()) {
                        for (String s : result.split("\n"))
                            output += s;
                }
            }

        } catch (WAException e) {

        }
        return output;
    }
}
