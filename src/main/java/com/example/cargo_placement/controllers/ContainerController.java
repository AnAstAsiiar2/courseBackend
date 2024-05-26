package com.example.cargo_placement.controllers;

import com.example.cargo_placement.models.*;
import com.example.cargo_placement.repositories.CargoTypeRepository;
import com.example.cargo_placement.repositories.ContainerRepository;
import com.example.cargo_placement.services.CargoPlacementService;
import com.example.cargo_placement.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/container")
public class ContainerController {
    @Autowired
    private ContainerRepository containerRepository;
    @Autowired
    private CargoTypeRepository cargoTypeRepository;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private CargoPlacementService cargoPlacementService;


    @Transactional
    @PostMapping("/create")
    public ResponseEntity<?> createContainer(@RequestBody Container container) {
        if (container.getCargos() != null) {
            container.getCargos().forEach(cargo -> cargo.setContainer(container));
        }
        Container savedContainer = containerRepository.save(container);
        return ResponseEntity.ok(savedContainer.getId());
    }
    @PostMapping("/auto_placement")
    public ResponseEntity<?> autoPlacement(@RequestBody AutoPlacementRequest request) {
        Container container = request.getContainer();
        List<Integer> cargoTypeIds = request.getCargosTypeAndCount()
                .stream()
                .map(CargoTypeAndCount::getTypeID)
                .toList();
        List<CargoType> cargoTypes = cargoTypeRepository.findAllById(cargoTypeIds);
        List<Cargo> cargos = request.getCargosTypeAndCount()
                .stream()
                .flatMap(cargoTypeCount -> {
                    CargoType cargoType = cargoTypes.stream()
                            .filter(type -> type.getId().equals(cargoTypeCount.getTypeID()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("CargoType not found for ID: " + cargoTypeCount.getTypeID()));
                    return cargoService.createTypedCargoList(cargoType, cargoTypeCount.getCount()).stream();
                })
                .toList();
        List<Cargo> bestCargosPlacement = cargoPlacementService.calculatePlacement(container,cargos);
        bestCargosPlacement
                .forEach(cargo -> cargo.setContainer(container));
        container.setCargos(bestCargosPlacement);
        var containerId = containerRepository.save(container);
        return ResponseEntity.ok(containerId);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ContainerDTO>> getAll(){
        List<ContainerDTO> containers= containerRepository.getAllContainerDTOs().reversed();
        return ResponseEntity.ok(containers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Container>> getByID(@PathVariable Integer id){
        Optional<Container> container= containerRepository.findWithCargosById(id);
        return ResponseEntity.ok(container);
    }
    @PutMapping("/{id}")//delete method not alowed  
    public ResponseEntity deleteContainer(@PathVariable Integer id){
        containerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}