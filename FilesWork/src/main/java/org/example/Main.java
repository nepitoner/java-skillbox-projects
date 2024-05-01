package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Main {
    public static ArrayList<Lines> getLines(Document doc) {
        ArrayList<Lines> linesList = new ArrayList<>();
        Elements lines = doc.select(".js-metro-line");
        for (Element line : lines) {
            String lineName = line.text();
            String lineNumber = line.attr("data-line");
            Lines metroLine = new Lines(lineName, lineNumber);
            linesList.add(metroLine);
        }
        return linesList;
    }
    public static Map<Lines,ArrayList<Stations>> getStations(Document doc, ArrayList<Lines> linesList) {
        Map<Lines,ArrayList<Stations>> stationsList = new HashMap<>();
        for (Lines line : linesList) {
//            System.out.println(line);
            String lineSelector = ".js-metro-stations[data-line='" + line.getNumber() + "']";
            Element lineStations = doc.selectFirst(lineSelector);
            assert lineStations != null;
            Elements stationElements = lineStations.select(".single-station");
            ArrayList<Stations> stations = new ArrayList<>();
            for (Element station : stationElements) {
                String number = station.selectFirst(".num").text();
                String name = station.selectFirst(".name").text();
                Stations metroStation = new Stations(name, number);
                stations.add(metroStation);
                stationsList.put(line, stations);
            }
        }
        return stationsList;
    }


    public static void main(String[] args) throws IOException {
        String url = "https://skillbox-java.github.io/";
        Document doc = Jsoup.connect(url).get();
        ArrayList<Lines> linesList = getLines(doc);
        System.out.println(linesList);
        Map<Lines,ArrayList<Stations>> stationsList = getStations(doc, linesList);
        System.out.println(stationsList);

        for(Lines key : stationsList.keySet()) {
            System.out.println(key + " " + stationsList.get(key));
        }

        FileFinder fileFinder = new FileFinder();
        ArrayList<String> jsonCsvFiles = new ArrayList<>();
        System.out.println(fileFinder.readFile("src/main/resources/data", jsonCsvFiles));

        System.out.println(new JsonParse().readJson("src/main/resources/data/4/6/depths-3.json"));
        System.out.println(new CsvParse().readCsv("src/main/resources/data/4/6/dates-1.csv"));

        ObjectMapper objectMapper = new ObjectMapper();
        File map = new File("map.json");
        objectMapper.writeValue(map, stationsList);

        String[] jsonFiles = {
                "src/main/resources/data/4/6/depths-3.json",
                "src/main/resources/data/2/4/depths-1.json",
                "src/main/resources/data/7/1/depths-2.json"};
        String[] csvFiles = {
                "src/main/resources/data/0/5/dates-2.csv",
                "src/main/resources/data/4/6/dates-1.csv",
                "src/main/resources/data/9/6/dates-3.csv"};

        StationProperty stationProperty = new StationProperty();
        stationProperty.writeJson(jsonFiles, csvFiles, stationsList);

    }
}