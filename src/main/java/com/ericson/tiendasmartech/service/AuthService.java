package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.AuthDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface AuthService {
    ServiceResponse login(AuthDto authDto);

    ServiceResponse register(AuthDto authDto);

    ServiceResponse validarEmail(String token);

    ServiceResponse enviarEmailDeRegistro(String email);

    ServiceResponse enviarEmailDeRecuperacion(String email);
}
