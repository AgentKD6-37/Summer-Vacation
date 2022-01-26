package com.ChildrenOfSummer.SummerVacation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameEngine {
    ArrayList<String> inventory = new ArrayList<String>();


    public void execute() {
        System.out.println("Welcome to Summer Vacation");
        System.out.println("Insert first intro paragraph");
        introduction();
    }

    public void introduction(){
        System.out.println("Sara says we should go to the abandoned airport today, do you wish to go?");

        Scanner startingQuestion = new Scanner(System.in);
        String startingAnswer = startingQuestion.nextLine().strip();

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
    }

    private void sceneOneWithItems() {
        String itemsArray[] = {"planks", "rope", "shoes", "hat", "rock", "notebook"};
        List<String> itemsList = Arrays.asList(itemsArray);

        System.out.println("You decided to add some items. Correct?");
        Scanner question = new Scanner(System.in);
        String answer = question.nextLine().strip();
        while (answer.equalsIgnoreCase("Yes")) {

            System.out.println("Choose an item to add: planks, rope, shoe, a rock, a notebook");
            Scanner sceneOneItems = new Scanner(System.in);
            String sceneOneItemsAnswer = sceneOneItems.nextLine().strip();
            inventory.add(sceneOneItemsAnswer);
            itemsList.remove("\""+sceneOneItemsAnswer+"\"");
            System.out.println("Your inventory is: " + inventory);
            System.out.println("The items remaining on the ground are: " + itemsList);

            System.out.println("Adding items...");
            System.out.println("Do you want to add additional items?");

            answer = question.nextLine().strip();
            if (answer.equalsIgnoreCase("Yes")){
                sceneOneWithItems();
            }
            else if (answer.equalsIgnoreCase("No")){
                System.out.println("No additional items were added");
                break;
            }
            else{
                System.out.println("Input not valid.");
                sceneOneWithItems();
            }
        }
        sceneTwo();
    }



    public void sceneTwo(){
        System.out.println("Insert paragraph for scene two");
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