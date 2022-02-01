package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Scanner;

class Player {
    String playerZone;
    String playerLocation;
    String playerName;
    ArrayList<String> playerInventory;

    public Player(String playerName, String playerLocation, String playerZone, ArrayList<String> playerInventory) {
        this.playerZone = playerZone;
        this.playerLocation = playerLocation;
        this.playerName = playerName;
        this.playerInventory = playerInventory;
    }

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
            System.out.println(NPCname + " is here.");
        }

    }


    void wakeUp(){
        Clock.wakeUpTime();
    }

    public void talk(String npcName){
        int number = randomNumberGenerator();
        if (number <= 3) {
            SaveEditor.getNPCsDialog(npcName,number);
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