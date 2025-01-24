package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.AuthDto;
import com.ericson.tiendasmartech.model.ServiceResponse;

public interface AuthService {
    ServiceResponse login(AuthDto authDto);

    ServiceResponse register(AuthDto authDto);
}
