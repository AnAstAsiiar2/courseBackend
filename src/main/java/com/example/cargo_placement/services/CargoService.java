package com.example.cargo_placement.services;

import com.example.cargo_placement.models.Cargo;
import com.example.cargo_placement.models.CargoType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CargoService {
    public List<Cargo> createTypedCargoList(CargoType cargoType, Integer count){
        List<Cargo> cargos = new ArrayList<>();
        for(int i = 0; i<count; i++){
            cargos.add(new Cargo());
            cargos.getLast().setCargoType(cargoType);
        }
        return cargos;
    }
}
