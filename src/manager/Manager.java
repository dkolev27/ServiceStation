package manager;

import client.Client;
import mechanic.Mechanic;
import service.*;
import users.*;
import vehicle.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import static manager.CommandsLiterals.*;
import static manager.Constants.*;
import static manager.TextColor.*;

public class Manager implements Manageable {

    // Overridden methods
    @Override
    public void manage(Scanner scanner) throws IOException, InstantiationException {
        ServiceClients clientsInService = new ServiceClients();
        ServiceMechanics mechanicsInService = new ServiceMechanics();
        ServiceVehicles vehiclesInService = new ServiceVehicles();

        ServiceStationParts partsInService = new ServiceStationParts();
        UsersCollection usersCollection = new UsersCollection();

        User loggedUser = null;
        boolean isAdminLoggedIn = false;
        boolean isHostLoggedIn = false;
        boolean isTechnicianLoggedIn = false;

        String input;

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase(LOGIN)) {

                if (isSomeoneLoggedIn(isAdminLoggedIn, isHostLoggedIn, isTechnicianLoggedIn)) {
                    System.out.println(ANSI_YELLOW +
                            "Someone is logged in! Wait him to log out and try again!"
                            + ANSI_RESET);
                    continue;
                }

                String userType = getUserType(scanner);

                System.out.print(INPUT_USERNAME);
                String username = scanner.nextLine();
                System.out.print(INPUT_PASSWORD);
                String password = scanner.nextLine();

                while (true) {
                    loggedUser = UserFactory.createUser(userType, username, password);
                    if (usersCollection.getUsers().contains(loggedUser)) {
                        break;
                    }

                    System.out.println(ANSI_RED +
                            "There is no such user! Press ENTER."
                            + ANSI_RESET);

                    userType = getValidType(scanner);
                    System.out.print(INPUT_USERNAME);
                    username = scanner.nextLine();
                    System.out.print(INPUT_PASSWORD);
                    password = scanner.nextLine();
                }

                if (userType.equals(ADMIN) && username.equals(ADMIN) && password.equals(ADMIN_PASSWORD)) {
                    isAdminLoggedIn = true;

                    System.out.println(ANSI_BLUE +
                            "Welcome admin!"
                            + ANSI_RESET);
                } else if (userType.equals(HOST)) {
                    isHostLoggedIn = true;

                    System.out.printf(ANSI_BLUE +
                            "Welcome host %s!" + System.lineSeparator()
                            + ANSI_RESET, username);
                } else if (userType.equals(TECHNICIAN)) {
                    isTechnicianLoggedIn = true;

                    System.out.printf(ANSI_BLUE +
                            "Welcome technician %s!" + System.lineSeparator()
                            + ANSI_RESET, username);
                } else {
                    System.out.println(ANSI_RED +
                            "Wrong user type or username or password! Try again!"
                            + ANSI_RESET);
                }

            } else if (input.equalsIgnoreCase(LOGOUT)) {

                if (isSomeoneLoggedIn(isAdminLoggedIn, isHostLoggedIn, isTechnicianLoggedIn)) {
                    System.out.printf(ANSI_BLUE +
                            "Goodbye %s!" + System.lineSeparator()
                            + ANSI_RESET, loggedUser.getName());

                    if (loggedUser instanceof Admin) {
                        isAdminLoggedIn = false;
                    } else if (loggedUser instanceof Host) {
                        isHostLoggedIn = false;
                    } else if (loggedUser instanceof Technician) {
                        isTechnicianLoggedIn = false;
                    }
                } else {
                    System.out.println(ANSI_RED +
                            "No one is logged in!"
                            + ANSI_RESET);
                }

            } else if (input.equalsIgnoreCase(WELCOME_CLIENT)) {

                welcomeClient(scanner, clientsInService, mechanicsInService, vehiclesInService, isHostLoggedIn, partsInService);

            } else if (input.equalsIgnoreCase(SEND_OUT_CLIENT)) {

                // Client's vehicle is repaired, and he is ready to be sent out
                sendOutClient(scanner, clientsInService, vehiclesInService, isHostLoggedIn);

            } else if (input.equalsIgnoreCase(REMOVE_VEHICLE)) {

                removeVehicle(scanner, vehiclesInService, isAdminLoggedIn);

            } else if (input.equalsIgnoreCase(PRINT_ALL_VEHICLES)) {

                printAllVehicles(vehiclesInService, loggedUser);

            } else if (input.equalsIgnoreCase(PRINT_VEHICLES)) {

                printVehiclesOf(scanner, clientsInService, isAdminLoggedIn);

            } else if (input.equalsIgnoreCase(PRINT_ALL_CLIENTS)) {

                printAllClients(clientsInService, isAdminLoggedIn);

            } else if (input.equalsIgnoreCase(CREATE_USER)) {

                createUser(scanner, usersCollection, isAdminLoggedIn);

            } else if (input.equalsIgnoreCase(HIRE_MECHANIC)) {

                hireMechanic(scanner, mechanicsInService, isTechnicianLoggedIn);

            } else if (input.equalsIgnoreCase(FIRE_MECHANIC)) {

                fireMechanic(scanner, mechanicsInService, isTechnicianLoggedIn);

            } else if (input.equalsIgnoreCase(PRINT_ALL_MECHANICS)) {

                printAllMechanics(mechanicsInService);

            } else if (input.equalsIgnoreCase(REMOVE_USER)) {

                removeUser(scanner, usersCollection, isAdminLoggedIn);

            } else if (input.equalsIgnoreCase(PRINT_ALL_USERS)) {

                printAllUsers(usersCollection, isAdminLoggedIn, isHostLoggedIn, isTechnicianLoggedIn);

            } else if (input.equalsIgnoreCase(PRINT_ALL_PARTS)) {

                printAllParts(partsInService, isAdminLoggedIn);

            } else if (input.equalsIgnoreCase(HELP)) {

                helpUser();

            } else if (input.equalsIgnoreCase(EXIT)) {

                System.out.println(ANSI_BLUE +
                        "Goodbye!"
                        + ANSI_RESET);
                return;

            } else {

                System.out.println(ANSI_RED +
                        "Invalid operation! Try again!"
                        + ANSI_RESET);

            }
        }
    }

    @Override
    public void printAllParts(ServiceStationParts partsInService, boolean isAdminLoggedIn) {
        if (isAdminLoggedIn) {
            partsInService.printAllParts();
        } else {
            System.out.println(ANSI_YELLOW +
                    "Only admin can view all the parts that the service had ever bought."
                    + ANSI_RESET);
        }
    }

    @Override
    public void removeVehicle(Scanner scanner, ServiceVehicles vehiclesInService, boolean isAdminLoggedIn) {
        if (isAdminLoggedIn) {
            System.out.print("Input type of the vehicle: ");
            String type = getValidVehicleType(scanner);
            String regNumber = inputRegNumber(scanner);

            Vehicle vehicle = VehicleFactory.crateVehicle(type, null, regNumber);

            if (!containsVehicle(vehiclesInService, vehicle)) {
                System.out.println(ANSI_RED +
                        "There is no such vehicle!"
                        + ANSI_RESET);
                return;
            }

            vehiclesInService.remove(vehicle);
        } else {
            System.out.println(ANSI_YELLOW +
                    "Only admin can remove cars from service's database!"
                    + ANSI_RESET);
        }
    }

    @Override
    public void printAllVehicles(ServiceVehicles vehiclesInService, User loggedUser) {
        if (loggedUser == null) {
            System.out.println(ANSI_YELLOW +
                    "Someone must be logged in!"
                    + ANSI_RESET);
            return;
        }
        vehiclesInService.print();
    }

    @Override
    public void helpUser() {
        System.out.println(ANSI_CYAN +
                HELP_DESCRIPTION
                + ANSI_RESET);
    }

    @Override
    public void printAllClients(ServiceClients clientsInService, boolean isAdminLoggedIn) {
        if (!isAdminLoggedIn) {
            System.out.println(ANSI_YELLOW +
                    "Only admin can see the clients in the service!"
                    + ANSI_RESET);
            return;
        }

        clientsInService.print();
    }

    @Override
    public void printAllMechanics(ServiceMechanics mechanicsInService) {
        mechanicsInService.print();
    }

    @Override
    public void printVehiclesOf(Scanner scanner, ServiceClients clientsInService, boolean isAdminLoggedIn) {
        if (!isAdminLoggedIn) {
            System.out.println(ANSI_YELLOW +
                    "Only admin can see all the vehicles!"
                    + ANSI_RESET);
            return;
        }

        System.out.print("Input the name of vehicle's owner: ");
        String clientName = scanner.nextLine();
        System.out.print("Input his subscription: ");
        String subscription = getValidSubscription(scanner);
        for (Client client : clientsInService.getClientsOfService()) {
            if (client.getName().equals(clientName) && client.getSubscription().equals(subscription)) {
                System.out.printf(ANSI_BLUE +
                        "%s has vehicles:" + System.lineSeparator()
                        + ANSI_RESET, clientName);

                client.printAllCars();
                return;
            }
        }
        System.out.println(ANSI_RED + "There is no such client!" + ANSI_RESET);
    }

    @Override
    public String getUserType(Scanner scanner) {
        System.out.print("Input user type: ");
        String userType = scanner.nextLine();
        while (true) {
            if (userType.equals(ADMIN) || userType.equals(HOST) || userType.equals(TECHNICIAN)) {
                break;
            }

            System.out.println(ANSI_RED +
                    "Invalid user type. Try again!"
                    + ANSI_RESET);

            System.out.print("Input user type: ");
            userType = scanner.nextLine();
        }
        return userType;
    }

    @Override
    public void printAllUsers(UsersCollection usersCollection,
                              boolean isAdminLoggedIn, boolean isHostLoggedIn, boolean isTechnicianLoggedIn) {
        if (isSomeoneLoggedIn(isAdminLoggedIn, isHostLoggedIn, isTechnicianLoggedIn)) {
            usersCollection.printUsers();
        } else {
            System.out.println(ANSI_YELLOW +
                    "You have to be logged in!"
                    + ANSI_RESET);
        }
    }

    @Override
    public void removeUser(Scanner scanner, UsersCollection usersCollection, boolean isAdminLoggedIn) {
        if (isAdminLoggedIn) {
            System.out.print("Input user type: ");
            String type = getValidType(scanner);

            System.out.print(INPUT_USERNAME);
            String name = scanner.nextLine();

            System.out.print(INPUT_PASSWORD);
            String password = scanner.nextLine();

            User user = UserFactory.createUser(type, name, password);
            usersCollection.removeUser(user);
        } else {
            System.out.println(ANSI_YELLOW +
                    "Only admin can remove users!"
                    + ANSI_RESET);
        }
    }

    @Override
    public void createUser(Scanner scanner, UsersCollection usersCollection, boolean isAdminLoggedIn) throws IOException {
        if (isAdminLoggedIn) {
            System.out.print("Input user type: ");
            String userType = getValidType(scanner);

            System.out.print(INPUT_USERNAME);
            String username = scanner.nextLine();

            System.out.print(INPUT_PASSWORD);
            String password = scanner.nextLine();

            User user = UserFactory.createUser(userType, username, password);
            System.out.println(ANSI_BLUE +
                    "User successfully created!"
                    + ANSI_RESET);

            usersCollection.addUsers(user);
        } else {
            System.out.println(ANSI_YELLOW +
                    "Only admin can create users!"
                    + ANSI_RESET);
        }
    }

    @Override
    public void fireMechanic(Scanner scanner, ServiceMechanics mechanicsInService, boolean isTechnicianLoggedIn) {
        if (isTechnicianLoggedIn) {
            System.out.print("Input mechanic name: ");
            String name = scanner.nextLine();

            Mechanic mechanic = new Mechanic(name);
            mechanicsInService.remove(mechanic);
        } else {
            System.out.println(ANSI_YELLOW +
                    "Only a technician can fire a mechanic!"
                    + ANSI_RESET);
        }
    }

    @Override
    public void hireMechanic(Scanner scanner, ServiceMechanics mechanicsInService, boolean isTechnicianLoggedIn) {
        if (isTechnicianLoggedIn) {
            System.out.print("Input mechanic name: ");
            String name = scanner.nextLine();

            Mechanic mechanic = new Mechanic(name);
            mechanicsInService.add(mechanic);

            System.out.printf(ANSI_CYAN +
                    "Mechanic %s successfully hired!" + System.lineSeparator()
                    + ANSI_RESET, name);
        } else {
            System.out.println(ANSI_YELLOW +
                    "Only a technician can hire a mechanic!"
                    + ANSI_RESET);
        }
    }

    @Override
    public void sendOutClient(Scanner scanner, ServiceClients clientsInService, ServiceVehicles vehiclesInService,
                              boolean isHostLoggedIn)
            throws InstantiationException {

        if (!isHostLoggedIn) {
            System.out.println(ANSI_YELLOW +
                    "Host must be logged in!"
                    + ANSI_RESET);
            return;
        }

        System.out.print("Input client's name: ");
        String clientName = scanner.nextLine();

        System.out.print("Input client's subscription: ");
        String clientSubscription = scanner.nextLine();

        Client client = null;
        try {
            client = new Client(clientName, clientSubscription);
        } catch (InstantiationException e) {
            System.out.println(ANSI_RED +
                    "Invalid subscription! Try again!"
                    + ANSI_RESET);

            sendOutClient(scanner, clientsInService, vehiclesInService, isHostLoggedIn);
        }

        if (!hasClient(client, clientsInService)) {
            System.out.println(ANSI_RED +
                    "There is no such client!"
                    + ANSI_RESET);
            return;
        }

        clientsInService.remove(client);
        System.out.println(ANSI_BLUE +
                clientName + ", your vehicle/s is/are repaired. Have a nice day!"
                + ANSI_RESET);
    }

    @Override
    public void welcomeClient(Scanner scanner,
                              ServiceClients clientsInService, ServiceMechanics mechanicsInService, ServiceVehicles vehiclesInService,
                              boolean isHostLoggedIn, ServiceStationParts partsInService) throws InstantiationException, IOException {
        if (isHostLoggedIn) {
            Vehicle vehicleToRepair = organizeVehicleToStepIntoService(scanner, clientsInService, vehiclesInService, partsInService);
            chooseMechanic(scanner, mechanicsInService, vehicleToRepair);
        } else {
            System.out.println(ANSI_YELLOW +
                    "Only a host can welcome clients!"
                    + ANSI_RESET);
        }
    }


    // Helper methods
    private Vehicle organizeVehicleToStepIntoService(Scanner scanner, ServiceClients clientsInService,
                                                     ServiceVehicles vehiclesInService, ServiceStationParts partsInService)
                                                     throws InstantiationException, IOException {
        Client client = createClient(scanner);

        // Create the car that will step into the service
        System.out.print("Tell me what type of vehicle you have: ");
        String typeOfVehicle = getValidVehicleType(scanner);

        System.out.print("Tell me its brand: ");
        String brand = scanner.nextLine();

        String regNumber = inputRegNumber(scanner);

        System.out.print("Tell me which parts needs to be changed: ");
        String[] parts = scanner.nextLine().split(", ");

        Vehicle vehicle = VehicleFactory.crateVehicle(typeOfVehicle, brand, regNumber);

        addClientToDataListOfTheService(clientsInService, client, vehicle);

        addVehicleToClient(clientsInService, client, vehicle);

        addPartsInService(parts, vehicle, partsInService);

        vehiclesInService.add(vehicle);
        clientsInService.add(client);

        // Assign subscription fee of the client
        subscriptionFee(client);

        return vehicle;
    }

    private static void addClientToDataListOfTheService(ServiceClients clientsInService, Client client, Vehicle vehicle) {
        if (clientsInService.hasClientInFile(client)) {
            System.out.printf(ANSI_YELLOW +
                    "Client with name %s is already signed in service, but we will add his new vehicle!" + System.lineSeparator()
                    + ANSI_RESET, client.getName());
        }

        client.addVehicle(vehicle);
    }

    private Client createClient(Scanner scanner) throws InstantiationException {
        System.out.print("Tell me your name: ");
        String name = scanner.nextLine();

        System.out.print("What is your subscription: ");
        String subscription = getValidSubscription(scanner);

        Client client = new Client(name, subscription);
        return client;
    }

    private boolean hasClient(Client client, ServiceClients clientsInService) {
        String clientName = client.getName();
        String clientSubscription = client.getSubscription();

        for (Client temp : clientsInService.getClientsOfService()) {
            if (temp.getName().equals(clientName) && temp.getSubscription().equals(clientSubscription)) {
                return true;
            }
        }
        return false;
    }

    private String getValidSubscription(Scanner scanner) {
        String subscription = scanner.nextLine();

        while (true) {
            assert subscription != null;
            if (subscription.equals(FREE_SUBSCRIPTION)     ||
                subscription.equals(STANDARD_SUBSCRIPTION) ||
                subscription.equals(PREMIUM_SUBSCRIPTION)) {
                break;
            } else {
                System.out.println(ANSI_RED +
                        "Invalid subscription!"
                        + ANSI_RESET);

                System.out.print("Input valid subscription: ");
                subscription = scanner.nextLine();
            }
        }

        return subscription;
    }

    private static void addVehicleToClient(ServiceClients clientsInService, Client client, Vehicle vehicle) {
        for (Client cl : clientsInService.getClientsOfService()) {
            if (cl.getName().equals(client.getName()) && cl.getSubscription().equals(client.getSubscription())) {
                cl.getVehicles().add(vehicle);
                break;
            }
        }
    }

    private static void addPartsInService(String[] parts, Vehicle vehicle, ServiceStationParts partsInService) throws IOException {
        for (String part : parts) {
            vehicle.buyCarParts(part);

            partsInService.addParts(part);
        }
    }

    private static void subscriptionFee(Client client) {
        if (client.getSubscription().equals(FREE_SUBSCRIPTION)) {

            System.out.println(ANSI_PURPLE +
                    "You must tip the mechanic at least 100 lev for his works."
                    + ANSI_RESET);

        } else if (client.getSubscription().equals(STANDARD_SUBSCRIPTION)) {

            System.out.println(ANSI_CYAN +
                    "You should tip the mechanic at least 20 lev for his works."
                    + ANSI_RESET);

        } else if (client.getSubscription().equalsIgnoreCase(PREMIUM_SUBSCRIPTION)) {

            System.out.println(ANSI_GREEN +
                    "You don't need to tip the mechanic for his works."
                    + ANSI_RESET);

        }
    }

    private static void chooseMechanic(Scanner scanner, ServiceMechanics mechanicsInService, Vehicle vehicle) {
        System.out.print("Which mechanic you want to repair your car: ");
        String mechanicName = scanner.nextLine();

        while (true) {
            if (!mechanicsInService.getMechanicsInService().contains(new Mechanic(mechanicName))) {
                System.out.println(ANSI_RED +
                        "No mechanic with this name is working here!"
                        + ANSI_RESET);

                System.out.print("Choose different mechanic: ");
                mechanicName = scanner.nextLine();
            } else {
                break;
            }
        }

        System.out.printf(ANSI_BLUE +
                mechanicName + " starts working on your %s." + System.lineSeparator()
                + ANSI_RESET, vehicle.getBrand());
    }

    private String inputRegNumber(Scanner scanner) {
        System.out.print("Tell me its registration number: ");

        while (!scanner.hasNext(REGISTRATION_NUMBER_REGEX)) {
            System.out.println(ANSI_RED +
                    "Invalid registration number!"
                    + ANSI_RESET);

            System.out.print("Tell me its registration number: ");
            scanner.nextLine();
        }

        return scanner.nextLine();
    }

    private static boolean isSomeoneLoggedIn(boolean isAdminLoggedIn, boolean isHostLoggedIn, boolean isTechnicianLoggedIn) {
        return isAdminLoggedIn || isHostLoggedIn || isTechnicianLoggedIn;
    }

    private boolean isUserValid(String type) {
        if (type.equalsIgnoreCase(ADMIN)) {
            System.out.println(ANSI_YELLOW +
                    "Admin is only one!!!"
                    + ANSI_RESET);
            return false;
        } else {
            return type.equalsIgnoreCase(HOST) || type.equalsIgnoreCase(TECHNICIAN);
        }
    }

    private String getValidType(Scanner scanner) {
        String userType = scanner.nextLine();
        while (true) {
            if (isUserValid(userType)) {
                break;
            }
            System.out.print("Input user type: ");
            userType = scanner.nextLine();
        }
        return userType;
    }

    private boolean isValidVehicleType(String type) {
        return  type.equals(CAR)    ||
                type.equals(BUS)    ||
                type.equals(TRUCK)  ||
                type.equals(MOTORCYCLE);
    }

    private String getValidVehicleType(Scanner scanner) {
        String vehicleType = scanner.nextLine();
        while (true) {
            if (isValidVehicleType(vehicleType)) {
                break;
            }

            System.out.print(ANSI_RED +
                    "Invalid vehicle type!" + System.lineSeparator() +
                    ANSI_RESET);
            System.out.print("Input vehicle type: ");

            vehicleType = scanner.nextLine();
        }
        return vehicleType;
    }

    private boolean containsVehicle(ServiceVehicles vehiclesInService, Vehicle vehicle) {
        Vehicle currentVehicle = null;
        Iterator<Vehicle> iterator = vehiclesInService.getVehiclesInService().iterator();

        while (iterator.hasNext()) {
            currentVehicle = iterator.next();
            if (vehicle instanceof Car && currentVehicle instanceof Car &&
                   (currentVehicle.getRegistrationNumber().equals(vehicle.getRegistrationNumber()))) {
                return true;
            } else if (vehicle instanceof Truck && currentVehicle instanceof Truck &&
                    (currentVehicle.getRegistrationNumber().equals(vehicle.getRegistrationNumber()))) {
                return true;
            } else if (vehicle instanceof Bus && currentVehicle instanceof Bus &&
                    (currentVehicle.getRegistrationNumber().equals(vehicle.getRegistrationNumber()))) {
                return true;
            } else if (vehicle instanceof Motorcycle && currentVehicle instanceof Motorcycle &&
                    (currentVehicle.getRegistrationNumber().equals(vehicle.getRegistrationNumber()))) {
                return true;
            }
        }

        return false;
    }

}
