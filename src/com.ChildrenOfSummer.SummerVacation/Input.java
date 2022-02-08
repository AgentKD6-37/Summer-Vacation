package com.ChildrenOfSummer.SummerVacation;

import com.ChildrenOfSummer.SummerVacation.Util.Directions;
import com.ChildrenOfSummer.SummerVacation.Util.TextParser;
import org.json.simple.JSONObject;

import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);   //takes direct input currently from the user and passes it to the program
    private static String ANSWER;
    private static ArrayList<String> empty = new ArrayList<>();
    private static Player player1 = Player.getInstance("default", "Player's House", "Suburb", empty);
    private static final Clip clip = FileManager.getMusic(null);


    public static boolean startMenu() {
        /*
         *Start menu, if you type new game it leaves the player1 we instantiate on class load as default or "new"
         *Load will of course over-write those values with the Player.json values so that you can continue your game.
         * -MS
         */

        boolean newGame = false;

        while (!newGame) {  //loop until new game option selected, error msg for invalid input -JH
            FileManager.menuFiles();    //display menu text
            String startMenuChoice = TextParser.getVerb(scanner.nextLine());    //.strip().toLowerCase();
            switch (startMenuChoice) {
                case "start":
                    player1.setPlayerInventory(empty);
                    player1.setPlayerName("default");
                    player1.setPlayerLocation("Player's House");
                    player1.setPlayerZone("Suburb");
                    playerCreator();
                    FileManager.loadDefaults();
                    return true;

                case "continue":
                    JSONObject saveFile = FileManager.loadGame();
                    String name = (String) saveFile.get("name");
                    String location = (String) saveFile.get("location");
                    String zone = (String) saveFile.get("zone");
                    ArrayList<String> inventory = (ArrayList<String>) saveFile.get("inventory");
                    player1.setPlayerInventory(inventory);
                    player1.setPlayerName(name);
                    player1.setPlayerLocation(location);
                    player1.setPlayerZone(zone);
                    return false;

                case "quit":
                    System.exit(0);

                default:
                    System.out.println("invalid!\n Please type 'new game' for new game, 'load game' to load your save or 'quit' to quit.\n");
            }

        }
        return newGame;
    }


    static void introduction() {
        FileManager.getAssetFile("introduction.txt");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        FileManager.getZoneArtFile("zone2.txt");
        FileManager.getAssetFile("game-start.txt");
    }


    static void playerCreator(){
        /*
         *Takes in your name and saves the save file with default values.
         */
        System.out.print("Enter your name:");
        ANSWER = scanner.nextLine().strip();
        player1.setPlayerName(ANSWER);
        FileManager.saveGame(player1.getPlayerName(), player1.getPlayerLocation(), player1.getPlayerZone(), player1.getPlayerInventory());

    }

    public static void inputCommandsLogic() {
        /*
         * THIS big boy is the main interaction point for the user with the game. It takes user commands in as verb x noun.
         * For example, you can type "get the dog" and the method will store [get, the, dog]
         * We then switch over the word in position 0 for matches to our commands list ("get") and then execute the "noun"
         * which is really just the word stored in position .length-1. With input validation provided for the verb by the switch statement
         * and for the noun by some robust if->else or for loops, we can prevent the player from breaking the program with an
         * unknown command. -MS
         */
        ArrayList<String> locationList = FileManager.getLocationItems(player1.getPlayerLocation());
        ArrayList<String> playerList = FileManager.getPlayerItems();
        if (!locationList.isEmpty()) {
            System.out.println("You see the following items lying around: ");
            for (String item : locationList) {
                System.out.print("|" + item);
            }
            System.out.println("|");
        }
        System.out.print("\nWhat would you like to do?");

        ANSWER = scanner.nextLine().strip().toLowerCase();
        String[] answerWords = ANSWER.split(" ");
        String verb = answerWords[0];
        String noun2 = answerWords[answerWords.length - 1];

        switch (verb) {
            case "map":
                FileManager.getAssetFile("map.txt");
                System.out.println("\nYour current location is " + player1.getPlayerLocation());
                inputCommandsLogic();
                break;
            case "inventory":
                System.out.println("Your inventory has: " + playerList);
                break;
            case "go":
                boolean didMove = false;
                for (Directions dir : Directions.values()) {
                    if (dir.name().equals(noun2.toUpperCase())) {
                        player1.move(noun2);
                        didMove = true;
                    }
                }
                if (!didMove) {
                    System.out.println("you were unable to move " + noun2 + ".");
                }
                FileManager.saveGame(player1.getPlayerName(), player1.getPlayerLocation(), player1.getPlayerZone(), player1.getPlayerInventory());
                break;
            case "get":
                if (locationList.contains(noun2)) {
                    locationList.remove(noun2);
                    playerList.add(noun2);
                    FileManager.updateLocationItems(player1.getPlayerLocation(), locationList);
                    FileManager.savePlayerItems(playerList);
                    player1.setPlayerInventory(playerList);
                    System.out.println("Your inventory has: " + playerList);
                } else {
                    System.out.println("I can't get that! There's no " + noun2 + " for me to pick up!");
                }
                break;
            case "drop":
                if (playerList.contains(noun2)) {
                    locationList.add(noun2);
                    playerList.remove(noun2);
                    FileManager.updateLocationItems(player1.getPlayerLocation(), locationList);
                    FileManager.savePlayerItems(playerList);
                    player1.setPlayerInventory(playerList);
                } else {
                    System.out.println("I can't drop what I don't have!");
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
                System.out.println("Your current location is " + player1.getPlayerLocation());
                FileManager.getAssetFile("help.txt");
                inputCommandsLogic();
                break;
            case "music":
                switch (noun2) {
                    case "on":
                        int loopTimes = 3;
                        clip.loop(loopTimes);
                        System.out.println("Back ground music turned on~~~~~");
                        break;
                    case "off":
                        clip.stop();
                        System.out.println("Back ground music stopped~~~~~");
                        break;
                    default: System.out.println("Not a valid response\n [on] [off]");
                }

                break;
            case "quit":
                FileManager.saveGame(player1.getPlayerName(), player1.getPlayerLocation(), player1.getPlayerZone(), player1.getPlayerInventory());
                System.out.println("Press 1 to quit to menu or 2 to exit game:");
                int choice = scanner.nextInt();
                switch (choice){
                    case 2:
                        System.exit(0);
                        break;
                    case 1:
                        GameEngine.execute();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        inputCommandsLogic();
                }

            default:
                System.out.println("I didn't understand that command. for help type help.");
                inputCommandsLogic();

        }
        FileManager.saveGame(player1.getPlayerName(), player1.getPlayerLocation(), player1.getPlayerZone(), player1.getPlayerInventory());
    }

    static boolean sceneOneAction() {
        boolean sceneOnePass = FileManager.sceneReader("sceneOnePassed");
        ArrayList<String> playerList = FileManager.getPlayerItems();
        System.out.println("You noticed that your rope and planks could be combined!\n " +
                "You can create a ladder to get out!\n " +
                "Do you wish to combine the items to get out?");
        String scan = scanner.nextLine().strip();
        if (scan.equals("yes")) {
            sceneOnePass = true;
            player1.getPlayerInventory();
            playerList.remove("rope");
            playerList.remove("planks");
            playerList.add("ladder");
            player1.setPlayerInventory(playerList);
            FileManager.savePlayerItems(playerList);
            FileManager.getAssetFile("scene-one-end.txt");
            FileManager.sceneWriter(true, "sceneOnePassed");
        } else if (scan.equals("no")){
            System.out.println("Game Over. Press enter to continue...");
            scanner.nextLine();
            startMenu();
        }
        else{
            System.out.println("Input not valid, enter yes or no");
            sceneOneAction();
        }
        return sceneOnePass;
    }
    static boolean sceneOneTransition(){
        boolean sceneOnePass = FileManager.sceneReader("sceneOnePassed");
        ArrayList<String> playerList = FileManager.getPlayerItems();
        if (player1.getPlayerLocation().equals("Paine Field") && !sceneOnePass) {
            FileManager.getAssetFile("scene-one.txt");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            if (playerList.contains("rope") && playerList.contains("planks")) {
                sceneOnePass = sceneOneAction();
            } else {
                System.out.println();
                System.out.println("With no items to help you, you and your friends are caught by security! You're grounded!");
                System.out.println("Game Over. Press Enter to continue...");
                scanner.nextLine();
                GameEngine.execute();
            }
        }
        return sceneOnePass;
    }

    static boolean sceneThree(){
        boolean sceneThreePass = FileManager.sceneReader("sceneThreePassed");

        ArrayList<String> playerList = FileManager.getPlayerItems();
        System.out.println(player1.getPlayerLocation());

        if (player1.getPlayerLocation().equals("Hay Field") && !sceneThreePass) {
            if (playerList.contains("rock")) {
                System.out.println("You can throw a rock to escape. Do you want to?");
                String scan = scanner.nextLine().strip().toLowerCase();
                if (scan.equals("yes")) {
                    System.out.println("You've escaped from the farmer.");
                    FileManager.getAssetFile("scene-three-end.txt");
                    sceneThreePass = true;
                    FileManager.sceneWriter(true, "SceneThreePass");
                } else if (scan.equals("no")){
                    System.out.println("You got caught! Game Over. Press enter to continue");
                    scanner.nextLine();
                    startMenu();
                }
                else{
                    System.out.println("Input not valid, enter yes or no");
                }
            } else {
                System.out.println("You reached into your inventory to find something to throw at the farmer" +
                        "but there's nothing there! You've been caught!");
                System.out.println("Game Over. You should explore the map to find something" +
                        "to throw at the farmer to distract him. A rock, maybe? Press enter to continue");
                scanner.nextLine();
                startMenu();
            }
        }
        player1.getPlayerInventory();
        playerList.remove("rock");
        player1.setPlayerInventory(playerList);
        FileManager.savePlayerItems(playerList);
        return sceneThreePass;
    }

    static boolean sceneFive(){
        boolean sceneFivePass = FileManager.sceneReader("sceneFivePassed");
        ArrayList<String> playerList = FileManager.getPlayerItems();
        System.out.println(player1.getPlayerLocation());
        if (playerList.contains("raft")&& playerList.contains("paddle") && playerList.contains("shovel") && !sceneFivePass) {
            FileManager.getAssetFile("scene-five.txt");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            boolean rapidsComplete = false;
            int raftHP = 5;
            while (raftHP > 0 && !rapidsComplete) {
                System.out.println("Rapids rush up to meet you in the middle of the river!\n Which way will you paddle to avoid them?");
                System.out.print("type 'paddle left' or 'paddle right': ");
                ANSWER = scanner.nextLine().strip().toLowerCase();
                boolean complete = false;
                while (!complete) {
                    switch (ANSWER) {
                        case "paddle left":
                            System.out.println("You paddle left. A tree branch in the river snags your raft! It takes some damage.");
                            raftHP--;
                            complete = true;
                            break;
                        case "paddle right":
                            System.out.println("You paddle right. A massive boulder stops your progress... \n After dislodging your raft you are free.");
                            complete = true;
                            break;
                        default:
                            System.out.println("You can't do that right now! Type 'paddle right' or 'paddle left'");
                            ANSWER = scanner.nextLine().strip().toLowerCase();
                    }
                }
                System.out.println("The river curves left\n You need to choose to hug the inner bank of center the raft in the river.");
                System.out.print("type 'hug the inner bank' or 'center the raft': ");
                ANSWER = scanner.nextLine().strip().toLowerCase();
                complete = false;
                while (!complete) {
                    switch (ANSWER) {
                        case "hug the inner bank":
                            System.out.println("A rock in the shallow inner bank scrapes your raft! ");
                            raftHP-=2;
                            complete = true;
                            break;
                        case "center the raft":
                            System.out.println("The raft hits no obstacles as you glide around the bend.");
                            complete = true;
                            break;
                        default:
                            System.out.println("You can't do that right now! Type 'hug the inner bank' or 'center the raft'");
                            ANSWER = scanner.nextLine().strip().toLowerCase();
                    }
                }
                System.out.println("Ahead, thorny branches almost cover the water.\n They will snag you unless you find a way to avoid them!");
                System.out.print("type 'duck under branches' or 'use paddle on branches': ");
                ANSWER = scanner.nextLine().strip().toLowerCase();
                complete = false;
                while (!complete) {
                    switch (ANSWER) {
                        case "duck under branches":
                            System.out.println("The branches puncture holes in your raft!");
                            raftHP-=3;
                            complete = true;
                            break;
                        case "use paddle on branches":
                            System.out.println("You push the thorny branches out of the way of your raft.");
                            rapidsComplete = true;
                            complete = true;
                            break;
                        default:
                            System.out.println("You can't do that right now! Type 'duck under branches' or 'use paddle on branches'");
                            ANSWER = scanner.nextLine().strip().toLowerCase();
                    }
                }
            }
            if(0 >= raftHP) {
                System.out.println("The raft punctures dumping you and Sara into the water!\nYou didn't reach the island.\n Game Over! Press enter to continue...");
                scanner.nextLine();
                startMenu();
            }
            FileManager.getAssetFile("scene-five-end.txt");
            sceneFivePass = true;
            FileManager.sceneWriter(true, "sceneFivePassed");
        }else{
            sceneFivePass = false;
        }
        return sceneFivePass;
    }
}

