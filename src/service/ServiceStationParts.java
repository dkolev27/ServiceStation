package service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ServiceStationParts {

    // Constants
    private final static String FILEPATH = "src/service/bought_car_parts.txt";


    //Field
    HashMap<String, Integer> boughtPartsByService;


    // Constructor
    final static int CAPACITY = 50;
    public ServiceStationParts() throws IOException {
        this.setBoughtPartsByService(CAPACITY);
    }


    // Setter and Getter
    public HashMap<String, Integer> getBoughtPartsByService() {
        return boughtPartsByService;
    }

    public void setBoughtPartsByService(int startingPartsNumber) throws IOException {
        this.boughtPartsByService = new HashMap<>(startingPartsNumber);

        File file = new File(FILEPATH);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            addPartToMap(st);
        }
    }


    // Utils
    public void addParts(String... parts) throws IOException {
        for (String part : parts) {
            addPartToMap(part);
            addToFile(part);
        }
    }

    public void addPartToMap(String part) {
        if (!this.boughtPartsByService.containsKey(part)) {
            int cnt = 1;
            this.boughtPartsByService.put(part, cnt);
        } else {
            int getCnt = this.boughtPartsByService.get(part);
            this.boughtPartsByService.replace(part, getCnt, ++getCnt);
        }
    }

    public void addToFile(String part) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true))) {
            writer.write(part);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAllParts() {
        for (Map.Entry<String, Integer> entry : this.boughtPartsByService.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " " + value);
        }
    }

}
