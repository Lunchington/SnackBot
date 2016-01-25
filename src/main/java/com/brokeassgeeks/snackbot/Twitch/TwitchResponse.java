package com.brokeassgeeks.snackbot.Twitch;

import lombok.Data;

import java.util.Map;

@Data

public class TwitchResponse {
    private Map<String, String> _links;
    private TwitchStream stream;

    @Data
    public class TwitchStream {
        private String _id;
        private String _game;
        private String _viewers;
        private String created_at;
        private String video_height;
        private String average_fps;
        private Map<String, String> _links;
        private Map <String, String> preview;
        private TwitchChannel channel;
    }

    @Data
    public class TwitchChannel {
        private Map<String, String> _links;
        private String background;
        private String banner;
        private String broadcaster_language;
        private String display_name;
        private String logo;
        private String mature;
        private String status;
        private String partner;
        private String url;
        private String video_banner;
        private String _id;
        private String name;
        private String created_at;
        private String updated_at;
        private String delay;
        private String followers;
        private String profile_banner;
        private String getProfile_banner_background_color;
        private String views;
        private String language;
    }
}
