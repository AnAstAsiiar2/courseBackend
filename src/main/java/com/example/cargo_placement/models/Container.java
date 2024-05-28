package com.example.cargo_placement.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "container")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Name of container can not be empty")
    @Column(name = "name", unique = true)
    private String name;

    @Positive(message = "Length must be a positive")
    @Column(name = "length")
    private Float length;

    @Positive(message = "Width must be a positive")
    @Column(name = "width")
    private Float width;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Cargo> cargos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = new Date(); // Set the created at date to the current time when the entity is created
    }
}
