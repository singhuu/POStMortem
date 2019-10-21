package com.example.postmortem;

abstract class Level {

  int difficulty;

  Level(int difficulty) {
    //do something, maybe add an id to each level so gamemanager can work with it
    this.difficulty = difficulty;
  }
}
