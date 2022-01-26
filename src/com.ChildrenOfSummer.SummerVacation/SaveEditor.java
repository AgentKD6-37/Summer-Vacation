package com.ChildrenOfSummer.SummerVacation;

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
    private String saveGame = "Assets/save-game.properties";
    public Properties saveFile;

    //returns the save file to the game engine to be read
    public Object getFlag(Properties flag) throws IOException {
        saveFile = new Properties();
        in = new FileInputStream(saveGame);
        saveFile.load(in);
        return saveFile.get(flag);
    }

    public void getAssetFile (String fileName) throws IOException{
        String art = "Assets/" + fileName;
        var out = new BufferedOutputStream(System.out);
        Files.copy(Path.of(art), out);
        out.flush();
    }

    void newPlayerCreator() throws IOException{
        saveFile = new Properties();
        in = new FileInputStream(saveGame);
        saveFile.load(in);
        in.close();
        out = new FileOutputStream(saveGame);
        System.out.print("What is your name? : ");
        System.out.println();
        String name = scanner.next();
        saveFile.put("playerName", name);
        saveFile.put("rlglComplete", Boolean.toString(false));
        saveFile.put("isAlive", Boolean.toString(true));
        System.out.print("Have you played this game before? [Y/N] : ");
        System.out.println();
        String newPlayer = scanner.next();
        if (newPlayer.equals("N")) {
            saveFile.put("newPlayer", Boolean.toString(true));
            getAssetFile("new-player-story.txt");
        }else if (newPlayer.equals("Y")){
            System.out.println("starting game");
        } else {
            System.out.println("Enter only Y or N");
            System.out.println();
            newPlayer = scanner.next();
        }
        saveFile.store(out, "Player Save File");
        out.close();
    }

    /*
     * These methods load sets of asset files for different menus.
     */

    public void MenuFiles() throws IOException {

        getAssetFile("opening-menu-art.txt");
        getAssetFile("opening-menu-banner.txt");
        getAssetFile("opening-menu-dialogue.txt");
    }

    //save-game.properties is set by default to null/0 values and then overwritten by the newPlayerCreator

    public Properties getSaveFile() throws IOException {
        try {
            saveFile = new Properties();
            in = new FileInputStream(saveGame);
            saveFile.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }return saveFile;
    }

}