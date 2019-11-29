package com.example.postmortem.FileInterfacing;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

abstract class FileController {
  private FileInterface fileInterface;

  FileController(String appDataDir, String fileName) {
    String filepath = appDataDir + System.getProperty("file.separator") + fileName;
    this.fileInterface = new FileInterface(filepath);
  }

  /**
   * Clears the saved object list and replaces it with new instances loaded from the file
   */
  public List load() {
    List loadedList = new LinkedList<>();
    try {
      List<String> fileData = fileInterface.readFile();
      List<String[]> seperatedData = formatReadData(fileData);
      loadedList = updateList(seperatedData);
    } catch (IOException e) {
      Log.e("IOException", e.getMessage());
    } finally {
      return loadedList;
    }
  }

  private List<String[]> formatReadData(List<String> fileData) {
    List<String[]> seperatedData = new ArrayList<>();
    for (String line : fileData) {
      seperatedData.add(line.split(","));
    }
    return seperatedData;
  }

  abstract List updateList(List<String[]> loadedData);

  /**
   * Overwrites the userdata file with the currently stored user information
   */
  public void save(List objects) {
    try {
      String output = formatOutputData(objects);
      fileInterface.writeToFile(output);
    } catch (IOException e) {
      Log.e("IOException", e.getMessage());
    }
  }

  abstract String formatOutputData(List objects);
}
