package org.example;
import org.apache.commons.io.FilenameUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FileFinder {
    public ArrayList<String> readFile(String path, ArrayList<String> jsonCsvFiles) {

        File folder = new File(path);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile()) {
                if (FilenameUtils.getExtension(file.getPath()).equals("csv") ||
                        FilenameUtils.getExtension(file.getPath()).equals("json")) {
                    jsonCsvFiles.add(file.getPath());
                }
            } else {
                readFile(file.getPath(), jsonCsvFiles);
            }
        }

        return jsonCsvFiles;
    }
}
