package com.example.postmortem.FileInterfacing;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract class FileController {
    FileInterface fileInterface;

    FileController(String appDataDir, String fileName){
        String filepath = appDataDir + System.getProperty("file.separator") + fileName;
        this.fileInterface = new FileInterface(filepath);
    }

    /**
     * Clears the saved object list and replaces it with new instances loaded from the file
     */
    public void load(){
        try{
            List<String> fileData = fileInterface.readFile();
            List<String[]> seperatedData = formatReadData(fileData);
            updateList(seperatedData);
        } catch (IOException e){
            Log.e("IOException" , e.getMessage());
        }
    }

    private List<String[]> formatReadData(List<String> fileData){
        List<String[]> seperatedData = new ArrayList<>();
        for(String line: fileData){
            seperatedData.add(line.split(","));
        }
        return seperatedData;
    }

    abstract void updateList(List<String[]> loadedData);

    /**
     * Overwrites the userdata file with the currently stored user information
     */
    public void save(){
        try{
        String output = formatOutputData();
        fileInterface.writeToFile(output);
        } catch (IOException e) {
            Log.e("IOException" , e.getMessage());
        }
    }

    abstract String formatOutputData();
}
