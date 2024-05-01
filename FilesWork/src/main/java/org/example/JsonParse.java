package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParse {
    public JsonNode readJson(String path) throws IOException {
        String jsonFile = Files.readString(Paths.get(path));

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonFile);
    }
}
