package users;

public class UserFactory {

    // Constants
    private final static String ADMIN_TYPE = "admin";
    private final static String TECHNICIAN_TYPE = "technician";
    private final static String HOST_TYPE = "host";


    // Static factory method
    public static User createUser(String type, String name, String password) {
        if (type.equalsIgnoreCase(ADMIN_TYPE)) {
            return new Admin();
        } else if (type.equalsIgnoreCase(TECHNICIAN_TYPE)) {
            return new Technician(name, password);
        } else if (type.equalsIgnoreCase(HOST_TYPE)) {
            return new Host(name, password);
        } else {
            throw new InstantiationError(String.format("Cannot be created an user as %s", type));
        }
    }

}