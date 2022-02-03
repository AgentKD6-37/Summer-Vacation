package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;

public class GameEngine {
    boolean sceneOnePassed = false;
    boolean sceneTwoPassed = false;


    public void execute() {
        if (Input.startMenu()) {
            introduction();
        }
        else{
            sceneOnePassed = FileManager.sceneReader("sceneOnePassed");
        }
        sceneOnePassed = sceneOne();
        if (sceneOnePassed == true){
            sceneTwoPassed = sceneTwo();
            if (sceneTwoPassed == true){
                sceneThree();
            }
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
       return sceneOnePassed = Input.sceneOneTransition();
    }

    static void sceneOneEnd(){
        FileManager.getAssetFile("scene-one-end.txt");
        FileManager.sceneWriter(true, "sceneOnePassed");
        sceneTwo();
    }

    static boolean sceneTwo() {
        boolean sceneTwoPassed = false;
        JSONObject saveFile;
        String playerLocation;
        do  {
            Input.inputCommandsLogic();
            saveFile = FileManager.loadGame();
            playerLocation = (String) saveFile.get("location");
        }while (!playerLocation.equals("Player's House"));{
            FileManager.getAssetFile("scene-two.txt");
            sceneTwoPassed = true;
        }
        FileManager.sceneWriter(true, "sceneTwoPassed");
        return sceneTwoPassed;
    }

    static boolean sceneThree() {
        System.out.println("Insert paragraph for scene three, SCENE THREE PLACEHOLDER");
        boolean sceneThreePassed = false;
        JSONObject saveFile;
        String playerLocation;
        do  {
            Input.inputCommandsLogic();
            saveFile = FileManager.loadGame();
            playerLocation = (String) saveFile.get("location");
        }while (!playerLocation.equals("Barn"));{
            FileManager.getAssetFile("scene-three.txt");
            sceneThreePassed = Input.sceneThree();
        }
        FileManager.sceneWriter(true, "sceneThreePassed");
        return sceneThreePassed;
    }

    public static void sceneThreeEnd() {
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