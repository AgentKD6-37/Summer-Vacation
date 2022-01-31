package com.ChildrenOfSummer.SummerVacation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);   //takes direct input currently from the user and passes it to the program
    private static String ANSWER;
    private static Player player1 = new Player();

    public static void startMenu() {
        SaveEditor.menuFiles();
        String startMenuChoice = scanner.nextLine().strip().toLowerCase();
        switch (startMenuChoice) {
            case "new game":
                playerCreator();
                break;
            case "load game":
                SaveEditor.getSaveFile();
                break;
            case "quit":
                System.exit(0);
        }
    }

    private static void playerCreator(){
        System.out.println("this is the player creator");
    }

    public static void inputCommandsLogic(){
        ArrayList<String> locationList = SaveEditor.getLocationItems(player1.playerLocation, player1.playerZone);
        ArrayList<String> playerList = SaveEditor.getPlayerItems();
        System.out.println("You see the following items on the ground: " + locationList + ".");
        System.out.print("What would you like to do?");
        ANSWER = scanner.nextLine().strip();
        String[] answerWords = ANSWER.split(" ");
        System.out.println(Arrays.toString(answerWords));
        String verb = answerWords[0];
        String noun = answerWords[answerWords.length - 1];

        switch (verb) {
            case "map":
                SaveEditor.getAssetFile("map.txt");
                System.out.println("\nYour current location is " + player1.playerLocation);
                break;
            case "go":
                Boolean didMove = false;
                for (Directions dir : Directions.values()) {
                    if (dir.name().equals(noun.toUpperCase())) {
                        player1.move(noun);
                        didMove = true;
                    }
                }
                if(didMove == false){
                    System.out.println("you were unable to move "+ noun + ".");
                }
                break;
            case "get":
                if (locationList.contains(noun)) {
                    locationList.remove(noun);
                    playerList.add(noun);
                    SaveEditor.updateLocationItems(player1.playerLocation, player1.playerZone, locationList);
                    SaveEditor.updatePlayerItems(playerList);
                } else {
                    System.out.println("I can't get that! There's no " + noun + " for me to pick up!");
                }
                break;
            case "drop":
                if (playerList.contains(noun)){
                    locationList.add(noun);
                    playerList.remove(noun);
                    SaveEditor.updateLocationItems(player1.playerLocation, player1.playerZone, locationList);
                    SaveEditor.updatePlayerItems(playerList);
                }else{
                    System.out.println("I can't drop what I don't have!");
                }
                break;
            case "combine":
                //do other stuff
            case "use":
                //do final stuff
            case "talk":
                player1.talk(noun);
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

        inputCommandsLogic();
    }
}
