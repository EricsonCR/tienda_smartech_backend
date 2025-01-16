package com.ericson.tiendasmartech.entity;

import com.ericson.tiendasmartech.enums.Documento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Documento documento;

    private String numero;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String email;
    private String password;
    private Date nacimiento;
    @Column(updatable = false)
    private Date registro;
    private Date actualiza;
    private boolean estado;

//    @OneToMany(mappedBy = "cliente")
//    private List<Venta> ventas;

    @PrePersist
    public void prePersist() {
        registro = new Date();
        actualiza = new Date();
        estado = true;
    }

    @PreUpdate
    public void preUpdate() {
        actualiza = new Date();
    }
}
