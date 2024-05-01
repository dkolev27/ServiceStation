package vehicle;

public class Truck extends Vehicle {

    // Constructor
    public Truck(String brand, String registrationNumber) {
        super(brand, registrationNumber);
    }


    // Overridden methods
    @Override
    public void printVehicle() {
        System.out.printf("""
                Brand: %s
                Type: truck
                Registration number: %s
                """, super.getBrand(), super.getRegistrationNumber());

        System.out.print("Repairs: ");
        int repSize = super.getRepairs().size();
        for (int i = 0; i < repSize; i++) {
            if (i != repSize - 1) {
                System.out.print(super.getRepairs().get(i) + ", ");
            } else {
                System.out.println(super.getRepairs().get(i));
            }
        }

        System.out.print("Bought truck parts: ");
        int partsSize = super.getBoughtParts().size();
        for (int i = 0; i < partsSize; i++) {
            if (i != partsSize - 1) {
                System.out.print(super.getBoughtParts().get(i) + ", ");
            } else {
                System.out.println(super.getBoughtParts().get(i));
            }
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Truck) && (((Truck) obj).getRegistrationNumber().equals(this.getRegistrationNumber()));
    }

    @Override
    public int hashCode() {
        return getRegistrationNumber().hashCode();
    }

}
