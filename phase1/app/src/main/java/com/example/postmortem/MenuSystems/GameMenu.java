package com.example.postmortem.MenuSystems;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class GameMenu {

    /** Menu Invariants, Types and Keys */
    public static final String MAIN_MENU = "Main Menu";
    public static final String LOGIN_MENU = "Login or Create a New User";
    public static final String GAME_OVER_MENU = "Game Over";

    public static final String MENU_TYPE = "menu_type";

    /** Menu items */
    protected List<View> items;


    protected String title;

    public GameMenu(String title){
        this.title = title;
        this.items = new ArrayList<>();
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

            case LOGIN_MENU:
                menu = new UserSelectMenu(menu_type);
                break;

            case GAME_OVER_MENU:
                menu = new GameOverMenu(menu_type);
                break;

            default:
                throw new RuntimeException(menu_type + " is not a valid menu type");

        }

        return menu;

    }

    protected void setColours(TextView view){
        view.setBackgroundColor(Color.rgb(208, 33, 32));
        view.setTextColor(Color.rgb(255, 255, 255));
    }

    public abstract List<View> buildMenuItems(AppCompatActivity context);

}
