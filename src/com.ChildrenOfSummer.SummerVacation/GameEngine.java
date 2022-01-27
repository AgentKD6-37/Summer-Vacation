package com.ChildrenOfSummer.SummerVacation;


import java.util.*;

public class GameEngine {
    ArrayList<String> inventory = new ArrayList<String>();
    String save = "Assets/save-game.JSON";
    Scanner scanner = new Scanner(System.in);
    String ANSWER;

    public void execute() {
        startMenu();
        introduction();
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
    public void introduction(){
        System.out.println("Insert first intro paragraph");
        System.out.println("Sara says we should go to the abandoned airport today, do you wish to go?");

        ANSWER = scanner.nextLine().strip();

        if (ANSWER.equalsIgnoreCase("Yes")) {
            sceneOne();
        } else if (ANSWER.equalsIgnoreCase("No")) {
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


        ANSWER = scanner.nextLine().strip();
        if (ANSWER.equalsIgnoreCase("Yes")){
            sceneOneWithItems();
        }
        else if (ANSWER.equalsIgnoreCase("No")){
            System.out.println("You decided not to add any items to your inventory.");
            sceneTwo();
        }
        else{
            System.out.println("User input not valid, type 'Yes' or 'No'");
            sceneOneAction();
        }

        while (ANSWER.equalsIgnoreCase("Yes")) {
            sceneOneWithItems();
        }
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
                        + "The items remaining on the ground are " + itemsList );
                break;
            case "rope":
                inventory.add("rope");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList );
                break;
            case "shoes":
                inventory.add("shoes");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList );
                break;
            case "hat":
                inventory.add("hat");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList );
                break;
            case "rock":
                inventory.add("rock");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList );
                break;
            case "notebook":
                inventory.add("notebook");
                System.out.println("Adding items...\n" +
                        "Your inventory is: " + inventory + "\n"
                        + "The items remaining on the ground are " + itemsList );
                break;
            default:
                System.out.println("Input invalid. Choose among the following: " + Arrays.toString(itemsArray));
                sceneOneWithItems();
        }
        sceneOneEnd();
    }


    public void sceneOneEnd(){
        System.out.println("Do you want to add additional items?");
        ANSWER = scanner.nextLine().strip();
        if (ANSWER.equalsIgnoreCase("yes")){
            sceneOneWithItems();
        }
        else if (ANSWER.equalsIgnoreCase("no")) {
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

    public int randomNumberGenerator(){
        //1-3, 3-6, 1-6
        int randomNumber = (int)(Math.random() * ((6) + 1));
        return randomNumber;
    }

}