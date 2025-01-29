package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courier")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

    @GetMapping("/listar")
    public ResponseEntity<ControllerResponse> listar() {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = courierService.listar();
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }
}
