package service;

import manager.TextColor;
import vehicle.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceVehicles implements ServiceAPI<Vehicle> {

    // Constants
    private final static int VEHICLES_CAPACITY = 20;

    private final static int BRAND = 0;
    private final static int REGISTRATION_NUMBER = 1;
    private final static int VEHICLE_TYPE = 2;

    private final static String CAR = "car";
    private final static String BUS = "bus";
    private final static String TRUCK = "truck";
    private final static String MOTORCYCLE = "motorcycle";

    private final static String LINE_SPLITTER = "---";

    private final static String FILEPATH = "src/service/vehicles.txt";


    // Fields
    private HashSet<Vehicle> vehiclesInService;


    // Constructor
    public ServiceVehicles() throws IOException {
        this.setVehiclesInService(VEHICLES_CAPACITY);
    }


    // Setter and Getter
    public HashSet<Vehicle> getVehiclesInService() {
        return vehiclesInService;
    }

    public void setVehiclesInService(int capacity) throws IOException {
        this.vehiclesInService = new HashSet<>(capacity);

        File file = new File(FILEPATH);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            String[] line = st.split(LINE_SPLITTER);

            String brand = line[BRAND];
            String regNumber = line[REGISTRATION_NUMBER];
            String type;
            try {
                type = line[VEHICLE_TYPE];
            } catch (IllegalArgumentException e) {
                System.out.println("This vehicle won't be added!");
                continue;
            }

            Vehicle vehicle = VehicleFactory.crateVehicle(type, brand, regNumber);
            this.vehiclesInService.add(vehicle);
        }
    }


    // Util methods
    private void addVehicleToFile(Vehicle vehicle, String type) throws IOException {
        String brand = vehicle.getBrand();
        String regNumber = vehicle.getRegistrationNumber();

        if (hasVehicleInFile(regNumber)) {
            System.out.printf("Vehicle with registration number %s is already in the service!" + System.lineSeparator(),
                                regNumber);
            return;
        }

        final int CAPACITY = 3;
        StringBuilder line = new StringBuilder(CAPACITY);
        line.append(brand).append(LINE_SPLITTER).append(regNumber).append(LINE_SPLITTER).append(type);
        String lineToAdd = line.toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true))) {
            writer.write(lineToAdd);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasVehicleInFile(String regNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            String[] carProperties = null;
            while ((line = reader.readLine()) != null) {
                carProperties = line.split(LINE_SPLITTER);

                if (carProperties[REGISTRATION_NUMBER].equals(regNumber)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    // Overridden methods
    @Override
    public void add(Vehicle vehicle) {
        if (hasVehicleInFile(vehicle.getRegistrationNumber())) {
            return;
        }

        try {
            if (vehicle instanceof Car) {
                addVehicleToFile(vehicle, CAR);
            } else if (vehicle instanceof Truck) {
                addVehicleToFile(vehicle, TRUCK);
            } else if (vehicle instanceof Bus) {
                addVehicleToFile(vehicle, BUS);
            } else if (vehicle instanceof Motorcycle) {
                addVehicleToFile(vehicle, MOTORCYCLE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        vehiclesInService.add(vehicle);
    }

    @Override
    public void remove(Vehicle vehicle) {
        if (!hasVehicleInFile(vehicle.getRegistrationNumber())) {
            System.out.println(TextColor.ANSI_RED +
                    "No such vehicle in the database!" +
                    TextColor.ANSI_RESET);
            return;
        }

        Path path = Paths.get(FILEPATH);
        try {
            List<String> lines = Files.readAllLines(path);
            lines = lines.stream()
                    .filter(l -> !l.contains(vehicle.getRegistrationNumber()))
                    .collect(Collectors.toList());
            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        vehiclesInService.remove(vehicle);
        System.out.println(TextColor.ANSI_BLUE +
                "Vehicle successfully removed!" + System.lineSeparator()
                + TextColor.ANSI_RESET);
    }

    @Override
    public void print() {
        int cnt = 1;
        for (Vehicle vehicle : this.vehiclesInService) {
            System.out.println(cnt++ + ". " + vehicle.getBrand() + " - " + vehicle.getRegistrationNumber());
        }
    }

}
