package de.legoshi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

    public static String readFile(String path) {
        StringBuilder line = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String read;
            while ((read = reader.readLine()) != null) {
                if (read.equals("")) {
                    line.append(read).append(" \n");
                } else {
                    line.append(read).append("\n");
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line.toString();
    }

}
