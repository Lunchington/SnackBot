package com.brokeassgeeks.snackbot.twitch;

import lombok.Data;

import java.util.Map;

@Data

public class TwitchResponse {
    private Map<String, String> _links;
    private TwitchStream stream;

    @Data
    private class TwitchStream {
        private long _id;
        private String game;
        private int viewers;
        private int video_height;
        private float average_fps;
        private float delay;
        private String created_at;
        private boolean is_playlist;
        private Map <String, String> preview;
        private TwitchChannel channel;
        private Map<String, String> _links;
    }

    @Data
    private class TwitchChannel {
        private boolean mature;
        private boolean partner;
        private String status;
        private String broadcaster_language;
        private String display_name;
        private String game;
        private String language;
        private long _id;
        private String name;
        private String created_at;
        private String updated_at;
        private float delay;
        private String logo;
        private String banner;
        private String video_banner;
        private String background;
        private String profile_banner;
        private String Profile_banner_background_color;
        private String url;
        private int followers;
        private long views;
        private Map<String, String> _links;
    }
}
