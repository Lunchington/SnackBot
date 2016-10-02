package com.brokeassgeeks.snackbot.commands.mcserver;

import lombok.Data;

import java.util.List;

/**
 * Created by Lunchington on 10/2/2016.
 */

@Data
public class StatusResponse19 extends StatusResponse {
    private Description description;
    private int time;


    @Override
    public String getDescription() {
        return description.getText();
    }

    @Data
    public class Description {
        private String text;
    }


}
