package com.example.postmortem.FileInterfacing;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

abstract class FileController {
  /**
   * File Interface object
   */
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
      List<String[]> separatedData = formatReadData(fileData);
      loadedList = updateList(separatedData);
    } catch (IOException e) {
      if (e.getMessage() != null)
        Log.e("IOException", e.getMessage());
    }

    return loadedList;
  }

  /**
   * Formats the read data from the file
   *
   * @param fileData List Interface that stores the file data
   * @return the formatted data
   */
  private List<String[]> formatReadData(List<String> fileData) {
    List<String[]> separatedData = new ArrayList<>();
    for (String line : fileData) {
      separatedData.add(line.split(","));
    }
    return separatedData;
  }

  /**
   * Abstract method that returns the updated list
   *
   * @param loadedData the read data
   * @return an updated list of elements
   */
  abstract List updateList(List<String[]> loadedData);

  /**
   * Overwrites the userdata file with the currently stored user information
   */
  public void save(List objects) {
    try {
      String output = formatOutputData(objects);
      fileInterface.writeToFile(output);
    } catch (IOException e) {
      if (e.getMessage() != null)
        Log.e("IOException", e.getMessage());
    }
  }

  /**
   * Abstract method that returns the formatted data
   *
   * @param objects the list of objects
   * @return the formatted output
   */
  abstract String formatOutputData(List objects);
}
