package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;

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

    private void sceneOne(){
        boolean isSolved = false;
        do {
            Input.inputCommandsLogic();
        } while (isSolved==false);
    }


    private void sceneTwo() {
        System.out.println("Insert paragraph for scene two");
        System.out.println("Hello");
        sceneThree();
    }

    private void sceneThree() {
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