package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

class Test {
    public static void main(String[] args) {
        Player player1 = new Player();
        JSONArray list = SaveEditor.getLocationItems(player1.playerLocation,player1.playerZone);
        System.out.println("print "+ list);
    }
}