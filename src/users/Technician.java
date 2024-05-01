package users;

public class Technician extends User {

    // Constructor
    public Technician(String name, String password) {
        super(name, password);
    }


    // Overridden methods
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Technician) &&
                (((Technician) obj).getName().equals(this.getName()) &&
                (((Technician) obj).getPassword().equals(this.getPassword())));
    }

    @Override
    public int hashCode() {
        return super.getName().hashCode();
    }

}
