package com.ericson.tiendasmartech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;
    private int stock;

    @Column(updatable = false)
    private Date registro;
    private Date actualiza;
    private boolean estado;

    @PrePersist
    public void prePersist(){
        registro = new Date();
        actualiza = new Date();
        estado = true;
    }

    @PreUpdate
    public void preUpdate(){
        actualiza = new Date();
    }
}
