package users;

public abstract class User {

    // Field
    private String name;
    private String password;


    // Constructor
    public User(String name, String password) {
        this.setName(name);
        setPassword(password);
    }


    // Setter and Getter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Overridden methods
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof User) &&
                (((User) obj).getName().equals(this.getName()) &&
                (((User) obj).getPassword().equals(this.getPassword())));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
