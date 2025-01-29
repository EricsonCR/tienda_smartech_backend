package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.entity.Courier;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.CourierRepository;
import com.ericson.tiendasmartech.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;

    @Override
    public ServiceResponse listar() {
        ServiceResponse response = new ServiceResponse();
        try {
            List<Courier> lista = courierRepository.findAll();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Couriers encontrados");
            response.setData(lista);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al listar");
        }
        return response;
    }
}
