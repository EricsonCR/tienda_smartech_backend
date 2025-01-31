package com.ericson.tiendasmartech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
    private long id;
    private CategoriaDto categoria;
    private String usuarioEmail;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precio;
    private int stock;
    private boolean estado;
}
