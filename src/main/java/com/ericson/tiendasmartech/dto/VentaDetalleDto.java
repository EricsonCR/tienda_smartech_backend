package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Medida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaDetalleDto {
    private long id;
    private int cantidad;
    private double precio;
    private double total;
    private double descuento;
    private double impuesto;
    private String comentarios;
    private Medida medida;
    private long productoId;
    private long ventaId;
}
