package com.ChildrenOfSummer.SummerVacation;


import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class GameEngine {
    Scanner scanner = new Scanner(System.in);   //takes direct input currently from the user and passes it to the program
    String ANSWER;
    Player player1 = new Player();

    public void execute() {
        Input.startMenu();
        introduction();
        sceneOneCheck();
    }

    public void playerCreator() {
        System.out.println("this is the player creator");
    }

    public void introduction() {
        SaveEditor.getAssetFile("introduction.txt");
    }

    // At the airport
    public void sceneOneCheck() {
        while (!player1.playerLocation.equals("Paine Field")) {
            Input.inputCommandsLogic();
        }
        SaveEditor.getAssetFile("scene-one.txt");
        Input.inputCommandsLogic();
    }

    public void sceneOneEnd() {
        System.out.println("Do you want to add additional items?");
        ANSWER = scanner.nextLine().strip();
        if (ANSWER.equalsIgnoreCase("yes")) {
            //do stuff
        } else if (ANSWER.equalsIgnoreCase("no")) {
            sceneTwo();
        } else {
            System.out.println("Input not valid");
            sceneOneEnd();
        }
    }

    public void sceneTwo() {
        System.out.println("Insert paragraph for scene two");
        System.out.println("Hello");
        sceneThree();
    }

    public void sceneThree() {
        System.out.println("Insert paragraph for scene three");
    }

    public void sceneFour() {
        System.out.println("work in progress");
    }

    public void sceneFive() {
        System.out.println("work in progress");
    }

    public void sceneSix() {
        System.out.println("work in progress");
    }

    public void sceneSeven() {
        System.out.println("work in progress");
    }

    public int randomNumberGenerator() {
        //1-3, 3-6, 1-6
        return (int) (Math.random() * ((6) + 1));
    }

}