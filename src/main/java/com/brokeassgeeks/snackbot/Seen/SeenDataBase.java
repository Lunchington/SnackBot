package com.brokeassgeeks.snackbot.Seen;

import com.almworks.sqlite4java.*;
import org.pircbotx.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
                        return new UserDB(st.columnInt(0), st.columnString(1),st.columnString(2), st.columnString(3), st.columnLong(4));
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
        final String q = "SELECT id FROM seen WHERE lastNick LIKE ? COLLATE NOCASE";
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

    public Long upDateUserbyID(final long id, final User user, final long time) {
        final String q = "UPDATE seen SET lastNick = ?, hostname =?, login = ?, time = ? WHERE id = ?";
        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, user.getNick());
                st.bind(2, user.getHostname());
                st.bind(3, user.getLogin());
                st.bind(4, time);
                st.bind(5, id);
                st.step();
                st.dispose();
                return connection.getLastInsertId();
            }
        }).complete();

    }


    public void processUserSeenRecord(final User user, final long time) {

        long userRecord = isUserInDB(user);
        if (userRecord > 0) {
            upDateUserbyID(userRecord,user, time);
        } else
        {
            addNewUser(user,time);
        }

    }


    public Long addNewUser(final User user, final long time) {
        final String q = "INSERT INTO seen(lastNick, login, hostname, time) VALUES (? , ?, ? , ?) ";

        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, user.getNick());
                st.bind(2, user.getLogin());
                st.bind(3, user.getHostname());
                st.bind(4, time);
                st.step();
                st.dispose();

                return connection.getLastInsertId();
            }
        }).complete();

    }

    public Long isUserInDB(final User user) {
        final String check = "SELECT id FROM seen WHERE lastNick LIKE ?";

        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(check);
                st.bind(1, user.getNick());

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

    public Long addTell(final String nick, final String string) {
        final String q = "INSERT INTO messages(nick, message) VALUES (? , ?) ";

        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, nick);
                st.bind(2, string);
                st.step();
                st.dispose();

                return connection.getLastInsertId();
            }
        }).complete();
    }

    public Long deleteTells(final String nick) {
        final String q = "DELETE FROM messages WHERE nick = ? ";

        return queue.execute(new SQLiteJob<Long>() {
            @Override
            protected Long job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, nick);
                st.step();
                st.dispose();

                return connection.getLastInsertId();
            }
        }).complete();
    }

    public List<String> getTells(final String user) {
        List<String> out = getTellsbyNick(user);
        deleteTells(user);
        return out;
    }

    public List<String> getTellsbyNick(final String nick) {

        final String q = "SELECT message FROM messages WHERE nick = ? COLLATE NOCASE";
        final List<String> out = new ArrayList<>();
        return queue.execute(new SQLiteJob<List<String>>() {
            @Override
            protected List<String> job(SQLiteConnection connection) throws Throwable {
                SQLiteStatement st = connection.prepare(q);
                st.bind(1, nick);
                while (st.step()) {
                    System.out.println(st.columnString(0));
                    out.add(st.columnString(0));
                }
                st.dispose();
                return out;
            }
        }).complete();
    }
}
