package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Location {

    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jParser=new JSONParser();

        String fileName = "SummerVacation\\location.json";
        FileReader reader=new FileReader(fileName);

        Object obj=jParser.parse(reader);
        JSONObject location=(JSONObject) obj;

        String fName=(String) location.get("Wood1");

//        System.out.println(fName);

    }


}