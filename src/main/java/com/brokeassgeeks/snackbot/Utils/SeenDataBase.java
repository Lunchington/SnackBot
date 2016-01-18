package com.brokeassgeeks.snackbot.Utils;

import java.io.File;
import java.io.IOException;

public class SeenDataBase {
    File DBFILE = new File("data/database.sqlite3");
    private String _delimiter = "[,]";

    public SeenDataBase()  {
        if (!DBFILE.isFile())
            try {
                DBFILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }


}
