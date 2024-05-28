package com.example.cargo_placement.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "cargo")
@NoArgsConstructor
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "x")
    private Double x;

    @Column(name = "y")
    private Double y;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private CargoType cargoType;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "container_id")
    @JsonBackReference
    private Container container;

    public Cargo(Cargo cargo) {
        this.id = cargo.id;
        this.x = cargo.x;
        this.y = cargo.y;
        this.cargoType = cargo.cargoType;
        this.container = cargo.container;
    }
}
