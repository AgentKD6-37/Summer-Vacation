package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;



/*
 *File manager can import, read, write, or create files
 */

public class SaveEditor {

    private static String locationsJSON = "Assets/Locations.JSON";
    private static String NPCsJSON = "Assets/NPCs.JSON";
    private static String playerJSON = "Assets/Player.JSON";
    private static String playerInvPath = "Assets/Player_Items/player_inventory.txt";


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
     *These methods exist to return SPECIFIC JSON data to the calling method from the JSON asset files.
     */

    public static String getNewLocation(String zone, String location, String direction) {
        /*
         * I hear you. Why oh why are there so many variable declarations?
         * because as best as I can tell, this is the way to drill down into a JSON tree with JSON.simple
         * This function does this:
         * {zone:{location[1]:{directions:{**input**:**data we want**
         * and then returns the data to the game engine.
         *
         * Ask Michael if you need more information.
         */


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
        /*
         * FOR NOW:
         * this method just grabs *A* description from the JSON.
         * There is a todo: IF THERE ARE TWO DESCRIPTIONS Check if NPC is present -> if present, give open, else give closed.
         * This is not high on the priority list though so not too concerned if it doesn't get implemented now.
         */

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
         * Sorry. -MS
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

    public static JSONArray getNPCsName(String zone, String location) {
        JSONArray NPCname = null;

        JSONObject locationJSON = grabJSONData();//THIS IS THE WHOLE JSON FILE
        JSONObject locationZone = (JSONObject) locationJSON.get(zone); //JUST EVERYTHING IN OUR ZONE
        JSONArray locationArea = (JSONArray) locationZone.get(location); //JUST EVERYTHING IN OUR AREA
        JSONObject NPCshowing = (JSONObject) locationArea.get(4); //JUST THE NPC
        NPCname = (JSONArray) NPCshowing.get("NPCs");

        return NPCname;
    }




    public static String getNPCsDialog(String NPCname,int digNum) {
        String NPCdia="Sounds like you don't feel like to talk today";
        String digKey=Integer.toString(digNum);

        JSONObject npcJSON = grabNPC();//THIS IS THE WHOLE JSON FILE
        JSONArray characterArray= (JSONArray) npcJSON.get(NPCname);
        if (npcJSON.containsKey(NPCname)){
            JSONObject digJson=(JSONObject) characterArray.get(2);
            JSONObject digList=(JSONObject) digJson.get("dialogue");
            NPCdia = (String) digList.get(digKey);
        } else
            System.out.println("......\n.......\n............");
        return NPCdia;
    }


    public static String getLocationInvFilePath(String location, String zone) {
        /*
         * first we drill down to get the filepath:
         * visualization: {zone:{location[3]:{items:[**data we need**]
         *
         * next we use the filepath to get the txt file with the data
         *
         * Again, ask Michael for more info.
         */

        JSONObject locationJSON = grabJSONData();
        JSONObject zoneData = (JSONObject) locationJSON.get(zone); //null
        JSONArray locationData = (JSONArray) zoneData.get(location);
        JSONObject itemData = (JSONObject) locationData.get(3);

        return (String) itemData.get("items");
    }


    public static ArrayList<String> getPlayerItems(){
        ArrayList<String> inventory = new ArrayList<>();
        try (Scanner s = new Scanner(new FileReader(playerInvPath))) {
            while (s.hasNext()) {
                inventory.add(s.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public static ArrayList<String> getLocationItems(String location, String zone) {
        String path = getLocationInvFilePath(location, zone);
        ArrayList<String> inventory = new ArrayList<>();
        try (Scanner s = new Scanner(new FileReader(path))) {
            while (s.hasNext()) {
                inventory.add(s.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public static void updateLocationItems(String location, String zone, ArrayList<String> inventory) {
        String path = getLocationInvFilePath(location, zone);
        addToInventory(inventory,path);
    }

    public static void updatePlayerItems(ArrayList<String> inventory){
        String path = playerInvPath;
        addToInventory(inventory, path);
    }

    public static void addToInventory(ArrayList<String> inventory, String path) {
        try (FileWriter w = new FileWriter(path)){
            for (String item : inventory) {
                w.write(item + System.lineSeparator());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static JSONObject grabJSONData() {
        /*
         * We made this method to grab the whole dang JSON file to simplify some other methods from being a huge mess
         * of variable declarations.
         */


        JSONObject locationJSON = null;
        try {
            //create JSON Parser and file reader then create a JSON reader by combining them
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(locationsJSON);
            Object obj = jsonParser.parse(fileReader);
            locationJSON = (JSONObject) obj;//THIS IS THE WHOLE JSON FILE
            fileReader.close();

        } catch (IOException | ParseException e) {
            System.err.print("Error. Failed to load location.");
        }
        return locationJSON;
    }

    private static JSONObject grabNPC() {
        JSONObject npcJSON = null;
        try {
            //create JSON Parser and file reader then create a JSON reader by combining them
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(NPCsJSON);
            Object obj = jsonParser.parse(fileReader);
            npcJSON = (JSONObject) obj;//THIS IS THE WHOLE JSON FILE

        } catch (IOException | ParseException e) {
            System.err.print("Error. Failed to load location.");
        }
        return npcJSON;

    }

    public static String getSaveFile() {
        String tempSaveResponse = "getting save file";
        return tempSaveResponse;
    }

}