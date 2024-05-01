package manager;

public class Constants {

    // Other constants used in Manager class
    final static String ADMIN = "admin";
    final static String HOST = "host";
    final static String TECHNICIAN = "technician";

    final static String INPUT_USERNAME = "Input username: ";
    final static String INPUT_PASSWORD = "Input password: ";


    final static String CAR = "car";
    final static String TRUCK = "truck";
    final static String MOTORCYCLE = "motorcycle";
    final static String BUS = "bus";

    final static String FREE_SUBSCRIPTION = "FREE";
    final static String STANDARD_SUBSCRIPTION = "STANDARD";
    final static String PREMIUM_SUBSCRIPTION = "PREMIUM";

    final static String REGISTRATION_NUMBER_REGEX = "[A-Z]\\d\\d\\d\\d[A-Z][A-Z]|[A-Z][A-Z]\\d\\d\\d\\d[A-Z][A-Z]";

    final static String HELP_DESCRIPTION =
            "login - logs in an user" + System.lineSeparator() +
            "logout - logs out the current user" + System.lineSeparator() +
            "welcome client - create a client and adds him to the system" + System.lineSeparator() +
            "send out client - client's vehicle is repaired and removes him from the system" + System.lineSeparator() +
            "create user - creates and adds user to the system" + System.lineSeparator() +
            "remove user - removes user from the system" + System.lineSeparator() +
            "hire mechanic - hires and adds a mechanic to the system" + System.lineSeparator() +
            "fire mechanic - fires and removes a mechanic to the system" + System.lineSeparator() +
            "remove vehicle - removes a vehicle from service's database" + System.lineSeparator() +
            "print vehicles - prints all vehicles of one client" + System.lineSeparator() +
            "print all vehicles - prints all vehicles that have ever been in the service" + System.lineSeparator() +
            "print all clients - prints all clients who are not sent out" + System.lineSeparator() +
            "print all mechanics - prints all mechanics in the service" + System.lineSeparator() +
            "print all users - prints all users in the system" + System.lineSeparator() +
            "print all parts - prints all parts that the service had ever bought" + System.lineSeparator() +
            "exit - close application";

}
