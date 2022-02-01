package com.ChildrenOfSummer.SummerVacation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);   //takes direct input currently from the user and passes it to the program
    private static String ANSWER;
    private static Player player1 = new Player();

    public static void startMenu() {
        //pretty basic start menu. Switch over input. todo: add input validation -MS
        SaveEditor.menuFiles();
        String startMenuChoice = scanner.nextLine().strip().toLowerCase();
        switch (startMenuChoice) {
            case "new game":
                playerCreator();
                break;
            case "load game":
                //do stuff
                break;
            case "quit":
                System.exit(0);
        }
    }

    static void playerCreator(){
        System.out.print("Enter your name:");
    }

    public static void inputCommandsLogic(){
        /*
         * THIS big boy is the main interaction point for the user with the game. It takes user commands in as verb x noun.
         * For example, you can type "get the dog" and the method will store [get, the, dog]
         * We then switch over the word in position 0 for matches to our commands list ("get") and then execute the "noun"
         * which is really just the word stored in position .length-1. With input validation provided for the verb by the switch statement
         * and for the noun by some robust if->else or for loops, we can prevent the player from breaking the program with an
         * unknown command. -MS
         */

        ArrayList<String> locationList = SaveEditor.getLocationItems(player1.playerLocation, player1.playerZone);
        ArrayList<String> playerList = SaveEditor.getPlayerItems();
        System.out.println("You see the following items on the ground: " + locationList + ".");
        System.out.print("What would you like to do?");
        ANSWER = scanner.nextLine().strip();
        String[] answerWords = ANSWER.split(" ");
        System.out.println(Arrays.toString(answerWords));
        String verb = answerWords[0];
        String noun1 = answerWords[1]; //ONLY USED FOR COMBINING
        String noun2 = answerWords[answerWords.length - 1];

        switch (verb) {
            case "map":
                SaveEditor.getAssetFile("map.txt");
                System.out.println("\nYour current location is " + player1.playerLocation);
                break;
            case "go":
                Boolean didMove = false;
                for (Directions dir : Directions.values()) {
                    if (dir.name().equals(noun2.toUpperCase())) {
                        player1.move(noun2);
                        didMove = true;
                    }
                }
                if(didMove == false){
                    System.out.println("you were unable to move "+ noun2 + ".");
                }
                break;
            case "get":
                if (locationList.contains(noun2)) {
                    locationList.remove(noun2);
                    playerList.add(noun2);
                    SaveEditor.updateLocationItems(player1.playerLocation, player1.playerZone, locationList);
                    SaveEditor.updatePlayerItems(playerList);
                } else {
                    System.out.println("I can't get that! There's no " + noun2 + " for me to pick up!");
                }
                break;
            case "drop":
                if (playerList.contains(noun2)){
                    locationList.add(noun2);
                    playerList.remove(noun2);
                    SaveEditor.updateLocationItems(player1.playerLocation, player1.playerZone, locationList);
                    SaveEditor.updatePlayerItems(playerList);
                }else{
                    System.out.println("I can't drop what I don't have!");
                }
                break;
            case "combine":
                if (playerList.contains(noun2)&&playerList.contains(noun1))
                    if((noun1.equals("planks")||noun1.equals("rope"))&&(noun2.equals("rope")||noun2.equals("planks"))){
                        System.out.println("You tie the planks to each other using the rope to create a ladder!");
                        playerList.remove(noun1);
                        playerList.remove(noun2);
                        playerList.add("ladder");
                        SaveEditor.updatePlayerItems(playerList);
                    }else {
                        System.out.println("I can't combine that!");
                    }
                break;
            case "use":
                //do final stuff
                System.out.println("Nothing happens...");
                break;
            case "talk":
                System.out.println("talk is called");
                player1.talk(noun2);
                break;
            case "help":
                System.out.println("Your current location is " + player1.playerLocation);
                System.out.println("Type 'go east' to go east.\n" +
                        "Type 'go west' to go west.\n" +
                        "Type 'go north' to go north\n" +
                        "Type 'go south' to go south");
                break;
            case "quit":
                System.exit(0);
            default:
                System.out.println("I didn't understand that command. for help type help.");
        }
        System.out.println("Your inventory has: " + playerList);
        //recursion happens in the while loop of the scene
    }
}
