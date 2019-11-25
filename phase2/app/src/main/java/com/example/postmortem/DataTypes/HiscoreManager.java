package com.example.postmortem.DataTypes;

import com.example.postmortem.FileInterfacing.HiscoreFileController;

import java.util.List;

public class HiscoreManager {
    private HiscoreFileController fileController;
    private List<Hiscore> hiscores;

    public HiscoreManager(String appDataDir){
        this.fileController = new HiscoreFileController(appDataDir);
        this.hiscores = (List<Hiscore>) fileController.load();
    }

    /**
     * Gets the list of game hiscores
     * @return the list of hiscores
     */
    public List<Hiscore> getHiscores() {
        return hiscores;
    }
}
