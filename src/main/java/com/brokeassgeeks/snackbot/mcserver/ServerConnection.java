package com.brokeassgeeks.snackbot.mcserver;
/*
    Derived from
    Author: zh32
    Edited by: Lunchington
    URL: https://github.com/zh32/TeleportSigns/blob/development/src/main/java/de/zh32/teleportsigns/server/status/ServerListPing.java
 */
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerConnection {
    public final InetSocketAddress host;
    public Socket socket;
    public InputStream inputStream;
    public OutputStream outputStream;
    public int timeout;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;

    public ServerConnection(MinecraftServer server) {
        this.host = server.getHost();
        this.timeout = 7000;
    }

    public StatusResponse getResponse() {
        StatusResponse response;
        try {
            this.connect();
            ServerQuery query = new ServerQuery(this);
            query.doHandShake();
            response = query.doStatusQuery();
            this.disconnect();

        } catch (IOException e) {
            return null;
        }
        return response;
    }

    public void connect() throws IOException {
        socket = new Socket();
        socket.setSoTimeout(timeout);
        socket.connect(host, timeout);
        inputStream = socket.getInputStream();
        dataInputStream = new DataInputStream(inputStream);
        outputStream = socket.getOutputStream();
        dataOutputStream = new DataOutputStream(outputStream);
    }

    private void disconnect() throws IOException {
        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
    }
}
