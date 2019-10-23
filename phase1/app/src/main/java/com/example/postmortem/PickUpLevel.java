package com.example.postmortem;

public class PickUpLevel extends Level {
    static final String[] possibleObjects = {"Deodorant", "Laptop", "Phone", "Pencil", "Tissue",
                                            "Wallet", "Notebook", "Gym Bag", "Glasses", "Backpack"};

    private String[] targetObjects;
    int numCorrect = 0;

    public PickUpLevel(int difficulty){
        super(difficulty);
        targetObjects = new String[3];

        targetObjects[0] = possibleObjects[(int)(Math.random() * possibleObjects.length)];
        targetObjects[1] = possibleObjects[(int)(Math.random() * possibleObjects.length)];
        targetObjects[2] = possibleObjects[(int)(Math.random() * possibleObjects.length)];
    }

    @Override
    public int getScore() {
        return 0;
    }

    String createSearchString() {
        String searchStr = "Find the ";
        StringBuilder searchStrBuilder = new StringBuilder(searchStr);
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
            System.out.println("Hello");
            if(val.equals(target))
                return true;
        }

        return false;
    }
}