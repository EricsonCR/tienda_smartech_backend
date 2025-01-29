package com.ericson.tiendasmartech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierDto {
    private long id;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
}
