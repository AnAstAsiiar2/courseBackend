package com.example.cargo_placement.repositories;

import com.example.cargo_placement.models.Cargo;
import com.example.cargo_placement.models.CargoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo,Integer> {
}
