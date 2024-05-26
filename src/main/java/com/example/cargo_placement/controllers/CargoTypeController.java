package com.example.cargo_placement.controllers;

import com.example.cargo_placement.models.CargoType;
import com.example.cargo_placement.repositories.CargoTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cargo_type")
public class CargoTypeController {
    @Autowired
    private CargoTypeRepository cargoTypeRepository;
    @PostMapping("/create")
    public ResponseEntity createContainer(@RequestBody CargoType cargoType){
        cargoTypeRepository.save(cargoType);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/get_all")
    public ResponseEntity<List<CargoType>> getAll(){
        List<CargoType> containers= cargoTypeRepository.findAll();
        return ResponseEntity.ok(containers);
    }
}