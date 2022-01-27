package com.ChildrenOfSummer.SummerVacation;

import java.util.Objects;

class Player {
    String playerLocation;
    String playerName;
    String[] playerInventory;

    void sleep(){
        Clock.incrementNextDay();
    }

    public void move(String direction){
        String tempLocation = Location.getNewLocation(playerLocation, direction);
        if (tempLocation.equals("Off Map")){
            System.out.println("You can't go that way!");
        }else{
            playerLocation = tempLocation;
            System.out.println("You move "+ direction + ".");
            Location.getDescription(playerLocation);
        }
    }

    void wakeUp(){
        Clock.wakeUpTime();
    }

    public void talk(String npcName){
        int number = randomNumberGenerator();
        if (number <= 3) {
            System.out.println(Location.getDialogue(npcName, number));
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

    public int randomNumberGenerator(){
        //1-3, 3-6, 1-6
        return (int)(Math.random() * ((5) + 1)+1);
    }
}