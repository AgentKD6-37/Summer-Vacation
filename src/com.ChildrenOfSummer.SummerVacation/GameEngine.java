package com.ChildrenOfSummer.SummerVacation;


import java.util.*;

public class GameEngine {
    ArrayList<String> inventory = new ArrayList<String>();
    String save = "Assets/save-game.JSON";
    Scanner scanner = new Scanner(System.in);


    public void execute() {
        startMenu();
        introduction();
    }
    public void startMenu() {
        SaveEditor.menuFiles();
        String startMenuChoice = scanner.nextLine().strip().toLowerCase();
        switch (startMenuChoice) {
            case "new game" -> playerCreator();
            case "load game" -> SaveEditor.getSaveFile();
            case "quit" -> System.exit(0);
        }

    }

    public void playerCreator() {
        System.out.println("this is the player creator");
    }
    public void introduction(){
        System.out.println("Insert first intro paragraph");
        System.out.println("Sara says we should go to the abandoned airport today, do you wish to go?");

        String startingAnswer = scanner.nextLine().strip();

        if (startingAnswer.equalsIgnoreCase("Yes")) {
            sceneOne();
        } else if (startingAnswer.equalsIgnoreCase("No")) {
            System.out.println("Exiting game");
            System.exit(0);
        }
        else {
            System.out.println("User input not valid, type 'Yes' or 'No'");
            introduction();
        }
    }

    // At the airport
    public void sceneOne(){
        System.out.println("Insert paragraph from scene one");
        sceneOneAction();
    }

    public void sceneOneAction(){
        System.out.println("You see random items scattered across the ground. You see some planks, rope,\n" +
                "shoes, hat, a rock, and a notebook.\n" +
                "Would you like to add some items?");

        Scanner sceneOneQuestionInventory = new Scanner(System.in);
        String sceneOneQuestionInventoryAnswer = sceneOneQuestionInventory.nextLine().strip();
        if (sceneOneQuestionInventoryAnswer.equalsIgnoreCase("Yes")){
            sceneOneWithItems();
        }
        else if (sceneOneQuestionInventoryAnswer.equalsIgnoreCase("No")){
            System.out.println("You decided not to add any items to your inventory.");
            sceneTwo();
        }
        else{
            System.out.println("User input not valid, type 'Yes' or 'No'");
            sceneOneAction();
        }

        while (sceneOneQuestionInventoryAnswer.equalsIgnoreCase("Yes")) {
            sceneOneWithItems();
        }

    }

    private void sceneOneWithItems() {
        String itemsArray[] = {"planks", "rope", "shoes", "hat", "rock", "notebook"};
        List<String> itemsList = Arrays.asList(itemsArray);
        System.out.println("Choose an item to add: planks, rope, shoes, a rock, a notebook");
        Scanner question = new Scanner(System.in);
        String answer = question.nextLine().strip();
        if (answer.equalsIgnoreCase("planks")){
            System.out.println("Adding items...");
            inventory.add("planks");
            System.out.println("Your inventory is: " + inventory);
            System.out.println("The items remaining on the ground are: " + itemsList);
        }
        else if (answer.equalsIgnoreCase("rope")){
            System.out.println("Adding items...");
            inventory.add("rope");
            System.out.println("Your inventory is: " + inventory);
            System.out.println("The items remaining on the ground are: " + itemsList);
        }
        else if (answer.equalsIgnoreCase("shoes")){
            System.out.println("Adding items...");
            inventory.add("shoes");
            System.out.println("Your inventory is: " + inventory);
            System.out.println("The items remaining on the ground are: " + itemsList);
        }
        else if (answer.equalsIgnoreCase("hat")){
            System.out.println("Adding items...");
            inventory.add("hat");
            System.out.println("Your inventory is: " + inventory);
            System.out.println("The items remaining on the ground are: " + itemsList);
        }
        else if (answer.equalsIgnoreCase("rock")){
            System.out.println("Adding items...");
            inventory.add("rock");
            System.out.println("Your inventory is: " + inventory);
            System.out.println("The items remaining on the ground are: " + itemsList);
        }
        else if (answer.equalsIgnoreCase("notebook")){
            System.out.println("Adding items...");
            inventory.add("notebook");
            System.out.println("Your inventory is: " + inventory);
            System.out.println("The items remaining on the ground are: " + itemsList);
        }
        else {
            System.out.println("Input invalid. Choose among the following: " + Arrays.toString(itemsArray));
            sceneOneWithItems();
        }
        sceneOneEnd();
    }

    public void sceneOneEnd(){
        System.out.println("Do you want to add additional items?");
        Scanner question = new Scanner(System.in);
        String answer = question.nextLine().strip();
        if (answer.equalsIgnoreCase("yes")){
            sceneOneWithItems();
        }
        else if (answer.equalsIgnoreCase("no")) {
            sceneTwo();
        }
        else {
            System.out.println("Input not valid");
            sceneOneEnd();
        }
    }

    public void sceneTwo(){
        System.out.println("Insert paragraph for scene two");
        System.out.println("Hello");
        sceneThree();
    }

    public void sceneThree(){
        System.out.println("Insert paragraph for scene three");
    }

    public void sceneFour(){
        System.out.println("work in progress");
    }

    public void sceneFive(){
        System.out.println("work in progress");
    }

    public void sceneSix(){
        System.out.println("work in progress");
    }

    public void sceneSeven(){
        System.out.println("work in progress");
    }

}