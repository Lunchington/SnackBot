package com.brokeassgeeks.snackbot.mcserver;

import lombok.Data;


@Data
public class StatusResponse19 extends StatusResponse {
    private Description description;
    private int time;


    @Override
    public String getDescription() {
        return description.getText();
    }

    @Data
    private class Description {
        private String text;
    }


}
