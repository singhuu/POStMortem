package com.example.postmortem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

  private  GameMenu menu_controller;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu);

    Intent intent = getIntent();
    String menu_type = intent.getStringExtra(GameMenu.MENU_TYPE);

    //create the backbone of the menu
    this.menu_controller = GameMenu.createMenu(menu_type);
  }
}
