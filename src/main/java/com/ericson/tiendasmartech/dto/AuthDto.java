package com.ericson.tiendasmartech.dto;

import com.ericson.tiendasmartech.enums.Documento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    private Documento documento;
    private String numero;
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
}
