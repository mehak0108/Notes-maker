package com.example.mehak.notes_maker;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class GetKeyPhrases {

    static String accessKey = "bd73ce91f606400cba499f24618e8958";
    static String host = "https://southeastasia.api.cognitive.microsoft.com";

    static String path = "/text/analytics/v2.0/keyPhrases";

    public static String GetJson (String s) throws Exception {

        Documents documents = new Documents ();
        documents.add ("1", "en", "The Grand Hotel is a new hotel in the center of Seattle. It earned 5 stars in my review, and has the classiest decor I've ever seen.");
        documents.add ("2", "es", "Si usted quiere comunicarse con Carlos, usted debe de llamarlo a su telefono movil. Carlos es muy responsable, pero necesita recibir una notificacion si hay algun problema.");
        documents.add ("3", "en", "The Grand Hotel is a new hotel in the center of Seattle. It earned 5 stars in my review, and has the classiest decor I've ever seen.");

        String text = new Gson().toJson(documents);
        byte[] encoded_text = text.getBytes("UTF-8");

        URL url = new URL(host+path);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/json");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(encoded_text, 0, encoded_text.length);
        wr.flush();
        wr.close();

        StringBuilder response = new StringBuilder ();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return prettify(response.toString());
    }

    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(json_text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    /*public static void main (String[] args) {
        try {


            //String response = GetKeyPhrases (documents);
            System.out.println (prettify (response));
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }*/
    public static class Document {
        public String id, language, text;

        public Document(String id, String language, String text){
            this.id = id;
            this.language = language;
            this.text = text;
        }
    }

    public static class Documents {
        public List<Document> documents;

        public Documents() {
            this.documents = new ArrayList<Document>();
        }
        public void add(String id, String language, String text) {
            this.documents.add (new Document (id, language, text));
        }
    }

}
