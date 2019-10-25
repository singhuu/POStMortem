package com.example.postmortem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PickUpLevel extends Level {
    static final String[] possibleObjects = {"Deodorant", "Laptop", "Phone", "Pencil", "Tissue",
                                            "Wallet", "Notebook", "Gym Bag", "Glasses", "Backpack"};

    private String[] targetObjects;
    int numCorrect = 0;

    public PickUpLevel(int difficulty){
        super(difficulty);
        ArrayList<String> targetObjectsList = new ArrayList<>(Arrays.asList(possibleObjects));
        targetObjects = getTargets(targetObjectsList);
    }

    @Override
    public int getScore() {
        return 0;
    }

    // Code taken from https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
    private String[] getTargets(ArrayList<String> list){
        int totalItems = 3;
        Random rand = new Random();

        // create a temporary list for storing
        // selected element
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            // take a random index between 0 to size
            // of given List
            int randomIndex = rand.nextInt(list.size());

            // add element in temporary list
            newList.add(list.get(randomIndex));

            list.remove(randomIndex);
        }

        Object[] abstractResultArr = newList.toArray();
        return Arrays.copyOf(abstractResultArr, abstractResultArr.length, String[].class);
    }

    String createSearchString() {
        String searchStr = "";
        StringBuilder searchStrBuilder = new StringBuilder("Find the ");
        for(String target : targetObjects){
            searchStrBuilder.append(target);

            if(!target.equals(targetObjects[targetObjects.length - 1]))
                searchStrBuilder.append(", ");
        }

        searchStr += searchStrBuilder.toString();
        searchStr += "!";

        return searchStr;
    }

    boolean checkSelectVal(String val){
        for(String target : targetObjects){
            System.out.println("Correct");
            if(val.equals(target))
                return true;
        }

        System.out.println("WRONG");
        return false;
    }
}