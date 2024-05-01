package vehicle;

public class Bus extends Vehicle {

    // Constructor
    public Bus(String brand, String registrationNumber) {
        super(brand, registrationNumber);
    }


    // Overridden methods
    @Override
    public void printVehicle() {
        System.out.printf("""
                Brand: %s
                Type: bus
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

        System.out.print("Bought bus parts: ");
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
        return (obj instanceof Bus) && (((Bus) obj).getRegistrationNumber().equals(this.getRegistrationNumber()));
    }

    @Override
    public int hashCode() {
        return getRegistrationNumber().hashCode();
    }

}
