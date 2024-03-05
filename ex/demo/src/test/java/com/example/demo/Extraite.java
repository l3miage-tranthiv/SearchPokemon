package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Extraite {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://pokeapi-proxy.freecodecamp.rocks/api/pokemon/25"); // URL pour Pikachu
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            JSONObject obj = new JSONObject(content.toString());
            
            // Extraction de la valeur de base_experience
            int baseExperience = obj.getInt("base_experience");
            System.out.println("Base Experience of Pikachu: " + baseExperience);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

