package com.ChildrenOfSummer.SummerVacation;


import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.*;

public class GameEngine {
    ArrayList<String> inventory = new ArrayList<String>();
    String save = "Assets/save-game.JSON";
    Scanner scanner = new Scanner(System.in);   //takes direct input currently from the user and passes it to the program
    String ANSWER;
    Player player1 = new Player();

    public void execute() {
        startMenu();
        introduction();
        preSceneOne();
    }

    public void startMenu() {
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

    public void playerCreator() {
        System.out.println("this is the player creator");
    }

    public void introduction() {
        SaveEditor.getAssetFile("introduction.txt");
    }

    public void preSceneOne() {
        System.out.println("Which way would you like to go?\n");
        ANSWER = scanner.nextLine().strip().toLowerCase();
            switch (ANSWER) {
                case "go north":
                    player1.move("north");
                    sceneOne();
                    preSceneOne();
                    break;
                case "go east":
                    player1.move("east");
                    sceneOne();
                    preSceneOne();
                    break;
                case "go south":
                    player1.move("south");
                    sceneOne();
                    preSceneOne();
                    break;
                case "go west":
                    player1.move("west");
                    sceneOne();
                    preSceneOne();
                    break;
                case "quit":
                    System.exit(0);
                default:
                    System.out.println("I can't go that way!");
                    preSceneOne();
            }
    }

    // At the airport
    public void sceneOne() {
        if(player1.playerLocation.equals("Paine Field")) {
            SaveEditor.getAssetFile("scene-one.txt");
            sceneOneAction();
        }
    }

    public void sceneOneAction() {
        JSONArray list = SaveEditor.getLocationItems(player1.playerLocation,player1.playerZone);
        System.out.println(" You see these items scattered across the ground: " + list);
        System.out.print("What would you like to do?");
        ANSWER = scanner.nextLine().strip();
        String[] answerWords = ANSWER.split(" ");
        System.out.println(Arrays.toString(answerWords));
        String verb = answerWords[0];
        String noun = answerWords[answerWords.length-1];

        switch (verb){
            case "get":
                //do stuff
            case "combine":
                //do other stuff
            case "use":
                //do final stuff
            case "talk":
                System.out.println("There's no one to talk to!");
                break;
            case "help":
                System.out.println("No help is currently available. Blame the Developers!");
                break;
            case "quit":
                System.exit(0);
            default:
                System.out.println("I didn't understand that command. for help type help.");
        }
        //sceneOneAction();
        SaveEditor.getAssetFile("ending.txt");
        try {
            System.in.read();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void sceneOneWithItems() {
        String itemsArray[] = {"planks", "rope", "shoes", "hat", "rock", "notebook"};
        List<String> itemsList = Arrays.asList(itemsArray);
        System.out.println("Choose an item to add: planks, rope, shoes, a rock, a notebook");
        Scanner question = new Scanner(System.in);
        String answer = question.nextLine().strip().toLowerCase();

        switch (answer) {
            case "planks":
                inventory.add("planks");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList);
                break;
            case "rope":
                inventory.add("rope");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList);
                break;
            case "shoes":
                inventory.add("shoes");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList);
                break;
            case "hat":
                inventory.add("hat");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList);
                break;
            case "rock":
                inventory.add("rock");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList);
                break;
            case "notebook":
                inventory.add("notebook");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList);
                break;

            default:
                System.out.println("Input invalid. Choose among the following: " + Arrays.toString(itemsArray));
                sceneOneWithItems();
        }
        sceneOneEnd();
    }


    public void sceneOneEnd() {
        System.out.println("Do you want to add additional items?");
        ANSWER = scanner.nextLine().strip();
        if (ANSWER.equalsIgnoreCase("yes")) {
            sceneOneWithItems();
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