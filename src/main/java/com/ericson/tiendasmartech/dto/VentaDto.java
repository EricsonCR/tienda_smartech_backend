package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.EstadoVenta;
import com.ericson.tiendasmartech.enums.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaDto {
    private long id;
    private long clienteId;
    private Double total;
    private Double descuento;
    private Double impuesto;
    private MetodoPago metodo;
    private EstadoVenta estado;
    private List<VentaDetalleDto> ventaDetalle;
    private String comentarios;
    private Date registro;
    private Date actualiza;
}
