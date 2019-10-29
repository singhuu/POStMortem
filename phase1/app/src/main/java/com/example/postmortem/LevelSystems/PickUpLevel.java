package com.example.postmortem.LevelSystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PickUpLevel extends Level {
    static final String[] possibleObjects = {"Deodorant", "Laptop", "Phone", "Pencil", "TCard",
                                            "Wallet", "Notebook", "Gym Bag", "Glasses", "Snack"};

    static final int WRONG_CHOICE_TIME = 1;

    private String targetObject;
    private String[] selectables;
    private int numCorrect = 0;

    public PickUpLevel(int difficulty){
        super(difficulty);

        ArrayList<String> selectablesList = new ArrayList<>(Arrays.asList(possibleObjects));
        updateInteractData(selectablesList);
    }

    // Code taken from https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
    public void updateInteractData(ArrayList<String> list){
        int totalItems = 6;
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
        selectables = Arrays.copyOf(abstractResultArr, abstractResultArr.length, String[].class);

        targetObject = selectables[(int) (Math.random() * selectables.length)];
    }

    public String getTarget(){
        return this.targetObject;
    }

    public String[] getSelectables() {
        return selectables;
    }

    @Override
    public int getScore() {
        return this.numCorrect;
    }

    public void increaseScore(){
        this.numCorrect += 1;
    }

    boolean checkSelectVal(String val){
        if(val.equals(targetObject)){
            updateInteractData(new ArrayList<>(Arrays.asList(possibleObjects)));
            return true;
        }

        return false;
    }
}