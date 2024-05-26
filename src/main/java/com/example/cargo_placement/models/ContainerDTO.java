package com.example.cargo_placement.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ContainerDTO {
    private Integer id;
    private String name;
    private Float maxWeight;
    private Float length;
    private Float width;
    private Date createdAt;
    public ContainerDTO(Container container) {
        id = container.getId();
        name = container.getName();
        maxWeight = container.getMaxWeight();
        length = container.getLength();
        width = container.getWidth();
        createdAt = container.getCreatedAt();
    }
}

