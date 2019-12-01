package com.example.postmortem.FileInterfacing;


import com.example.postmortem.DataTypes.Hiscore;

import java.util.ArrayList;
import java.util.List;

public class HiscoreFileController extends FileController {

    public HiscoreFileController(String appDataDir) {
        super(appDataDir, "Hiscores.csv");
    }

    /**
     * Updates the List of High Scores
     *
     * @param loadedData the read data
     * @return the updated high scores
     */
    @Override
    List updateList(List<String[]> loadedData) {
        List<Hiscore> hiscores = new ArrayList<>();
        //create the hiscores
        for (String[] hiscoreData : loadedData) {
            String username = hiscoreData[0];
            int score = Integer.parseInt(hiscoreData[1]);

            Hiscore hiscore = new Hiscore(username, score);
            hiscores.add(hiscore);
        }
        return hiscores;
    }

    /**
     * Formats the output list of high scores
     *
     * @param hiscores the list that contains all the high scores
     * @return the formatted output
     */
    @Override
    String formatOutputData(List hiscores) {
        StringBuilder out = new StringBuilder();
        for (Object obj : hiscores) {
            Hiscore hiscore = (Hiscore) obj;
            out.append(createDataFromHiscore(hiscore));
            out.append("\n");
        }

        return out.toString();

    }

    /**
     * Creates data that links the high score to a particular username
     *
     * @param hiscore The object the stores the high score variables
     * @return String that contains the data of username and high score
     */
    private StringBuilder createDataFromHiscore(Hiscore hiscore) {
        StringBuilder data = new StringBuilder();
        data.append(hiscore.getUsername());
        data.append(",");
        data.append(hiscore.getScore());
        return data;
    }
}
