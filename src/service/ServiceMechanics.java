package service;

import manager.TextColor;
import mechanic.Mechanic;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceMechanics implements ServiceAPI<Mechanic> {

    // Constants
    private final static int MECHANICS_CAPACITY = 5;

    private final static String FILEPATH = "src/service/mechanics.txt";


    // Field
    private HashSet<Mechanic> mechanicsInService;


    // Constructor
    public ServiceMechanics() throws IOException {
        this.setMechanicsInService(MECHANICS_CAPACITY);
    }


    // Setter and Getter
    public HashSet<Mechanic> getMechanicsInService() {
        return mechanicsInService;
    }

    public void setMechanicsInService(int capacity) throws IOException {
        this.mechanicsInService = new HashSet<>(capacity);

        File file = new File(FILEPATH);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            this.mechanicsInService.add(new Mechanic(st));
        }
    }


    // Util methods
    private void addMechanicToFile(Mechanic mechanic) throws IOException {
        String name = mechanic.getName();

        if (hasMechanicInFile(mechanic)) {
            System.out.printf("Mechanic with name %s is already an employee!" + System.lineSeparator(), mechanic.getName());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true))) {
            writer.write(name);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasMechanicInFile(Mechanic mechanic) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(mechanic.getName())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    // Overridden methods
    @Override
    public void add(Mechanic mechanic) {
        if (hasMechanicInFile(mechanic)) {
            return;
        }

        try {
            addMechanicToFile(mechanic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mechanicsInService.add(mechanic);
    }

    @Override
    public void remove(Mechanic mechanic) {
        if (!hasMechanicInFile(mechanic)) {
            System.out.println(TextColor.ANSI_RED + "There is no mechanic with a name: " +
                    TextColor.ANSI_RESET + mechanic.getName());
            return;
        }

        Path path = Paths.get(FILEPATH);
        try {
            List<String> lines = Files.readAllLines(path);
            lines = lines.stream()
                    .filter(l -> !l.contains(mechanic.getName()))
                    .collect(Collectors.toList());
            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mechanicsInService.remove(mechanic);
        System.out.printf(TextColor.ANSI_PURPLE + "Mechanic %s successfully fired!" +
                        System.lineSeparator() + TextColor.ANSI_RESET,
                        mechanic.getName());
    }


    @Override
    public void print() {
        for (Mechanic mechanic : this.mechanicsInService) {
            System.out.println(mechanic.getName());
        }
    }

}
