package com.example.postmortem;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public abstract class GameMenu {

    /** Menu Invariants, Types and Keys */
    public static final String MAIN_MENU = "Main Menu";

    public static final String MENU_TYPE = "menu_type";


    private String title;

    public GameMenu(String title){
        this.title = title;
    }

    public static Intent openMenu(AppCompatActivity packageContext, String menu_type){
        Intent intent = new Intent(packageContext, MenuActivity.class);
        intent.putExtra(MENU_TYPE, menu_type);
        return intent;
    }

    static GameMenu createMenu(String menu_type){
        GameMenu menu;

        switch (menu_type){
            case MAIN_MENU:
                menu = new MainMenu(menu_type);
                break;

            default:
                throw new RuntimeException(menu_type + " is not a valid menu type");

        }

        return menu;

    }

}
