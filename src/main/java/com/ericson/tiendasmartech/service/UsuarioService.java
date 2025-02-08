package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface UsuarioService {
    ServiceResponse registrar(UsuarioDto usuarioDto);

    ServiceResponse listar();

    ServiceResponse actualizar(UsuarioDto usuarioDto);

    ServiceResponse buscarPorId(long id);

    ServiceResponse buscarPorEmail(String email);

    ServiceResponse eliminar(long id);
}
