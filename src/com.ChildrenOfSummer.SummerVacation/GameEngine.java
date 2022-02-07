package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class GameEngine {
    static boolean sceneOnePassed;
    static boolean sceneTwoPassed;
    static boolean sceneThreePassed;
    static boolean sceneFourPassed;
    static boolean sceneFivePassed;

    public static void execute() {
        FileManager.loadDefaults();
        if (Input.startMenu()) {
            Input.introduction();

        } else {
            sceneOnePassed = FileManager.sceneReader("sceneOnePassed");
            sceneTwoPassed = FileManager.sceneReader("sceneTwoPassed");
            sceneThreePassed = FileManager.sceneReader("sceneThreePassed");
            sceneFourPassed = FileManager.sceneReader("sceneFourPassed");
            sceneFivePassed = FileManager.sceneReader("sceneFivePassed");
        }
        while (!sceneOnePassed){
            sceneOnePassed = sceneOne();
        }
        while (!sceneTwoPassed){
            sceneTwoPassed = sceneTwo();
        }
        while (!sceneThreePassed){
            sceneThreePassed = sceneThree();
        }
        while (!sceneFourPassed){
            sceneFourPassed = sceneFour();
        }
        while (!sceneFivePassed){
            sceneFivePassed = sceneFive();
        }
        FileManager.getAssetFile("win-text.txt");
    }

    private static boolean sceneOne() {
        doWhile("Paine Field");
        return sceneOnePassed = Input.sceneOneTransition();
    }

    static boolean sceneTwo() {
        boolean sceneTwoPassed;
        doWhile("Player's House");
        FileManager.getAssetFile("scene-two.txt");
        sceneTwoPassed = true;
        FileManager.sceneWriter(true, "sceneTwoPassed");
        return sceneTwoPassed;
    }

    static boolean sceneThree() {
        doWhile("Hay Field");
        FileManager.getAssetFile("scene-three.txt");
        return  Input.sceneThree();
    }

    static boolean sceneFour() {
        boolean sceneFourPassed;
        doWhile("Old House South");
        FileManager.getAssetFile("scene-four.txt");
        sceneFourPassed = true;
        FileManager.sceneWriter(true,"sceneFourPassed");
        return sceneFourPassed;
    }

    private static boolean sceneFive() {
        doWhile("River");
        return Input.sceneFive();
    }

    private static void doWhile(String location) {
        JSONObject saveFile;
        String playerLocation;
        do {
            Input.inputCommandsLogic();
            saveFile = FileManager.loadGame();
            playerLocation = (String) saveFile.get("location");
        } while (!playerLocation.equals(location));
    }
}