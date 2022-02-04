package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;

public class GameEngine {
    static boolean sceneOnePassed = false;
    static boolean sceneTwoPassed = false;
    static boolean sceneThreePassed = false;


    public static void execute() {
        FileManager.loadDefaults();
        if (Input.startMenu()) {
            Input.introduction();

        } else {
            sceneOnePassed = FileManager.sceneReader("sceneOnePassed");
            sceneTwoPassed = FileManager.sceneReader("sceneTwoPassed");
            sceneThreePassed = FileManager.sceneReader("sceneThreePassed");
        }
        if (sceneOnePassed == false){
            sceneOnePassed = sceneOne();
        }
        if (sceneTwoPassed == false){
            sceneTwoPassed = sceneTwo();
        }
        if (sceneThreePassed == false){
            sceneThreePassed = sceneThree();
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
        doWhile("Barn");
        FileManager.getAssetFile("scene-three.txt");
        FileManager.sceneWriter(true, "sceneThreePassed");
        System.out.println("STOPPER 2");
        sceneThreePassed = Input.sceneThree();
        return sceneThreePassed = Input.sceneThree();
    }

    private void sceneFour() {
        System.out.println("work in progress");
    }

    private void sceneFive() {
        System.out.println("work in progress");
    }

    private void sceneSix() {
        System.out.println("work in progress");
    }

    private void sceneSeven() {
        System.out.println("work in progress");
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