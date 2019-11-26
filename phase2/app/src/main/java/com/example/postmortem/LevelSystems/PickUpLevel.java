package com.example.postmortem.LevelSystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PickUpLevel extends Level {

    /**
     * Stores the names of possible choices in the game
     */
    static final String[] possibleObjects = {"Deodorant", "Laptop", "Phone", "Pencil", "TCard",
            "Wallet", "Notebook", "Gym Bag", "Glasses", "Snack"};

    /**
     * Stores the number of wrong choices
     */
    static final int WRONG_CHOICE_TIME = 1;

    /**
     * String that stores target object
     */
    private String targetObject;
    /**
     * List that stores possible choices
     */
    private String[] selectables;
    /**
     * Stores the correct choices
     */
    private int numCorrect = 0;

    /**
     * Constructor Method that creates a new list of selectables
     * @param difficulty the difficulty of the level
     */
    public PickUpLevel(int difficulty) {
        super(difficulty);

        ArrayList<String> selectablesList = new ArrayList<>(Arrays.asList(possibleObjects));
        updateInteractData(selectablesList);
    }

    /**
     * Updates Interactive Data like the possible selectables and
     * creates a list of 6 from possibleObjects
     * @param list
     */
    // Code taken from https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
    public void updateInteractData(ArrayList<String> list) {
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

    /**
     * Getter method to get the target object
     * @return targetObject string
     */
    public String getTarget() {
        return this.targetObject;
    }

    /**
     * Getter method to get the target Selectables
     */
    public String[] getSelectables() {
        return selectables;
    }

    @Override
    /**
     * Getter method to get the score
     * @return the correct score which is the number of correct answer * 10
     */
    public int getScore() {
        return this.numCorrect * 10;
    }

    /**
     * Tracks the increase in score by 1
     */
    public void increaseScore() {
        this.numCorrect += 1;
    }

    /**
     * Check the selected value is a part of the list
     * @param val the String to be checked
     * @return true if it finds something else false
     */
    boolean checkSelectVal(String val) {
        if (val.equals(targetObject)) {
            updateInteractData(new ArrayList<>(Arrays.asList(possibleObjects)));
            return true;
        }

        return false;
    }
}