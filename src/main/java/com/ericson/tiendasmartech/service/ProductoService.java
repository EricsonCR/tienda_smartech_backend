package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.entity.Producto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface ProductoService {
    ServiceResponse listar();

    ServiceResponse registrar(Producto producto);

    ServiceResponse actualizar(Producto producto);

    ServiceResponse eliminar(Long id);

    ServiceResponse buscarPorId(Long id);
}
