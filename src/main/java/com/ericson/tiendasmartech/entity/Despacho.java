package com.ericson.tiendasmartech.entity;

import com.ericson.tiendasmartech.enums.EstadoDespacho;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "despachos")
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "courier")
    private Courier courier;

    private String tracking;
    private String direccion;

    @Enumerated(EnumType.STRING)
    private EstadoDespacho estado;
    private String comentarios;
    private Date fecha_envio;
    private Date fecha_entrega;
    private Time hora_inicio;
    private Time hora_fin;
    private Date registro;
    private Date actualiza;

    @PrePersist
    protected void onCreate() {
        registro = new Date();
        actualiza = new Date();
        estado = EstadoDespacho.PROCESANDO;
    }

    @PreUpdate
    protected void onUpdate() {
        actualiza = new Date();
    }
}
