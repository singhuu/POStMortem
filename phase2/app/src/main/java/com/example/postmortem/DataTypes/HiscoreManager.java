package com.example.postmortem.DataTypes;

import com.example.postmortem.FileInterfacing.HiscoreFileController;

import java.util.List;

public class HiscoreManager {

  private static HiscoreManager manager;

  private HiscoreFileController fileController;
  private List<Hiscore> hiscores;

  private HiscoreManager(String appDataDir) {
    this.fileController = new HiscoreFileController(appDataDir);
    this.refresh();
  }

  /**
   * Sets the directory for the application data and creates the instance of the class
   *
   * @param directory the directory of the program files
   */
  public static void initialize(String directory) {
    manager = new HiscoreManager(directory);
  }

  /**
   * Gets the manager for hiscores
   * There is only one instance of this class at a time, which can be accessed by many
   * classes at once
   *
   * @return the hiscore manger
   */
  public static HiscoreManager getManager() {
    return manager;
  }

  /**
   * Refreshes the high score
   */
  @SuppressWarnings("unchecked")
  private void refresh() {
    this.hiscores = (List<Hiscore>) fileController.load();
  }

  /**
   * Saves the current state of the hiscores list to the file
   */
  public void saveState() {
    fileController.save(hiscores);
  }

  /**
   * Gets the list of game hiscores
   *
   * @return the list of hiscores
   */
  public List<Hiscore> getHiscores() {
    return hiscores;
  }

  /**
   * Ensures High Scores are added
   *
   * @param newHiscore new hiscore from recent play-through
   */
  public void addHiscore(Hiscore newHiscore) {
    int i = 0;
    while (i < hiscores.size() && i < 5) {
      Hiscore hiscore = hiscores.get(i);

      if (hiscore.getScore() < newHiscore.getScore()) {
        hiscores.add(i, newHiscore);
        i = 5;
      } else {
        i++;
      }

    }
    //if the hiscore is lower than all others but there is more space than add it anyway
    if (i < 5) {
      hiscores.add(newHiscore);
    }
    //ensure that hiscores conforms to its maximum size 5
    int newLength = Math.min(5, hiscores.size());
    hiscores = hiscores.subList(0, newLength);

  }


}
