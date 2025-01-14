package com.ericson.tiendasmartech.entity;

import com.ericson.tiendasmartech.enums.Documento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long id;

    @Enumerated(EnumType.STRING)
    private Documento documento;
    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String direccion;
    private String password;
    private Date nacimiento;
    private Date actualizacion;
    private boolean estado;

}
