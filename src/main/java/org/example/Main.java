package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File inputDir = new File("input");
    private static final File outputDir = new File("output");

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void main(String[] args) {
        try {
            File[] inputFiles = getInputFiles();
            for (File inputFile : inputFiles) {

                File outputFile = new File(outputDir, inputFile.getName());
                if (validateIfOutputFilesExists(outputFile, inputFile.getName())) {
                    continue;
                }

                List<Analysis> analyses = deserializeAnalysis(inputFile);
                analyses.forEach(analysis ->
                        analysis.setAnalysisCode("unique_" + UUID.randomUUID())
                );


                mapper.writeValue(outputFile, analyses);
                System.out.println("Modified file saved: " + outputFile.getAbsolutePath());

            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    private static List<Analysis> deserializeAnalysis(File inputFile) throws IOException {
        return new ArrayList<>(mapper.readValue(inputFile, new TypeReference<List<Analysis>>() {
        }));
    }

    private static File[] getInputFiles() {
        if (!outputDir.exists() && (!outputDir.mkdirs())) {
            throw new RuntimeException("Failed to create output directory.");
        }
        return inputDir.listFiles();
    }

    private static boolean validateIfOutputFilesExists(File outputFile, String fileName) {
        if (outputFile.exists()) {
            System.out.println(
                    "Skipping file '" + fileName + "'. File already exists in output directory.");
            return true;
        }
        return false;
    }
}