package com.ericson.tiendasmartech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "couriers")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;

    @PrePersist
    protected void onCreate() {
        nombre = nombre.toUpperCase();
    }
}
