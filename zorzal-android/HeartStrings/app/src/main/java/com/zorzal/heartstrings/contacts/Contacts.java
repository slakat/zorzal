package com.zorzal.heartstrings.contacts;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
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

    }

    public static void update(){
        new updateContacts().execute();
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

        public Callout(String username, String name, String keyword, String email) {
            this.username = username;
            this.name = name;
            this.nick = name;
            this.keyword = keyword;
            this.email = email;
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



    static class updateContacts extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String ...query) {
            try {

                URL url= new URL("http://zorzal.herokuapp.com/api/users");
                Log.i("Zorzal - url", url.toString());
                InputStream content = null;
                HttpResponse response=null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    response = httpclient.execute(new HttpGet(url.toString()));
                } catch (Exception e) {
                    Log.i("[GET REQUEST]", "Network exception", e);
                }


                String json_string = EntityUtils.toString(response.getEntity());
                int statusCode = response.getStatusLine().getStatusCode();
                Log.i("STATUS", String.valueOf(statusCode));
                JSONArray json = new JSONArray(json_string);

                if(json != null){
                    try{
                        Log.i("Zorzal - response",json.toString());


                        CALLOUTS.clear();

                        for (int i=0; i < json.length(); i++)
                        {
                            JSONObject oneObject = json.getJSONObject(i);
                            // get all value here
                            String name=oneObject.getString("name");
                            String username=oneObject.getString("username");
                            String email=oneObject.getString("email");

                            // get other values from jsonobject in same way

                            CALLOUTS.add(new Callout(name, username,"friend", email));
                        }



                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                this.exception = e;
            }
            return null;
        }


    }

}

