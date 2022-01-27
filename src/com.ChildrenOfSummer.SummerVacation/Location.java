package com.ChildrenOfSummer.SummerVacation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Location {

    public Location() throws IOException, ParseException {
    }

    public JSONObject getJSONData(String search) throws IOException, ParseException
       JSONParser jParser = new JSONParser();

       String fileName = "Assets/Locations.JSON";
       FileReader reader = new FileReader(fileName);

       Object obj = jParser.parse(reader);
       JSONObject location = (JSONObject) obj;
       String fName = (String) location.get("Wood1");






}