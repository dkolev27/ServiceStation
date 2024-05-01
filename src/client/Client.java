package client;

import vehicle.Vehicle;

import java.util.LinkedList;
import java.util.List;

public class Client {

    // Constants
    private final static String FREE_SUBSCRIPTION = "FREE";
    private final static String STANDARD_SUBSCRIPTION = "STANDARD";
    private final static String PREMIUM_SUBSCRIPTION = "PREMIUM";

    //Fields
    private String name;
    private final List<Vehicle> vehicles;
    private String subscription;


    // Constructor
    public Client(String name, String subscription) throws InstantiationException {
        this.setName(name);
        this.setSubscription(subscription);
        this.vehicles = new LinkedList<>();
    }


    // Setters and Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) throws InstantiationException {
        if (subscription.equals(FREE_SUBSCRIPTION) ||
            subscription.equals(STANDARD_SUBSCRIPTION) ||
            subscription.equals(PREMIUM_SUBSCRIPTION)) {
            this.subscription = subscription;
        } else {
            throw new InstantiationException("Invalid subscription!");
        }
    }

    // Methods
    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void sellVehicle(String registrationNumber) {
        this.vehicles.removeIf(vehicle -> vehicle.getRegistrationNumber().equals(registrationNumber));
    }

    public void printAllCars() {
        int size = vehicles.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                System.out.println(vehicles.get(i).getBrand());
            } else {
                System.out.print(vehicles.get(i).getBrand() + ", ");
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Client) &&
                (((Client) obj).getName().equals(this.getName()) &&
                        (((Client) obj).getSubscription().equals(this.getSubscription())));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
