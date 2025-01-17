package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Documento;
import com.ericson.tiendasmartech.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private long id;
    private Documento documento;
    private String numero;
    private String nombres;
    private String apellidos;
    private Rol rol;
    private String email;
    private String telefono;
    private String direccion;
    private Date nacimiento;
    private boolean estado;
}
