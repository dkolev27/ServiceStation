package users;

import manager.TextColor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UsersCollection {

    // Constants
    private final static String FILEPATH = "src/users/users.txt";

    private final static String ADMIN = "admin";
    private final static String TECHNICIAN = "technician";
    private final static String HOST = "host";

    private final static int USER_TYPE = 0;
    private final static int USER_NAME = 1;


    // Field
    HashSet<User> users;


    // Constructor
    final static int STARTING_CAPACITY = 20;

    public UsersCollection() throws IOException {
        setUsers();
    }


    // Getter and Setter
    public HashSet<User> getUsers() {
        return users;
    }

    public void setUsers() throws IOException {
        this.users = new HashSet<>(STARTING_CAPACITY);

        File file = new File(FILEPATH);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            String[] line = st.split(" ");
            String type = line[0];
            String name = line[1];
            String password = line[2];

            User user = UserFactory.createUser(type, name, password);
            this.users.add(user);
        }
    }


    // Methods
    public void addUsers(User user) {
        if (hasUserInFile(user) && !getUserType(user).equalsIgnoreCase(ADMIN)) {
            System.out.println("This user is already added!");
            return;
        }

        addUsersToFile(user);
        users.add(user);
    }

    private void addUsersToFile(User user) {
        if (hasUserInFile(user)) {
            System.out.printf("User with name %s is already in the service!\n", user.getName());
            return;
        }

        String userType = getUserType(user);
        String line = userType + ' ' + user.getName() + ' ' + user.getPassword();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasUserInFile(User user) {
        String userType = getUserType(user);

        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            String[] userProps;
            while ((line = reader.readLine()) != null) {
                userProps = line.split(" ");
                if (userProps[USER_TYPE].equalsIgnoreCase(userType) &&
                    userProps[USER_NAME].equalsIgnoreCase(user.getName())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String getUserType(User user) {
        String userType = null;

        if (user instanceof Admin) {
            userType = ADMIN;
        } else if (user instanceof Host) {
            userType = HOST;
        } else if (user instanceof Technician) {
            userType = TECHNICIAN;
        }
        return userType;
    }

    public void removeUser(User user) {
        if (!hasUserInFile(user)) {
            System.out.println(TextColor.ANSI_RED + "There is no user with name: " + TextColor.ANSI_RESET + user.getName());
            return;
        }

        Path path = Paths.get(FILEPATH);
        try {
            List<String> lines = Files.readAllLines(path);
            lines = lines.stream()
                    .filter(l -> !(l.contains(getUserType(user)) && l.contains(user.getName())))
                    .collect(Collectors.toList());
            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

         users.remove(user);
        System.out.println(TextColor.ANSI_PURPLE + "User's removed!" + TextColor.ANSI_RESET);
    }

    public void printUsers() {
        for (User user : this.getUsers()) {
            System.out.println(getUserType(user) + ' ' + user.getName());
        }
    }

}