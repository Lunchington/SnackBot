package com.brokeassgeeks.snackbot.mcserver;
/*
    Author: zh32
    Edited by: Lunchington
    URL: https://github.com/zh32/TeleportSigns/blob/development/src/main/java/de/zh32/teleportsigns/server/status/QueryHandler.java
 */
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class ServerQuery {
    private final ServerConnection connection;
    private static final Gson gson = new Gson();

    public ServerQuery(ServerConnection connection) {
        this.connection = connection;
    }

    public void doHandShake() throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bs);
        out.write(0x00); //packet id
        writeVarInt(out, 4); //protocol version
        writeString(out, connection.host.getHostString());
        out.writeShort(connection.host.getPort());
        writeVarInt(out, 1); //target state 1
        sendPacket(bs.toByteArray());
    }

    public IStatusResponse doStatusQuery() throws IOException, InvalidResponseException {
        sendPacket(new byte[]{0x00});
        int size = readVarInt(connection.dataInputStream);
        int packetId = readVarInt(connection.dataInputStream);
        if (packetId != 0x00) {
            throw new IOException("Invalid packetId");
        }
        int stringLength = readVarInt(connection.dataInputStream);
        if (stringLength < 1) {
            throw new IOException("Invalid string length.");
        }
        byte[] responseData = new byte[stringLength];
        connection.dataInputStream.readFully(responseData);
        String jsonString = new String(responseData, Charset.forName("utf-8"));
        try {
            return gson.fromJson(jsonString, StatusResponse19.class);
        } catch (JsonSyntaxException ignored) {}
        try {
            return gson.fromJson(jsonString, StatusResponse17.class);
        } catch (JsonSyntaxException ignored) {}
        throw new InvalidResponseException();
    }

    private void sendPacket(byte[] data) throws IOException {
        writeVarInt(connection.dataOutputStream, data.length);
        connection.dataOutputStream.write(data);
    }

    private int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
            if ((k & 0x80) != 128) {
                break;
            }
        }
        return i;
    }

    private void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.write(paramInt);
                return;
            }
            out.write(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }

    private void writeString(DataOutputStream out, String string) throws IOException {
        writeVarInt(out, string.length());
        out.write(string.getBytes(Charset.forName("utf-8")));
    }
}
