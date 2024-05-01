package users;

public class Admin extends User {

    // Constants
    private final static String NAME = "admin";
    private final static String PASSWORD = "i<3java";


    // Constructor
    public Admin() {
        super(NAME, PASSWORD);
    }


    // Overridden methods
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Admin) && (((Admin) obj).getName().equals(this.getName()) && (((Admin) obj).getPassword().equals(this.getPassword())) );
    }

    @Override
    public int hashCode() {
        return super.getName().hashCode();
    }

}
