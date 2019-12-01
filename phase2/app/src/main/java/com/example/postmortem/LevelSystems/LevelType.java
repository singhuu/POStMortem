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
    /**
     * List Interface that stores the level types
     */
    private static final List<LevelType> LEVEL_TYPES =
            Collections.unmodifiableList(Arrays.asList(values()));
    /**
     * List interface that holds the size of the level
     */
    private static final int SIZE = LEVEL_TYPES.size();
    /**
     * Generates random values
     */
    private static final Random RANDOM = new Random();

    /**
     * Stores level id
     */
    private int levelId;

    LevelType(int levelId) {
        this.levelId = levelId;
    }

    /**
     * Returns the randomly generated level type
     *
     * @return the randomly generated level type
     */
    public static LevelType randomLevelType() {
        return LEVEL_TYPES.get(RANDOM.nextInt(SIZE));
    }

    /**
     * Gets level type from ID
     *
     * @param levelId stores the ID of the level
     * @return returns the type of the level from it's ID
     */
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

    /**
     * Returns level id
     *
     * @return the level id
     */
    public int getLevelId() {
        return this.levelId;
    }


}
