package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            /**
             *  Read JSON file and convert to list of MyObject
             *  the file with initial data need to be placed in the root of the project
             */
            List<Analisis> myObjects = mapper.readValue(new File("1706865111142(Updated).dat"), new TypeReference<List<Analisis>>(){});
            // Modify the objects
            for (Analisis obj : myObjects) {
                    obj.setAnalysisCode("unique_" + UUID.randomUUID());
            }
            // Write the modified objects back to a JSON file
            mapper.writeValue(new File("output.json"), myObjects);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}