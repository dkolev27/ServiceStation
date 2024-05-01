import manager.Manageable;
import manager.Manager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InstantiationException {

        Manageable manager = new Manager();
        manager.manage(new Scanner(System.in));
    }
}