package com.example.client.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**A class that reads data from a file*/
public class FileReader {
    public ArrayList<String> readFile(String fileName) {
        ArrayList<String> linesArrayList = new ArrayList<>();
        MessageHandler.displayToUser("Name of file: '" + fileName + "'.\nFilePath: '" + fileName + "'.");
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                linesArrayList.add(line);
            }
            sc.close();
            MessageHandler.displayToUser("File was readied successfully.");
            return linesArrayList;
        } catch (IOException | NullPointerException e) {
            MessageHandler.displayToUser("Error with file, check path of the file. Check file's format: '<filename>.<FileFormat>'.`");
            return new ArrayList<>();
        }
    }

}
