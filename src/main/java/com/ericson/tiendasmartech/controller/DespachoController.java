package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.DespachoDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.DespachoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/despacho")
@RequiredArgsConstructor
public class DespachoController {
    private final DespachoService despachoService;

    @GetMapping("/listar")
    public ResponseEntity<ControllerResponse> listar() {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = despachoService.listar();
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registrar")
    public ResponseEntity<ControllerResponse> registrar(@RequestBody DespachoDto despachoDto) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = despachoService.registrar(despachoDto);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ControllerResponse> buscarPorId(@PathVariable Long id) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = despachoService.buscarPorId(id);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ControllerResponse> actualizar(@RequestBody DespachoDto despachoDto) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = despachoService.actualizar(despachoDto);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ControllerResponse> eliminar(@PathVariable Long id) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = despachoService.eliminar(id);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }
}
