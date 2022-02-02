package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);   //takes direct input currently from the user and passes it to the program
    private static String ANSWER;
    private static ArrayList<String> empty = new ArrayList<>();
    private static Player player1 = new Player("default","Player's House","Suburb",empty);

    public static boolean startMenu() {
        /*
         *Start menu, if you type new game it leaves the player1 we instantiate on class load as default or "new"
         *Load will of course over-write those values with the Player.json values so that you can continue your game.
         */

        boolean newGame = false;
        SaveEditor.menuFiles();
        String startMenuChoice = scanner.nextLine().strip().toLowerCase();
        switch (startMenuChoice) {
            case "new game":
                playerCreator();
                newGame = true;
                break;
            case "load game":
                JSONObject saveFile = SaveEditor.loadGame();
                String name = (String) saveFile.get("name");
                String location = (String) saveFile.get("location");
                String zone = (String) saveFile.get("zone");
                ArrayList<String> inventory = (ArrayList<String>) saveFile.get("inventory");
                player1 = new Player(name, location, zone, inventory);
                break;
            case "quit":
                System.exit(0);
            default:
                System.out.println("invalid!\n Please type 'new game' for new game, 'load game' to load your save or 'quit' to quit.\n");
        }
        return newGame;
    }

    static void playerCreator(){

        System.out.print("Enter your name:");
        ANSWER = scanner.nextLine().strip();
        player1.playerName = ANSWER;
        SaveEditor.saveGame(player1.playerName, player1.playerLocation, player1.playerZone, player1.playerInventory);

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
        if(!locationList.isEmpty()) {
            System.out.println("You see the following items on the ground: ");
            for (String item: locationList) {
                System.out.print("|" + item);
            }
            System.out.println("|");
        }
        System.out.print("What would you like to do?");

        ANSWER = scanner.nextLine().strip().toLowerCase();
        String[] answerWords = ANSWER.split(" ");
        String verb = answerWords[0];
        String noun1 = "out of bounds saver";
        if(answerWords.length > 1) {
            noun1 = answerWords[1]; //ONLY USED FOR COMBINING
        }
        String noun2 = answerWords[answerWords.length - 1];

        switch (verb) {
            case "see":
                SaveEditor.getAssetFile("map.txt");
                System.out.println("\nYour current location is " + player1.playerLocation);
                break;
            case "go":
                boolean didMove = false;
                for (Directions dir : Directions.values()) {
                    if (dir.name().equals(noun2.toUpperCase())) {
                        player1.move(noun2);
                        didMove = true;
                    }
                }
                if(!didMove){
                    System.out.println("you were unable to move "+ noun2 + ".");
                }
                SaveEditor.saveGame(player1.playerName, player1.playerLocation, player1.playerZone, player1.playerInventory);
                break;
            case "get":
                if (locationList.contains(noun2)) {
                    locationList.remove(noun2);
                    playerList.add(noun2);
                    SaveEditor.updateLocationItems(player1.playerLocation, player1.playerZone, locationList);
                    SaveEditor.updatePlayerItems(playerList);
                    player1.playerInventory = playerList;
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
                    player1.playerInventory = playerList;
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
                        player1.playerInventory = playerList;
                    }else {
                        System.out.println("I can't combine that!");
                    }
                break;
            case "use":
                //do final stuff
                System.out.println("Nothing happens...");
                break;
            case "talk":
                System.out.println(player1.talk(noun2));
                break;
            case "help":
                System.out.println("Your current location is " + player1.playerLocation);
                SaveEditor.getAssetFile("help.txt");
                break;
            case "music":
                System.out.println("~~~~Turn on back ground music~~~~~");
                System.out.println(noun2);
                SaveEditor.getMusic(noun2);
                break;
            case "quit":
                SaveEditor.saveGame(player1.playerName,player1.playerLocation, player1.playerZone,player1.playerInventory);
                System.exit(0);
            default:
                System.out.println("I didn't understand that command. for help type help.");
        }
        if(!playerList.isEmpty()) {
            System.out.println("Your inventory has: " + playerList);
        }
        //recursion happens in the while loop of the scene
    }
}
