package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

class SceneOneCompletion {

    static Scanner scanner = new Scanner(System.in);
    static String ANSWER = scanner.nextLine().strip().toLowerCase();

    public static boolean completion() {
        System.out.println("Congratulations! You've picked the rope and planks. Do you wish to combine them?");
        if (ANSWER.equalsIgnoreCase("yes")) {
            System.out.println("Nice! You've combined the items. You can now use it to get out");
            GameEngine.sceneOneEnd();
        }
        else{
            Input.inputCommandsLogic();
        }
        return true;
    }
}


