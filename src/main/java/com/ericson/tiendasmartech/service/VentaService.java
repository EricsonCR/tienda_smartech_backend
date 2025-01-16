package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.VentaDto;
import com.ericson.tiendasmartech.entity.Venta;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface VentaService {
    ServiceResponse registrar(VentaDto ventaDto);

    ServiceResponse listar();

    ServiceResponse actualizar(VentaDto ventaDto);

    ServiceResponse buscarPorId(long id);

    ServiceResponse eliminar(long id);
}
