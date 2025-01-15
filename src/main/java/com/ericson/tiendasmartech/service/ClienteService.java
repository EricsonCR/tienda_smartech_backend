package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.entity.Cliente;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface ClienteService {
    ServiceResponse registrar(Cliente cliente);

    ServiceResponse listar();

    ServiceResponse actualizar(Cliente cliente);

    ServiceResponse buscarPorId(long id);

    ServiceResponse eliminar(long id);
}
