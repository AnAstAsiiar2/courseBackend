package com.example.cargo_placement.models;

import com.example.cargo_placement.validators.ValidHexColor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "cargo_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Name of container can not be empty")
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "hex_color")
    @ValidHexColor(message = "It is not valid HEX color")
    private String hex_color;

    @Column(name = "length")
    @Positive(message = "Length must be a positive")
    private Float length;

    @Column(name = "width")
    @Positive(message = "Width must be a positive")
    private Float width;

    @Column(name = "weight")
    @Positive(message = "Weight must be a positive")
    private Float weight;
}