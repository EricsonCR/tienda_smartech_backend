package com.ericson.tiendasmartech.entity;

import com.ericson.tiendasmartech.enums.EstadoVenta;
import com.ericson.tiendasmartech.enums.MetodoPago;
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
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Double total;
    private Double descuento;
    private Double impuesto;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodo;

    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;

    private String comentarios;

    @Column(updatable = false)
    private Date registro;

    private Date actualiza;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VentaDetalle> ventaDetalle;

    @PrePersist
    public void prePersist() {
        registro = new Date();
        actualiza = new Date();
        estado = EstadoVenta.PENDIENTE;
    }

    @PreUpdate
    public void preUpdate() {
        actualiza = new Date();
    }
}
