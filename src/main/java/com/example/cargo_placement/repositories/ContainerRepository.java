package com.example.cargo_placement.repositories;

import com.example.cargo_placement.models.Container;
import com.example.cargo_placement.models.ContainerDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContainerRepository extends JpaRepository<Container,Integer> {
    @EntityGraph(attributePaths = {"cargos"})
    Optional<Container> findWithCargosById(@Param("id") Integer id);
    @Query("SELECT new com.example.cargo_placement.models.ContainerDTO(c.id, c.name, c.maxWeight,c.length,c.width,c.createdAt) FROM Container c")
    List<ContainerDTO> getAllContainerDTOs();
}
