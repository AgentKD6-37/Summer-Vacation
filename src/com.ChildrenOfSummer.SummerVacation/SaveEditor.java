package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;


/*
 *File manager can import, read, write, or create files
 */

public class SaveEditor {


    private static String save = "Assets/save-game.properties";
    private static String locationsJSON = "Assets/Locations.JSON";



    //business methods

    public static void getAssetFile(String fileName) {
        try {
            String art = "Assets/" + fileName;
            var out = new BufferedOutputStream(System.out);
            Files.copy(Path.of(art), out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * These methods load sets of asset files for different menus.
     */

    public static void menuFiles() {
        getAssetFile("opening-menu.txt");
    }



    /*
     *These methods exist to return SPECIFIC data to the calling method from JSON files.
     */

    public static String getNewLocation(String zone, String location, String direction) {
        String newLocation;

        JSONObject locationJSON = grabJSONData();//THIS IS THE WHOLE JSON FILE
        JSONObject locationZone = (JSONObject) locationJSON.get(zone); //JUST EVERYTHING IN OUR ZONE
        JSONArray locationArea = (JSONArray) locationZone.get(location); //JUST EVERYTHING IN OUR AREA
        JSONObject locationDirection = (JSONObject) locationArea.get(1); //JUST THE DIRECTIONS
        JSONObject locationDirectionCardinal = (JSONObject) locationDirection.get("directions"); //JUST THE DIRECTION I WANT
        newLocation = (String) locationDirectionCardinal.get(direction);

        return newLocation;
    }

    public static String getLocationDescription(String location, String zone) {
        String descriptionText = null;

        JSONObject locationJSON = grabJSONData();
        JSONObject zoneData = (JSONObject) locationJSON.get(zone); //null
        JSONArray locationData = (JSONArray) zoneData.get(location);
        JSONObject descriptionData = (JSONObject) locationData.get(2);
        while (descriptionText == null) {
            descriptionText = (String) descriptionData.get("description");
            if (descriptionText == null) {
                descriptionText = (String) descriptionData.get("description_open");
                if (descriptionText == null) {
                    descriptionText = (String) descriptionData.get("description_closed");
                }
            }
        }
        System.out.println(descriptionText);

        return descriptionText;
    }

    public static String getNewZone(String location) {
        String zone = null;
        /*
         * This big dumb stupid function checks every zone in the game for the updated player location and then
         * returns the new zone location to the player object so that when you travers zones the game doesn't break.
         * It is very ugly, and can probably be done more efficiently. It was like midnight, and I was like, super tired.
         * Sorry.
         */

        try {
            JSONObject locationData = grabJSONData();
            JSONArray zones = new JSONArray();
            zones.add(locationData.get("Farm"));
            zones.add(locationData.get("Old Town"));
            zones.add(locationData.get("Suburb"));
            zones.add(locationData.get("New Suburb"));
            zones.add(locationData.get("Town Center"));
            zones.add(locationData.get("Wild Field"));
            for (int i = 0; i <= 5; i++) {
                JSONObject tempZone = (JSONObject) zones.get(i);
                if (tempZone.containsKey(location)) {
                    JSONArray tempLoc = (JSONArray) tempZone.get(location);
                    JSONObject insideTempLoc = (JSONObject) tempLoc.get(0);
                    zone = (String) insideTempLoc.get("location_zone");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zone;
    }

    public ArrayList getLocationItems(){
        ArrayList<String> temp = new ArrayList<>();
        return temp;
    }

    public void updateLocationItems(String item){
        

    }

    public void updatePlayerInventory(String item, String verb){

    }

    public static JSONObject grabJSONData() {
        JSONObject locationJSON = null;
        try {
            //create JSON Parser and file reader then create a JSON reader by combining them
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(locationsJSON);
            Object obj = jsonParser.parse(fileReader);
            locationJSON = (JSONObject) obj;//THIS IS THE WHOLE JSON FILE

        } catch (IOException | ParseException e) {
            System.err.print("Error. Failed to load location.");
        }
        return locationJSON;
    }

    public static String getSaveFile() {
        String tempSaveResponse = "getting save file";
        return tempSaveResponse;
    }
}