package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONArray;

import java.util.Scanner;

class Player {
    String playerZone = "Suburb";
    String playerLocation = "Player's House";
    String playerName = "Default";
    String[] playerInventory;

    void sleep(){
        Clock.incrementNextDay();
    }

    public void move(String direction){
        String tempLocation = SaveEditor.getNewLocation(playerZone, playerLocation, direction);
        JSONArray NPCname=SaveEditor.getNPCsName(playerZone,playerLocation);

        if (tempLocation.equals("Off Map")){
            System.out.println(tempLocation);
            System.out.println("You can't go that way!");
        }else { //success on move
            playerLocation = tempLocation;
            playerZone = SaveEditor.getNewZone(playerLocation);
            System.out.println("You move " + direction + ".");
            SaveEditor.getLocationDescription(playerLocation, playerZone);
            System.out.println("You are bumped into: "+NPCname);
            System.out.println("Who would you like to talk to ?");
            Scanner input = new Scanner(System.in);
            String chatyNPC= input.nextLine();
            talk(chatyNPC);
        }

    }


    void wakeUp(){
        Clock.wakeUpTime();
    }

    public void talk(String npcName){
        int number = randomNumberGenerator();
        if (number <= 3) {
        }else{
            talk(npcName);
        }
    }

    void shop(){

    }

    public void get(String item){

    }

    void drop(){

    }

    void combine(){

    }

    public static int randomNumberGenerator(){
        //1-3, 3-6, 1-6
        return (int)(Math.random() * ((5) + 1)+1);
    }
}