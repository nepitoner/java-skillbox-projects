package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private final static StringBuilder sb = new StringBuilder();
    private static final Path DSPATH = Paths.get("src/main/resources/result.txt");

    private static void print(TreeSet<String> set, String mainPage, String tab) {
        tab += "\t";
        for (String st : set) {
            String c = st.substring(0, st.lastIndexOf("/"));
            if (c.equals(mainPage)) {
                sb.append(tab).append(st).append("\n");
                print(set, st, tab);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        String url = "https://lenta.ru";

        TreeSet<String> set = new TreeSet<>(new ForkJoinPool().invoke(new LinksGetter(url)));
        sb.append(url).append("\n");
        print(set, url, "");
        Files.write(DSPATH, sb.toString().getBytes());
    }
}
