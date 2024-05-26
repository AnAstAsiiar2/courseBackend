package com.example.cargo_placement.models;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    private List<Cargo> cargos; // List to hold cargos with their positions

    // Constructor
    public Individual() {
        this.cargos = new ArrayList<>();
    }

    // Copy constructor for cloning individuals
    public Individual(Individual other) {
        this.cargos = new ArrayList<>();
        for (Cargo cargo : other.cargos) {
            this.cargos.add(new Cargo(cargo)); // Assuming Cargo class has a copy constructor
        }
    }

    // Getter and Setter
    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    // Optional: Add a method to calculate the fitness of this individual
    public double calculateFitness(Container container) {
        double penalty = 0;
        for (int i = 0; i < this.cargos.size(); i++) {
            Cargo cargo1 = this.cargos.get(i);
            // Check if cargo is out of container bounds
            if (cargo1.getX() + cargo1.getCargoType().getWidth() > container.getWidth() ||
                    cargo1.getY() + cargo1.getCargoType().getLength() > container.getLength()) {
                penalty += 1000; // Assign a high penalty for out-of-bounds
            }

            // Check for overlaps with other cargos
            for (int j = i + 1; j < this.cargos.size(); j++) {
                Cargo cargo2 = this.cargos.get(j);
                if (rectanglesOverlap(cargo1, cargo2)) {
                    penalty += 1000; // Assign a high penalty for overlaps
                }
            }
        }
        return -penalty; // Fitness is better when penalty is lower
    }

    private boolean rectanglesOverlap(Cargo cargo1, Cargo cargo2) {
        return cargo1.getX() < cargo2.getX() + cargo2.getCargoType().getWidth() &&
                cargo1.getX() + cargo1.getCargoType().getWidth() > cargo2.getX() &&
                cargo1.getY() < cargo2.getY() + cargo2.getCargoType().getLength() &&
                cargo1.getY() + cargo1.getCargoType().getLength() > cargo2.getY();
    }
}
