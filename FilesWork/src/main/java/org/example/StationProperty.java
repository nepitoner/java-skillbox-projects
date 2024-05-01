package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StationProperty {
    private final Map<String, Stations> stationInfoMap = new HashMap<>();
    private final  ObjectMapper objectMapper = new ObjectMapper();;
    private void readJson(String[] jsonFiles, Map<Lines,ArrayList<Stations>> stationsList) {
        for (String jsonFile : jsonFiles) {
            try {
                String jsonData = new String(Files.readAllBytes(Paths.get(jsonFile)));
                ArrayNode jsonStationsArray = (ArrayNode) objectMapper.readTree(jsonData);
                for (int i = 0; i < jsonStationsArray.size(); i++) {
                    ObjectNode jsonStation = (ObjectNode) jsonStationsArray.get(i);
                    String name = jsonStation.get("station_name").asText();
                    String number = "";
                    String line = "";
                    for(Lines l: stationsList.keySet()) {
                        for (Stations st : stationsList.get(l)) {
                            if (name.equals(st.getName())) {
                                number = l.getNumber();
                                line = l.getName();
                                break;
                            }
                        }
                    }
                    double depth = jsonStation.get("depth").asDouble();

                    if (stationInfoMap.containsKey(name)) {
                        Stations existingStation = stationInfoMap.get(name);

                        if (depth > existingStation.getDepth()) {
                            existingStation.setDepth(depth);
                        }
                    } else {
                        Stations newStation = new Stations(name, number, line,"", depth);
                        stationInfoMap.put(name, newStation);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readCsv(String[] csvFiles) {
        for (String csvFile : csvFiles) {
            try {
                String csvData = new String(Files.readAllBytes(Paths.get(csvFile)));

                String[] csvLines = csvData.split("\n");

                for (String csvLine : csvLines) {
                    String[] csvValues = csvLine.split(",");

                    if (csvValues.length >= 2) {
                        String name = csvValues[0];
                        String dateStr = csvValues[1];

                        if (stationInfoMap.containsKey(name)) {
                            Stations existingStation = stationInfoMap.get(name);
                            if (existingStation.getDate().isEmpty()) {
                                existingStation.setDate(dateStr);
                            }
                            if (!existingStation.getDate().equals(dateStr)) {
                                Stations newStation = new Stations(name, existingStation.getNumber(),
                                        existingStation.getLine(), dateStr, existingStation.getDepth());
                                stationInfoMap.put(name, newStation);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeJson (String[] jsonFiles, String[] csvFiles, Map<Lines,ArrayList<Stations>> stationsList) {
        ObjectNode root = objectMapper.createObjectNode();
        ArrayNode stationsArray = root.putArray("stations");
        readJson(jsonFiles, stationsList);
        readCsv(csvFiles);

        for (Stations stationInfo : stationInfoMap.values()) {
            ObjectNode stationNode = stationsArray.addObject();
            stationNode.put("name", stationInfo.getName());
            stationNode.put("line", stationInfo.getLine());
            stationNode.put("date", stationInfo.getDate());
            stationNode.put("depth", stationInfo.getDepth());
        }

        try {
            System.out.println(root);
            objectMapper.writeValue(new File("stations.json"), root);
            System.out.println("Файл stations.json успешно создан.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

