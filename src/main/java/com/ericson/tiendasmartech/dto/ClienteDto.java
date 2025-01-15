package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Documento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {
    private long id;
    private Documento documento;
    private String numero;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String email;
    private Date nacimiento;
    private Date registro;
    private Date actualiza;
    private boolean estado;
}
