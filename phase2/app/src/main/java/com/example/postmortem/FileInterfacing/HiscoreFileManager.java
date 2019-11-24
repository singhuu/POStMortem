package com.example.postmortem.FileInterfacing;


import com.example.postmortem.Hiscore;

import java.util.ArrayList;
import java.util.List;

public class HiscoreFileManager extends FileManager {

    private List<Hiscore> hiscores;

    public HiscoreFileManager(String appDataDir){
        super(appDataDir, "Hiscores.csv");
        hiscores = new ArrayList<>();
    }

    @Override
    void updateList(List<String[]> loadedData) {
        for(String[] hiscoreData: loadedData){
            String username = hiscoreData[0];
            int score = Integer.parseInt(hiscoreData[1]);

            Hiscore hiscore = new Hiscore(username, score);
            hiscores.add(hiscore);
        }
    }

    @Override
    String formatOutputData(){
        StringBuilder out = new StringBuilder();
        for(Hiscore hiscore: hiscores) {
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

    public List<Hiscore> getHiscores(){
        return hiscores;
    }
}
