package mechanic;

import vehicle.Vehicle;

import java.util.Objects;

public class Mechanic {

    //Fields
    private String name;
    private Vehicle vehicleToRepair;
    private int repairedVehicles;


    // Constructor
    public Mechanic(String name) {
        this.setName(name);
        this.setVehicleToRepair(null);
        this.setRepairedVehicles(0);
    }


    // Setters and Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vehicle getVehicleToRepair() {
        return vehicleToRepair;
    }

    public void setVehicleToRepair(Vehicle vehicleToRepair) {
        this.vehicleToRepair = vehicleToRepair;
    }

    public int getRepairedVehicles() {
        return repairedVehicles;
    }

    public void setRepairedVehicles(int repairedVehicles) {
        this.repairedVehicles = repairedVehicles;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mechanic mechanic = (Mechanic) obj;
        return Objects.equals(name, mechanic.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}