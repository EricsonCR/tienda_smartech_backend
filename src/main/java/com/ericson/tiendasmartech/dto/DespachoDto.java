package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.EstadoDespacho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespachoDto {
    private long id;
    private long idVenta;
    private long idUsuario;
    private CourierDto courier;
    private String direccion;
    private EstadoDespacho estado;
    private String comentarios;
    private String tracking;
    private Date fechaEnvio;
    private Date fechaEntrega;
    private Time horaInicio;
    private Time horaFin;
}
