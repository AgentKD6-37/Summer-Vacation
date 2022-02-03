package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;

public class GameEngine {
    boolean sceneOnePassed = false;

    public void execute() {
        FileManager.loadDefaults();
        if (Input.startMenu()) {
            introduction();
        }
        else{
            sceneOnePassed = FileManager.sceneReader("sceneOnePassed");
        }
        sceneOnePassed = sceneOne();
        if (sceneOnePassed == true){
            sceneTwo();
        }else{
            execute();
        }
    }

    private void introduction() {
        FileManager.getAssetFile("introduction.txt");
        System.out.println("\n");
    }

//     At the airport
    private boolean sceneOne() {
        /*
         * This is the first cutscene check, essentially it allows the game to run in the background by calling Input.inputCommandsLogic()
         * over and over again until the player location hits the required area. At that point it *SHOULD* trigger the scene-one.txt to play out.
         */
        boolean sceneOnePassed = false;
        JSONObject saveFile;
        String playerLocation;
        do  {
            Input.inputCommandsLogic();
            saveFile = FileManager.loadGame();
            playerLocation = (String) saveFile.get("location");
        } while (!playerLocation.equals("Paine Field"));
//        SaveEditor.getAssetFile("scene-one.txt");
       return sceneOnePassed = Input.sceneOneTransition();
    }

    static void sceneOneEnd(){
        FileManager.getAssetFile("scene-one-end.txt");
        sceneTwo();
        FileManager.sceneWriter(true, "sceneOnePassed");
    }

    private static void sceneTwo() {
        boolean sceneTwoPassed = false;
        JSONObject saveFile;
        String playerLocation;
        do  {
            Input.inputCommandsLogic();
            saveFile = FileManager.loadGame();
            playerLocation = (String) saveFile.get("location");
        }while (playerLocation.equals("Player's House"));{
            System.out.println("SCENE TWO PLACEHOLDER");
        }
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