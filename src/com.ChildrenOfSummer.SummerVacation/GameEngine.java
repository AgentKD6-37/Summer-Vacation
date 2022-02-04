package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;

import java.io.File;

public class GameEngine {
    static boolean sceneOnePassed;
    static boolean sceneTwoPassed;
    static boolean sceneThreePassed;
    static boolean sceneFourPassed;
    static boolean sceneFivePassed;
    static boolean sceneSixPassed;
    static boolean sceneSevenPassed;
    static boolean sceneEightPassed;
    static boolean sceneNinePassed;


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
            sceneSixPassed = FileManager.sceneReader("sceneSixPassed");
            sceneSevenPassed = FileManager.sceneReader("sceneSevenPassed");
            sceneEightPassed = FileManager.sceneReader("sceneEightPassed");
            sceneNinePassed = FileManager.sceneReader("sceneNinePassed");

        }
        if (!sceneOnePassed){
            sceneOnePassed = sceneOne();
        }
        if (!sceneTwoPassed){
            sceneTwoPassed = sceneTwo();
        }
        if (!sceneThreePassed){
            sceneThreePassed = sceneThree();
        }
        if (!sceneFourPassed){
            sceneFourPassed = sceneFour();
        }
        if (!sceneFivePassed){
            sceneFivePassed = sceneFive();
        }
        if (!sceneSixPassed){
            sceneSixPassed = sceneSix();
        }
        if (!sceneSevenPassed){
            sceneSevenPassed = sceneSeven();
        }
        if (!sceneEightPassed){
            sceneEightPassed = sceneEight();
        }

        if (!sceneNinePassed){
            sceneNinePassed = sceneNine();
        }
    }


    //     At the airport
    private static boolean sceneOne() {
        /*
         * This is the first cutscene check, essentially it allows the game to run in the background by calling Input.inputCommandsLogic()
         * over and over again until the player location hits the required area. At that point it *SHOULD* trigger the scene-one.txt to play out.
         */
        boolean sceneOnePassed = false;
        doWhile("Paine Field");
        return sceneOnePassed = Input.sceneOneTransition();
    }

    static void sceneOneEnd() {
        FileManager.getAssetFile("scene-one-end.txt");
        FileManager.sceneWriter(true, "sceneOnePassed");
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
        boolean sceneThreePassed;
        doWhile("Hay Field");
        FileManager.getAssetFile("scene-three.txt");
        FileManager.sceneWriter(true, "sceneThreePassed");
        sceneThreePassed = Input.sceneThree();
        return sceneThreePassed = Input.sceneThree();
    }

    private static boolean sceneFour() {
        boolean sceneFourPassed;
        doWhile("Old House South");
        FileManager.getAssetFile("scene-four.txt");
        sceneFourPassed = true;
        FileManager.sceneWriter(true,"sceneFourPassed");
        return sceneFourPassed;
    }

    private static boolean sceneFive() {
        doWhile("River");
        return false;
    }

    private static boolean sceneSix() {
        System.out.println("work in progress");
        doWhile("Sara's House");
        return false;
    }

    private static boolean sceneSeven() {
        doWhile("Barn");
        return false;
    }

    private static boolean sceneEight() {
        doWhile("Player's House");
        return false;
    }

    private static boolean sceneNine() {
        doWhile("New House Northeast");
        return false;
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