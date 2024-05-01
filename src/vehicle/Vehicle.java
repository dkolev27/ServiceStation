package vehicle;

import java.util.LinkedList;
import java.util.List;

public abstract class Vehicle {

    // Fields
    private String brand;
    private String registrationNumber;
    private final List<String> repairs;
    private final List<String> boughtParts;


    // Constructor
    public Vehicle(String brand, String registrationNumber) {
        this.setBrand(brand);
        this.setRegistrationNumber(registrationNumber);
        repairs = new LinkedList<>();
        boughtParts = new LinkedList<>();
    }


    // Setters and Getters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<String> getRepairs() {
        return repairs;
    }

    public List<String> getBoughtParts() {
        return boughtParts;
    }


    // Methods
    public void repair(String repair) {
        this.repairs.add(repair);
    }

    public void buyCarParts(String part) {
        this.boughtParts.add(part);
    }

    public abstract void printVehicle();

}
