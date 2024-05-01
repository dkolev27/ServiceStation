package manager;

import service.*;
import users.User;
import users.UsersCollection;

import java.io.IOException;
import java.util.Scanner;

public interface Manageable {

    // Main method (the business logic here)
    void manage(Scanner scanner) throws IOException, InstantiationException;


    // Util methods
    String getUserType(Scanner scanner);
    void createUser(Scanner scanner, UsersCollection usersCollection, boolean isAdminLoggedIn) throws IOException;
    void removeUser(Scanner scanner, UsersCollection usersCollection, boolean isAdminLoggedIn);
    void welcomeClient(Scanner scanner,
                       ServiceClients clientsInService, ServiceMechanics mechanicsInService, ServiceVehicles vehiclesInService,
                       boolean isHostLoggedIn, ServiceStationParts partsInService)
                       throws InstantiationException, IOException;
    void sendOutClient(Scanner scanner, ServiceClients clientsInService, ServiceVehicles vehiclesInService, boolean isHostLoggedIn)
            throws InstantiationException;
    void hireMechanic(Scanner scanner, ServiceMechanics mechanicsInService, boolean isTechnicianLoggedIn);
    void fireMechanic(Scanner scanner, ServiceMechanics service, boolean isAdminLoggedIn);
    void removeVehicle(Scanner scanner, ServiceVehicles vehiclesInService, boolean isAdminLoggedIn);
    void printAllUsers(UsersCollection usersCollection, boolean isAdminLoggedIn, boolean isHostLoggedIn, boolean isTechnicianLoggedIn);
    void printAllVehicles(ServiceVehicles vehiclesInService, User loggedUser);
    void printAllMechanics(ServiceMechanics mechanicsInService);
    void printAllClients(ServiceClients clientsInService, boolean isAdminLoggedIn);
    void printVehiclesOf(Scanner scanner, ServiceClients clientsInService, boolean isAdminLoggedIn) ;
    void printAllParts(ServiceStationParts partsInService, boolean isAdminLoggedIn);
    void helpUser();

}