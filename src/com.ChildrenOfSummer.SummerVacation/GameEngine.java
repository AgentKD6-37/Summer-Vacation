package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class GameEngine {

    public void execute() {
        if (Input.startMenu()) {
            introduction();
        }
        sceneOneCheck();
    }

    private void introduction() {
        SaveEditor.getAssetFile("introduction.txt");
        System.out.println("\n");
    }

    // At the airport
    private void sceneOneCheck() {
        /*
         * This is the first cutscene check, essentially it allows the game to run in the background by calling Input.inputCommandsLogic()
         * over and over again until the player location hits the required area. At that point it *SHOULD* trigger the scene-one.txt to play out.
         */
        JSONObject saveFile;
        String playerLocation;
        do  {
            Input.inputCommandsLogic();
            saveFile = SaveEditor.loadGame();
            playerLocation = (String) saveFile.get("location");
        } while (!playerLocation.equals("Paine Field"));
        SaveEditor.getAssetFile("scene-one.txt");
        sceneOne();
    }

//    private void sceneOne(){
//        boolean isSolved = false;
//        do {
//            Input.inputCommandsLogic();
//        } while (isSolved==false);
//    }
    private void sceneOne(){
        JSONObject saveFile = SaveEditor.loadGame();
        ArrayList<String> playerList =(ArrayList<String>) saveFile.get("inventory");
        String location = (String) saveFile.get("location");
        boolean sceneOneCompleted = false;
        while (sceneOneCompleted){
            if (location.equals("Paine Field") && playerList.contains("rope") && playerList.contains("planks")) {
                sceneOneCompleted = SceneOneCompletion.completion();
                sceneOneEnd();
            }
            Input.inputCommandsLogic();
        }
    }

    static void sceneOneEnd(){
        SaveEditor.getAssetFile("scene-one-end.txt");
        sceneTwo();
    }


    private static void sceneTwo() {
        System.out.println("Insert paragraph for scene two");
        System.out.println("Hello");
        sceneThree();
    }

    private static void sceneThree() {
        System.out.println("Insert paragraph for scene three");
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

}