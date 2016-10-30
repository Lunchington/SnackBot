package com.brokeassgeeks.snackbot.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lunchington on 10/29/2016.
 */
public final class Pastebin {

    private Pastebin() {}

    public static String putPaste(String name, String string) throws Exception {
        StringBuilder out = new StringBuilder();

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://pastebin.com/api/api_post.php");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<>(2);
        params.add(new BasicNameValuePair("api_dev_key", "1e90817c7621ade0adbd8b5e12634786"));
        params.add(new BasicNameValuePair("api_paste_private", "1"));
        params.add(new BasicNameValuePair("api_option", "paste"));

        params.add(new BasicNameValuePair("api_paste_name", name));
        params.add(new BasicNameValuePair("api_paste_code", string));




        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }

            }
        }
        return out.toString();
    }
}
