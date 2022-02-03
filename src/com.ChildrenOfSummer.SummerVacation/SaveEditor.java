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



/*
 *File manager can import, read, write, or create files
 */

public class SaveEditor {

    private static String locationsJsonPath = "Assets/Locations.JSON";
    private static String npcsJsonPath = "Assets/NPCs.JSON";
    private static String playerJsonPath = "Assets/Player.JSON";
    private static String locationItemsJsonPath = "Assets/Location_items.JSON";
    private static String locationNpcsJsonPath = "Assets/Location_NPCs.JSON";
    private static JSONParser jsonParser = new JSONParser();
    private static String defaultLocationItemsJsonPath = "Assets/defaults/Location_items_default.JSON";
    private static String defaultLocationNpcsJsonPath = "Assets/defaults/Location_NPCs_default.JSON";

    private static String defaultPlayerJsonPath = "Assets/defaults/Player_default.JSON";

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


    private static JSONObject grabJSONData(String path) {
        /*
         * We made this method to grab any whole dang JSON file to simplify some other methods from being a huge mess
         * of variable declarations. -MS
         */

        JSONObject locationJSON = null;
        try {
            //create JSON Parser and file reader then create a JSON reader by combining them
            FileReader fileReader = new FileReader(path);
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
        JSONObject locationJSON = grabJSONData(locationsJsonPath);//THIS IS THE WHOLE JSON FILE
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

        JSONObject locationJSON = grabJSONData(locationsJsonPath);
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

    public static ArrayList<String> getLocationItems(String location) {
        JSONObject locationItems = grabJSONData(locationItemsJsonPath);
        ArrayList<String> inventory = (ArrayList<String>) locationItems.get(location);
        return inventory;
    }

    public static void updateLocationItems(String location, ArrayList<String> inventory) {
        //This gets called frequently! (once per "tick" or "action") to track the movement of items through the game. -MS
        JSONObject locationItems = grabJSONData(locationItemsJsonPath);
        locationItems.put(location, inventory);
        writeJSONFile(locationItemsJsonPath, locationItems);
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
            JSONObject locationData = grabJSONData(locationsJsonPath);
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

    public static JSONArray getNPCsName(String location) {
        /*
         * Searches the Locations_NPC.JSON file to see if there is an NPC in the location the player is in. -MS
         */
        JSONArray NPCname;
        JSONObject locationNpcJson = grabJSONData(locationNpcsJsonPath);//THIS IS THE WHOLE JSON FILE
        NPCname = (JSONArray) locationNpcJson.get(location);

        return NPCname;
    }

    public static String getNPCsDialog(String NPCname, int digNum) {
        String NPCdia = "There was no one to talk to\n...\n......";
        String digKey = Integer.toString(digNum);

        JSONObject npcJSON = grabJSONData(npcsJsonPath);
        JSONArray characterArray = (JSONArray) npcJSON.get(NPCname);
        if (npcJSON.containsKey(NPCname)) {
            JSONObject digJson = (JSONObject) characterArray.get(2);
            JSONObject digList = (JSONObject) digJson.get("dialogue");
            NPCdia = (String) digList.get(digKey);
        }
      return NPCdia;
    }

    public static ArrayList<String> getPlayerItems() {
        //this method only reads Player.JSON and returns the items within to the caller -MS
        ArrayList<String> inventory;
        JSONObject playerSave = loadGame();
        inventory = (ArrayList<String>) playerSave.get("inventory");
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
            try(FileWriter w = new FileWriter(playerJsonPath)) {
                w.write(saveFileJson.toJSONString());
                w.flush();
            }catch(IOException e){
                e.printStackTrace();
            }
    }

    public static JSONObject loadGame(){
        //loads Player.JSON for parsing by save method and other load methods outside this class -MS
        JSONObject saveFileJson = new JSONObject();
        try(FileReader reader = new FileReader(playerJsonPath)){
            Object saveFileObj = jsonParser.parse(reader);
            saveFileJson = (JSONObject) saveFileObj;
        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
        return saveFileJson;
    }

    public static void loadDefaults(){
        //Sets the stage for a new game! -MS
        JSONObject newLocationItems = grabJSONData(defaultLocationItemsJsonPath);
        writeJSONFile(locationItemsJsonPath,newLocationItems);
        JSONObject newNpcLocations = grabJSONData(defaultLocationNpcsJsonPath);
        writeJSONFile(locationNpcsJsonPath,newNpcLocations);

        JSONObject newPlayer = grabJSONData(defaultPlayerJsonPath);
        writeJSONFile(playerJsonPath, newPlayer);
    }

    public static void savePlayerItems(ArrayList<String> inventory) {
        //This updates the player_inventory.JSON -MS
        JSONObject playerSave = loadGame();
        JSONArray inventoryArr = new JSONArray();
        inventoryArr.addAll(inventory);
        playerSave.put("inventory", inventoryArr);
        writeJSONFile(playerJsonPath,playerSave);
    }

    public static void writeJSONFile(String path, JSONObject obj){
        //write JSON files. I was writing this too much.
        try (FileWriter w = new FileWriter(path)) {
            w.write(obj.toJSONString());
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean sceneReader(String fileName){
        JSONObject saveFile = loadGame();
        boolean sceneFlag = (boolean) saveFile.get(fileName);
        return sceneFlag;
    }

    public static void sceneWriter(boolean scene, String fileName){
        JSONObject saveFile = loadGame();
        saveFile.put(fileName,scene);
        writeJSONFile(playerJsonPath,saveFile);
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