package com.brokeassgeeks.snackbot.Utils;

import com.almworks.sqlite4java.*;
import java.io.File;

public class SeenDataBase {
    File DBFILE = new File("db/database.db");
    SQLiteQueue queue = new SQLiteQueue(DBFILE);

    public SeenDataBase() {
        queue.start();

    }

    public UserDB getUserbyID(final long id) {
        if (id <1)
            return null;

        final String q = "SELECT * FROM seen WHERE id = ?";
        return queue.execute(new SQLiteJob<UserDB>() {
            @Override
            protected UserDB job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, id);
                try {
                    if (st.step()) {
                        return new UserDB(st.columnInt(0), st.columnString(1), st.columnString(2), st.columnLong(3));
                    } else
                        return null;
                } finally {
                    st.dispose();
                }
            }
        }).complete();

    }

    public UserDB getUserbyNick(String user) {
        Long id = getUserIdbyNick(user);
        return getUserbyID(id);
    }

    public Long getUserIdbyNick(final String nick) {
        final String q = "SELECT id FROM nicks WHERE nick = ? COLLATE NOCASE";
        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, nick);
                try {
                    if (st.step())
                        return st.columnLong(0);
                    return 0L;
                } finally {
                    st.dispose();
                }
            }
        }).complete();
    }

    public Long upDateUserbyID(final long id, final String nick, final String hostname, final long time) {
        final String q = "UPDATE seen SET lastNick = ?, time = ? WHERE id = ?";
        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, nick);
                st.bind(2, time);
                st.bind(3, id);
                st.step();
                st.dispose();
                return connection.getLastInsertId();
            }
        }).complete();

    }


    public void processUserSeenRecord(final String channel, final String nick, final String login, final String hostname, final long time) {

        final String loginHost = login + "@" + hostname;
        long userRecord = isUserInDB(loginHost);
        if (userRecord > 0) {
            UserDB currentUser = getUserbyID(userRecord);
            if (!nick.equalsIgnoreCase(currentUser.lastNick)) {
                addNewNickbyID(currentUser.id,nick);
            }
            upDateUserbyID(userRecord,nick,hostname, time);
        } else
        {
            Long newID = addNewUser(nick,loginHost,time);
            addNewNickbyID(newID,nick);
        }

    }


    public Long addNewUser(final String user, final String loginHost, final long time) {
        final String q = "INSERT INTO seen(lastNick, hostname, time) VALUES (? , ? , ?) ";

        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, user);
                st.bind(2, loginHost);
                st.bind(3, time);
                st.step();
                st.dispose();

                return connection.getLastInsertId();
            }
        }).complete();

    }

    public Long addNewNickbyID(final long id, final String nick) {
        final String q = "INSERT OR IGNORE INTO nicks(id, nick) VALUES (? , ?) ";

        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, id);
                st.bind(2, nick);
                st.step();
                st.dispose();
                return connection.getLastInsertId();
            }
        }).complete();
    }

    public Long isUserInDB(final String hostname) {
        final String check = "SELECT id FROM seen WHERE hostname = ?";

        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(check);
                st.bind(1, hostname);
                try {
                    if (st.step())
                        return st.columnLong(0);
                    return 0L;
                } finally {
                    st.dispose();
                }
            }
        }).complete();
    }
}
