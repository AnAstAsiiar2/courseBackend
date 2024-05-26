package com.example.cargo_placement.services;

import com.example.cargo_placement.models.Cargo;
import com.example.cargo_placement.models.Container;
import com.example.cargo_placement.models.Individual;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class CargoPlacementService {

    private static final int MAX_GENERATIONS = 2000;
    private static final int POPULATION_SIZE = 300;
    private final Random random = new Random();

    public List<Cargo> calculatePlacement(Container container, List<Cargo> cargos) {
        List<Individual> population = initializePopulation(cargos, container,POPULATION_SIZE);
        for (int i = 0; i < MAX_GENERATIONS; i++) {
            List<Individual> newPopulation = new ArrayList<>();
            while (newPopulation.size() < POPULATION_SIZE) {
                Individual parent1 = select(population,container);
                Individual parent2 = select(population,container);
                List<Individual> children = crossover(parent1, parent2);
                mutate(children);
                newPopulation.addAll(children);
            }
            population = newPopulation;
            if (checkConvergence(population,container)) {
                break;
            }
        }
        return extractBestSolution(population,container);
    }

    private List<Individual> initializePopulation(List<Cargo> cargos, Container container, int populationSize) {
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Individual individual = new Individual();
            for (Cargo cargo : cargos) {
                Cargo placedCargo = new Cargo(cargo);
                placedCargo.setCargoType(cargo.getCargoType());
                placedCargo.setX(random.nextDouble() * (container.getWidth() - cargo.getCargoType().getWidth()));
                placedCargo.setY(random.nextDouble() * (container.getLength() - cargo.getCargoType().getLength()));
                individual.getCargos().add(placedCargo);
            }
            population.add(individual);
        }
        return population;
    }
    private Individual select(List<Individual> population,Container container) {
        int tournamentSize = 5;
        Individual best = null;
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(population.size());
            Individual competitor = population.get(randomIndex);
            if (best == null || evaluateFitness(competitor,container) > evaluateFitness(best,container)) {
                best = competitor;
            }
        }
        return best;
    }


    private List<Individual> crossover(Individual parent1, Individual parent2) {
        Individual child1 = new Individual();
        Individual child2 = new Individual();
        int crossPoint = random.nextInt(parent1.getCargos().size());

        for (int i = 0; i < crossPoint; i++) {
            child1.getCargos().add(new Cargo(parent1.getCargos().get(i)));
            child2.getCargos().add(new Cargo(parent2.getCargos().get(i)));
        }
        for (int i = crossPoint; i < parent1.getCargos().size(); i++) {
            child1.getCargos().add(new Cargo(parent2.getCargos().get(i)));
            child2.getCargos().add(new Cargo(parent1.getCargos().get(i)));
        }
        return Arrays.asList(child1, child2);
    }


    private void mutate(List<Individual> individuals) {
        double mutationRate = 0.05;  // 5% mutation rate
        for (Individual individual : individuals) {
            if (random.nextDouble() < mutationRate) {
                for (Cargo cargo : individual.getCargos()) {
                    cargo.setX(Math.max(0, cargo.getX() + (random.nextDouble() - 0.5) * 10)); // Randomly adjust X
                    cargo.setY(Math.max(0, cargo.getY() + (random.nextDouble() - 0.5) * 10)); // Randomly adjust Y
                }
            }
        }
    }

    private boolean checkConvergence(List<Individual> population,Container container) {
        double threshold = -100; // Define a threshold for satisfactory fitness
        for (Individual individual : population) {
            if (evaluateFitness(individual,container) > threshold) {
                return true;
            }
        }
        return false;
    }

    private List<Cargo> extractBestSolution(List<Individual> population,Container container) {
        Individual bestIndividual = population.get(0);
        double bestFitness = evaluateFitness(bestIndividual,container);

        for (Individual individual : population) {
            double fitness = evaluateFitness(individual,container);
            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestIndividual = individual;
            }
        }

        return bestIndividual.getCargos();
    }
    private double evaluateFitness(Individual individual, Container container) {
        double penalty = 0;
        List<Cargo> cargos = individual.getCargos();
        for (int i = 0; i < cargos.size(); i++) {
            Cargo cargo1 = cargos.get(i);
            // Check bounds
            if (cargo1.getX() + cargo1.getCargoType().getWidth() > container.getWidth() ||
                    cargo1.getY() + cargo1.getCargoType().getLength() > container.getLength()) {
                penalty += 1000; // Arbitrary penalty for exceeding container bounds
            }

            // Check overlaps
            for (int j = i + 1; j < cargos.size(); j++) {
                Cargo cargo2 = cargos.get(j);
                if (rectanglesOverlap(cargo1, cargo2)) {
                    penalty += 1000; // Arbitrary penalty for overlaps
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
