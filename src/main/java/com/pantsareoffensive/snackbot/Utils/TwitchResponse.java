package com.pantsareoffensive.snackbot.Utils;

import java.util.Map;

public class TwitchResponse {
    public Map<String, String> _links;
    public TwitchStream stream;

    public class TwitchStream {
        public String _id;
        public String _game;
        public String _viewers;
        public String created_at;
        public String video_height;
        public String average_fps;
        public Map<String, String> _links;
        public Map <String, String> preview;
        public TwitchChannel channel;
    }

    public class TwitchChannel {
        public Map<String, String> _links;
        public String background;
        public String banner;
        public String broadcaster_language;
        public String display_name;
        public String logo;
        public String mature;
        public String status;
        public String partner;
        public String url;
        public String video_banner;
        public String _id;
        public String name;
        public String created_at;
        public String updated_at;
        public String delay;
        public String followers;
        public String profile_banner;
        public String getProfile_banner_background_color;
        public String views;
        public String language;
    }
}
