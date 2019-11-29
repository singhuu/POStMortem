package com.example.postmortem.LevelSystems;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum LevelType {

  PICKUP(0),
  TAP(1),
  TYPE(2),
  SWIPE(3);

  //COde for randomly getting values from
  //https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
  private static final List<LevelType> LEVEL_TYPES =
          Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = LEVEL_TYPES.size();
  private static final Random RANDOM = new Random();

  private int levelId;

  LevelType(int levelId) {
    this.levelId = levelId;
  }

  public static LevelType randomLevelType() {
    return LEVEL_TYPES.get(RANDOM.nextInt(SIZE));
  }

  public static LevelType getLevelTypeFromId(int levelId) {
    switch (levelId) {
      case 1:
        return TAP;

      case 2:
        return TYPE;

      case 3:
        return SWIPE;

      default:
        return PICKUP;
    }
  }

  public int getLevelId() {
    return this.levelId;
  }


}
