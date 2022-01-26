package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Scanner;

/*
 *File manager can import, read, write, or create files
 */

public class SaveEditor {

    private FileInputStream in = null;
    private FileOutputStream out = null;
    private FileWriter writer;
    private Scanner scanner = new Scanner(System.in);
    private FileReader reader;
    private static String save = "Assets/save-game.properties";
    public Properties saveFile;

    //returns the save file to the game engine to be read

    public static void getAssetFile(String fileName) throws IOException{
        String art = "Assets/" + fileName;
        var out = new BufferedOutputStream(System.out);
        Files.copy(Path.of(art), out);
        out.flush();
    }

    /*
     * These methods load sets of asset files for different menus.
     */

    public static void menuFiles() {
        try {
            getAssetFile("opening-menu.txt");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //save-game.properties is set by default to null/0 values and then overwritten by the newPlayerCreator

    public static Object getSaveFile() {
        Object jsonSave = null;
        try{
            //create JSON Parser and file reader then create a JSON reader by combining them
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(save);
            jsonSave = jsonParser.parse(fileReader);
            System.out.println(jsonSave);

        }catch(IOException|ParseException e) {
            System.err.print("No save file present! Please create a new game.");
        }
        return jsonSave;
    }

}