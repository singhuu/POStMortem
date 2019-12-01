package com.example.postmortem.FileInterfacing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class FileInterface {

    /**
     * Object that stores file information
     */
    private File file;

    FileInterface(String filepath) {
        openFile(filepath);
    }

    private void openFile(String filePath) {
        file = new File(filePath);
        try {

            boolean exists = file.exists();
            boolean create = file.createNewFile();
            int wait = 0;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the data written in the file as a list of the lines of the file
     *
     * @return The data stored in the file
     */
    List<String> readFile() throws IOException {

        Scanner scan = new Scanner(new FileInputStream(file));
        return readDataFromFile(scan);

    }

    private List<String> readDataFromFile(Scanner scan) {

        LinkedList<String> data = new LinkedList<>();

        while (scan.hasNextLine()) {
            data.addLast(scan.nextLine());
        }

        return data;

    }

    /**
     * Overwrites the previous information in the file with the new information
     *
     * @param data the information to be written to the file
     * @throws IOException if an IOException occurs
     */
    void writeToFile(String data) throws IOException {

        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(data);
        out.flush();
        out.close();

    }

}
