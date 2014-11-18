package com.zorzal.heartstrings.contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ariel on 13/11/2014.
 */
public class Contacts {

    public static List<Callin> CALLINS = new ArrayList<Callin>();
    public static List<Callout> CALLOUTS = new ArrayList<Callout>();

    static {
        CALLINS.add(new Callin("ariel","papa"));
        CALLINS.add(new Callin("slakat", "kathy"));

        CALLOUTS.add(new Callout("slakat", "kathy", "zorzal"));
    }

    static class Callin {
        private String username;
        private String name;
        private String nick;
        private String email;

        public Callin(String username, String name) {
            this.username = username;
            this.name = name;
            this.nick = name;
            this.email = "sample@uc.cl";
        }
        public String getUsername() { return username; }
        public String getName() { return name; }
        public String getNick() { return nick; }
        public String getEmail() { return email; }

        @Override
        public String toString() {
            return username + " " + name;
        }
    }

    static class Callout {
        private String username;
        private String name;
        private String nick;
        private String keyword;
        private String email;

        public Callout(String username, String name, String keyword) {
            this.username = username;
            this.name = name;
            this.nick = name;
            this.keyword = keyword;
            this.email = "sample@uc.cl";
        }

        public String getUsername() { return username; }
        public String getName() { return name; }
        public String getNick() { return nick; }
        public String getEmail() { return email; }
        public String getKeyword() { return keyword; }

        @Override
        public String toString() {
            return username + " " + name;
        }
    }
}

