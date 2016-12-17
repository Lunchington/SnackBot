package com.brokeassgeeks.snackbot.mcserver;

import com.brokeassgeeks.snackbot.Utils.TimeDifference;
import lombok.Data;

import java.util.Date;

@Data
public class LastActivity {

    private String user;
    private long time;

    public LastActivity() {
        this.user ="";
        this.time = 0;
    }


    public String toString() {
        long now = System.currentTimeMillis();
        String out="<B><b>There has been no activity";
        if (time != 0) {
            TimeDifference diff = new TimeDifference( new Date(now),  new Date(time));

            out =  String.format("Last activity was <B><b>%s<N> by <B><b>%s<B>",diff.getDifferenceString(),user);
        }
        return out;

    }
}







