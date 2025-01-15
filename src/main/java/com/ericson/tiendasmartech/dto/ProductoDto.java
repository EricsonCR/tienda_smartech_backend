package com.ericson.tiendasmartech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
    private long id;
    private CategoriaDto categoria;
    private String usuario;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precio;
    private int stock;
    private boolean estado;
}
