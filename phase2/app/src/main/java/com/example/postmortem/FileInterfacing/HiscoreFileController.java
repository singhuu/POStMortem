package com.example.postmortem.FileInterfacing;


import com.example.postmortem.Hiscore;

import java.util.ArrayList;
import java.util.List;

public class HiscoreFileController extends FileController {

    public HiscoreFileController(String appDataDir){
        super(appDataDir, "Hiscores.csv");
    }

    @Override
    List updateList(List<String[]> loadedData) {
        List<Hiscore> hiscores = new ArrayList<>();
        //create the hiscores
        for(String[] hiscoreData: loadedData){
            String username = hiscoreData[0];
            int score = Integer.parseInt(hiscoreData[1]);

            Hiscore hiscore = new Hiscore(username, score);
            hiscores.add(hiscore);
        }
        return hiscores;
    }

    @Override
    String formatOutputData(List hiscores){
        StringBuilder out = new StringBuilder();
        for(Object obj: hiscores) {
            Hiscore hiscore = (Hiscore) obj;
            out.append(createDataFromHiscore(hiscore));
            out.append("\n");
        }

        return out.toString();

    }

    private StringBuilder createDataFromHiscore(Hiscore hiscore){
        StringBuilder data = new StringBuilder();
        data.append(hiscore.getUsername());
        data.append(",");
        data.append(hiscore.getScore());
        return data;
    }
}
