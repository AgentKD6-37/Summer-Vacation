package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.sound.sampled.*;
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
    private static JSONParser jsonParser = new JSONParser();

    /*
     * Simple text loader for use by art or text callers
     */

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
     * These methods load sets of asset files for different menus/locations.
     */

    public static void menuFiles() {
        getAssetFile("opening-menu.txt");
    }


    /*
     *These methods exist to return or update SPECIFIC JSON data to the calling method from the JSON asset files.
     */

    private static JSONObject grabJSONData() {
        /*
         * We made this method to grab the whole dang JSON file to simplify some other methods from being a huge mess
         * of variable declarations. -MS
         */

        JSONObject locationJSON = null;
        try {
            //create JSON Parser and file reader then create a JSON reader by combining them
            FileReader fileReader = new FileReader(locationsJSON);
            Object obj = jsonParser.parse(fileReader);
            locationJSON = (JSONObject) obj;//THIS IS THE WHOLE JSON FILE
            fileReader.close();

        } catch (IOException | ParseException e) {
            System.err.print("Error. Failed to load location.");
        }
        return locationJSON;
    }

    public static String getNewLocation(String zone, String location, String direction) {
        /*
         * I hear you. Why oh why are there so many variable declarations?
         * because as best as I can tell, this is the way to drill down into a JSON tree with JSON.simple
         * This function does this:
         * {zone:{location[1]:{directions:{**input**:**data we want**
         * and then returns the data to the game engine.
         *
         * Ask Michael if you need more information. -MS
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
         * This is not high on the priority list though so not too concerned if it doesn't get implemented now. -MS
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

    public static String getLocationInvFilePath(String location, String zone) {
        /*
         * first we drill down to get the filepath:
         * visualization: {zone:{location[3]:{items:[**data we need**]
         *
         * next we use the filepath to get the txt file with the data
         *
         * Again, ask Michael for more info. -MS
         */

        JSONObject locationJSON = grabJSONData();
        JSONObject zoneData = (JSONObject) locationJSON.get(zone); //null
        JSONArray locationData = (JSONArray) zoneData.get(location);
        JSONObject itemData = (JSONObject) locationData.get(3);

        return (String) itemData.get("items");
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
        //This gets called frequently! (once per "tick" or "action") to track the movement of items through the game. -MS
        String path = getLocationInvFilePath(location, zone);
        addToInventory(inventory, path);
    }

    public static String getNewZone(String location) {
        String zone = null;
        /*
         * This big dumb stupid function checks every zone in the game for the updated player location and then
         * returns the new zone location to the player object so that when you traverse zones the game doesn't break.
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

    private static JSONObject grabNPC() {
        JSONObject npcJSON = null;
        try {
            FileReader fileReader = new FileReader(NPCsJSON);
            Object obj = jsonParser.parse(fileReader);
            npcJSON = (JSONObject) obj;//THIS IS THE WHOLE JSON FILE

        } catch (IOException | ParseException e) {
            System.err.print("Error. Failed to load location.");
        }
        return npcJSON;

    }

    public static JSONArray getNPCsName(String zone, String location) {
        JSONArray NPCname;
        JSONObject locationJSON = grabJSONData();//THIS IS THE WHOLE JSON FILE
        JSONObject locationZone = (JSONObject) locationJSON.get(zone); //JUST EVERYTHING IN OUR ZONE
        JSONArray locationArea = (JSONArray) locationZone.get(location); //JUST EVERYTHING IN OUR AREA
        JSONObject NPCshowing = (JSONObject) locationArea.get(4); //JUST THE NPC
        NPCname = (JSONArray) NPCshowing.get("NPCs");

        return NPCname;
    }

    public static String getNPCsDialog(String NPCname, int digNum) {
        String NPCdia = "There was no one to talk to\n...\n......";
        String digKey = Integer.toString(digNum);

        JSONObject npcJSON = grabNPC();//THIS IS THE WHOLE JSON FILE
        JSONArray characterArray = (JSONArray) npcJSON.get(NPCname);
        if (npcJSON.containsKey(NPCname)) {
            JSONObject digJson = (JSONObject) characterArray.get(2);
            JSONObject digList = (JSONObject) digJson.get("dialogue");
            NPCdia = (String) digList.get(digKey);
        }
      return NPCdia;
    }

    public static ArrayList<String> getPlayerItems() {
        //this method only reads player_inventory.txt and returns the items within to the caller -MS
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

    public static void saveGame(String name, String location, String zone, ArrayList<String>inventory){
        /*
         * In order to save the game I found it helpful to copy over the current values from the Player fields used
         * in construction and then pass them to this method to be saved. The game background saves frequently which
         * isn't strictly necessary but is nice for when you are testing and just hit the x without typing quit. -MS
         */
            JSONObject saveFileJson = loadGame();
            saveFileJson.put("name",name);
            saveFileJson.put("location",location);
            saveFileJson.put("zone", zone);
            JSONArray inventoryArr = new JSONArray();
            inventoryArr.addAll(inventory);
            saveFileJson.put("inventory", inventoryArr);
            try(FileWriter w = new FileWriter(playerJSON)) {
                w.write(saveFileJson.toJSONString());
                w.flush();
            }catch(IOException e){
                e.printStackTrace();
            }
    }

    public static JSONObject loadGame(){
        //loads Player.JSON for parsing by save method and other load methods outside this class -MS
        JSONObject saveFileJson = new JSONObject();
        try(FileReader reader = new FileReader(playerJSON)){
            Object saveFileObj = jsonParser.parse(reader);
            saveFileJson = (JSONObject) saveFileObj;
        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
        return saveFileJson;
    }

    public static void updatePlayerItems(ArrayList<String> inventory) {
        //This updates the player_inventory.txt -MS
        String path = playerInvPath;
        addToInventory(inventory, path);
    }



    public static void addToInventory(ArrayList<String> inventory, String path) {
        //this is generic and used by player and location inventory -MS
        try (FileWriter w = new FileWriter(path)) {
            for (String item : inventory) {
                w.write(item + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Clip getMusic(Clip clip) {

        String AudioFile = "Assets/sample1.wav";
        try {
            File file = new File(AudioFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;

    }
}