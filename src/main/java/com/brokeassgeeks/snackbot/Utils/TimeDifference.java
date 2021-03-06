package com.brokeassgeeks.snackbot.Utils;

/*
    From: http://stackoverflow.com/a/24781239
 */

import java.util.Date;

public class TimeDifference {
    int years;
    int months;
    int days;
    int hours;
    int minutes;
    int seconds;
    String differenceString;



    public TimeDifference(Date curdate, Date olddate) {

        float diff=curdate.getTime() - olddate.getTime();
        if (diff >= 0) {
            int yearDiff = Math.round( ( diff/ (365L*2592000000f))>=1?( diff/ (365L*2592000000f)):0);
            if (yearDiff > 0) {
                years = yearDiff;
                setDifferenceString(years + (years == 1 ? " year" : " years") + " ago");
            } else {
                int monthDiff = Math.round((diff / 2592000000f)>=1?(diff / 2592000000f):0);
                if (monthDiff > 0) {
                    if (monthDiff > 11)
                        monthDiff = 11;

                    months = monthDiff;
                    setDifferenceString(months + (months == 1 ? " month" : " months") + " ago");
                } else {
                    int dayDiff = Math.round((diff / (86400000f))>=1?(diff / (86400000f)):0);
                    if (dayDiff > 0) {
                        days = dayDiff;
                        if(days==30)
                            days=29;
                        setDifferenceString(days + (days == 1 ? " day" : " days") + " ago");
                    } else {
                        int hourDiff = Math.round((diff / (3600000f))>=1?(diff / (3600000f)):0);
                        if (hourDiff > 0) {
                            hours = hourDiff;
                            setDifferenceString( hours + (hours == 1 ? " hour" : " hours") + " ago");
                        } else {
                            int minuteDiff = Math.round((diff / (60000f))>=1?(diff / (60000f)):0);
                            if (minuteDiff > 0) {
                                minutes = minuteDiff;
                                setDifferenceString(minutes + (minutes == 1 ? " minute" : " minutes") + " ago");
                            } else {
                                int secondDiff =Math.round((diff / (1000f))>=1?(diff / (1000f)):0);
                                if (secondDiff > 0)
                                    seconds = secondDiff;
                                else
                                    seconds = 1;
                                setDifferenceString(seconds + (seconds == 1 ? " second" : " seconds") + " ago");
                            }
                        }
                    }

                }
            }

        }

    }
    public String getDifferenceString() {
        return differenceString;
    }

    private void setDifferenceString(String differenceString) {
        this.differenceString = differenceString;
    }
}