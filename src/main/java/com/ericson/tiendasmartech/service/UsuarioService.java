package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface UsuarioService {
    ServiceResponse registrar(Usuario usuario);

    ServiceResponse listar();

    ServiceResponse actualizar(Usuario usuario);

    ServiceResponse buscarPorId(long id);

    ServiceResponse eliminar(long id);
}
