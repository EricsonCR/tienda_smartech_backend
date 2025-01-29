package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.DespachoDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface DespachoService {
    ServiceResponse listar();

    ServiceResponse buscarPorId(long id);

    ServiceResponse registrar(DespachoDto despachoDto);

    ServiceResponse actualizar(DespachoDto despachoDto);

    ServiceResponse eliminar(long id);
}
