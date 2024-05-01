package users;

public class Host extends User {

    // Constructor
    public Host(String name, String password) {
        super(name, password);
    }


    // Overridden methods
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Host) && (((Host) obj).getName().equals(this.getName()) && (((Host) obj).getPassword().equals(this.getPassword())) );
    }

    @Override
    public int hashCode() {
        return super.getName().hashCode();
    }

}
