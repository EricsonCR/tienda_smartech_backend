package com.ericson.tiendasmartech.entity;

import com.ericson.tiendasmartech.enums.Medida;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venta_detalles")
public class VentaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int cantidad;
    private double precio;
    private double total;
    private double descuento;
    private double impuesto;
    private String comentarios;

    @Enumerated(EnumType.STRING)
    private Medida medida;

    @ManyToOne
    @JoinColumn(name = "producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "venta")
    private Venta venta;
}
