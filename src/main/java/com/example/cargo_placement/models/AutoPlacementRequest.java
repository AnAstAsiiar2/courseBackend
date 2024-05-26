package com.example.cargo_placement.models;

import lombok.Data;

import java.util.List;

@Data
public class AutoPlacementRequest {
    private Container container;
    private List<CargoTypeAndCount> cargosTypeAndCount;
}
