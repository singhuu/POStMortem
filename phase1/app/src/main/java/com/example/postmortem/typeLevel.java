package com.example.postmortem;


import android.os.Bundle;
import android.view.View;


public class typeLevel extends Level {

    /** Initiates the typeLevel and sets the difficulty using the Level constructor*/
    public typeLevel(int difficulty) {
        super(difficulty);

    }

    /**Overriding draw for now as to not get any errors, might not use in future */
    @Override
    public void draw(View view) {

    }


    /**Chooses how to display this level when it is created in the app */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_level);

    }

}
