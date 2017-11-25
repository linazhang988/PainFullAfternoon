package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {


    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        return response;
        //this transform raw data into item strings, break by ##
        //the strings inside the arraylist won't have the ## sign
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException {
        ArrayList<String> value;
        ArrayList<String> valueOfKeys=new ArrayList<String>();
        if(patternMatchName(rawItem) && patternMatchPrice(rawItem) && patternMatchType(rawItem) && patternMatchExpiration(rawItem)) {
            ArrayList<String> rawData = parseRawDataIntoStringArray(rawItem);
            ArrayList<String> keyValue = findKeyValuePairsInRawItemData(rawData.get(rawData.size()-1));
            //breaks string into key and value pair, such as name:milk
            String valueOfKey;
            for (int i = 0; i < keyValue.size(); i++) {
                value = splitStringWithRegexPattern(":", keyValue.get(i));
                valueOfKey = value.get(1).toLowerCase();
                valueOfKeys.add(valueOfKey);
            }
            return new Item(valueOfKeys.get(0),Double.parseDouble(valueOfKeys.get(1)),valueOfKeys.get(2),valueOfKeys.get(3));
        }
        else {
            throw new ItemParseException();
        }
    }
//    public String nameValue(String rawItem){
//        ArrayList<String> name=new ArrayList<String>();
//        if(patternMatchName(rawItem)) {
//            ArrayList<String>keyValue = findKeyValuePairsInRawItemData(rawItem);
//            name = splitStringWithRegexPattern(":",keyValue.get(0));
//        }
//        return name.get(1);
//    }

    public boolean patternMatchName(String rawItem){
        String nameRegex = "n..e:\\w+";

        Pattern patternName = Pattern.compile(nameRegex,Pattern.CASE_INSENSITIVE);
        //Pattern patternSplit = Pattern.compile(":");

        Matcher matchName = patternName.matcher(rawItem);
        return matchName.find();
    }
    public boolean patternMatchPrice(String rawItem){
        String priceRegex = "p...e:\\d...";
        Pattern patternPrice = Pattern.compile(priceRegex,Pattern.CASE_INSENSITIVE);
        Matcher matchPrice = patternPrice.matcher(rawItem);
        return matchPrice.find();
    }
    public boolean patternMatchType(String rawItem){
        String typeRegex = "t..e:\\w...";
        Pattern patternType = Pattern.compile(typeRegex,Pattern.CASE_INSENSITIVE);
        Matcher matchType = patternType.matcher(rawItem);
        return matchType.find();
    }
    public boolean patternMatchExpiration(String rawItem){
        String expirationRegex = "e........n:\\d.......\\d";
        Pattern patternExpiration = Pattern.compile(expirationRegex, Pattern.CASE_INSENSITIVE);
        Matcher matchExpiration = patternExpiration.matcher(rawItem);
        return matchExpiration.find();
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;|^|%|!|@|*]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
        //this breaks the raw data into different attribute and values( key:value)
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }




}
