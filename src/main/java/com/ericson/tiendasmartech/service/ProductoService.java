package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.ProductoDto;
import com.ericson.tiendasmartech.entity.Producto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface ProductoService {
    ServiceResponse listar();

    ServiceResponse registrar(ProductoDto productoDto);

    ServiceResponse actualizar(ProductoDto productoDto);

    ServiceResponse eliminar(Long id);

    ServiceResponse buscarPorId(Long id);
}
