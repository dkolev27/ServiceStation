package service;

import client.Client;
import vehicle.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceClients implements ServiceAPI<Client> {

    // Constants
    private final static int CLIENTS_CAPACITY = 40;

    private static final int NAME = 0;
    private static final int SUBSCRIPTION = 1;
    private static final int VEHICLE_BRAND = 2;
    private static final int REGISTRATION_NUMBER = 3;
    private static final int VEHICLE_TYPE = 4;

    private final static String CAR = "car";
    private final static String BUS = "bus";
    private final static String TRUCK = "truck";
    private final static String MOTORCYCLE = "motorcycle";

    private final static String LINE_SPLITTER = "---";

    private static final String FILEPATH = "src/service/clients.txt";


    // Field
    private HashSet<Client> clientsOfService;


    // Constructor
    public ServiceClients() throws IOException, InstantiationException {
        this.setClientsOfService(CLIENTS_CAPACITY);
    }


    // Setter and Getter
    public HashSet<Client> getClientsOfService() {
        return clientsOfService;
    }

    public void setClientsOfService(int capacity) throws IOException, InstantiationException {
        this.clientsOfService = new HashSet<>(capacity);

        File file = new File(FILEPATH);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            String[] line = st.split(LINE_SPLITTER);

            String name = line[NAME];
            String subscription = line[SUBSCRIPTION];
            String vehicleBrand = line[VEHICLE_BRAND];
            String regNumber = line[REGISTRATION_NUMBER];
            String type = line[VEHICLE_TYPE];

            Client client = new Client(name, subscription);
            addNewVehicleToClient(vehicleBrand, regNumber, type, client);

            client.addVehicle(VehicleFactory.crateVehicle(type, vehicleBrand, regNumber));

            this.clientsOfService.add(client);
        }
    }


    // Utils methods
    private void addClientToFile(Client client) throws IOException {
        final int CARS = 5;
        StringBuilder clientCars = new StringBuilder(CARS);

        for (Vehicle vehicle : client.getVehicles()) {
            if (vehicle instanceof Car) {
                clientCars.append(vehicle.getBrand()).append(LINE_SPLITTER).append(vehicle.getRegistrationNumber()).append(LINE_SPLITTER).append(CAR);
            } else if (vehicle instanceof Truck) {
                clientCars.append(vehicle.getBrand()).append(LINE_SPLITTER).append(vehicle.getRegistrationNumber()).append(LINE_SPLITTER).append(TRUCK);
            } else if (vehicle instanceof Motorcycle) {
                clientCars.append(vehicle.getBrand()).append(LINE_SPLITTER).append(vehicle.getRegistrationNumber()).append(LINE_SPLITTER).append(MOTORCYCLE);
            } else if (vehicle instanceof Bus) {
                clientCars.append(vehicle.getBrand()).append(LINE_SPLITTER).append(vehicle.getRegistrationNumber()).append(LINE_SPLITTER).append(BUS);
            } else {
                throw new IllegalArgumentException("Invalid type of vehicle!");
            }
        }

        String clientInfo = client.getName() + LINE_SPLITTER + client.getSubscription() + LINE_SPLITTER + clientCars;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true))) {
            writer.write(clientInfo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasClientInFile(Client client) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            String[] clientProperties = null;
            while ((line = reader.readLine()) != null) {
                clientProperties = line.split(LINE_SPLITTER);

                if (clientProperties[0].equals(client.getName()) && clientProperties[1].equals(client.getSubscription())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void addNewVehicleToClient(String vehicleBrand, String regNumber, String type, Client client) {
        for (Client cl : clientsOfService) {
            if (cl.getName().equals(client.getName()) && cl.getSubscription().equals(client.getSubscription())) {
                cl.addVehicle(VehicleFactory.crateVehicle(type, vehicleBrand, regNumber));
                break;
            }
        }
    }


    // Overrode methods
    @Override
    public void add(Client client) {
        try {
            addClientToFile(client);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clientsOfService.add(client);
    }

    @Override
    public void remove(Client client) {
        if (!hasClientInFile(client)) {
            System.out.println("There is no client with name: " + client.getName());
            return;
        }

        Path path = Paths.get(FILEPATH);
        try {
            List<String> lines = Files.readAllLines(path);
            lines = lines.stream()
                    .filter(l -> !(l.contains(client.getName()) && l.contains(client.getSubscription())))
                    .collect(Collectors.toList());
            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientsOfService.remove(client);
    }

    @Override
    public void print() {
        if (this.clientsOfService.isEmpty()) {
            System.out.println("There aren't any clients be to printed out!");
            return;
        }

        for (Client client : this.clientsOfService) {
            final int CARS_NUMBER_AT_FIRST = 5;
            StringBuilder clientCars = new StringBuilder(CARS_NUMBER_AT_FIRST);

            int size = client.getVehicles().size();
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    clientCars.append(client.getVehicles().get(i).getBrand()).append('.');
                    break;
                }
                clientCars.append(client.getVehicles().get(i).getBrand()).append(", ");
            }

            System.out.println(client.getName() + " has " + clientCars);
        }
    }

}