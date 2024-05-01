package vehicle;

public class VehicleFactory {

    // Constants
    private final static String CAR = "car";
    private final static String MOTORCYCLE = "motorcycle";
    private final static String TRUCK = "truck";
    private final static String BUS = "bus";


    // Static factory method
    public static Vehicle crateVehicle(String type, String brand, String registrationNumber) {
        if (type.equalsIgnoreCase(CAR)) {
            return new Car(brand, registrationNumber);
        } else if (type.equalsIgnoreCase(MOTORCYCLE)) {
            return new Motorcycle(brand, registrationNumber);
        } else if (type.equalsIgnoreCase(TRUCK)) {
            return new Truck(brand, registrationNumber);
        } else if (type.equalsIgnoreCase(BUS)) {
            return new Bus(brand, registrationNumber);
        } else {
            throw new InstantiationError(String.format("Cannot be created a vehicle as %s", type));
        }
    }

}