package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.ProductoDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/listar")
    public ResponseEntity<ControllerResponse> listar() {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = productoService.listar();
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registrar")
    public ResponseEntity<ControllerResponse> registrar(@RequestBody ProductoDto productoDto) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = productoService.registrar(productoDto);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ControllerResponse> buscarPorId(@PathVariable Long id) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = productoService.buscarPorId(id);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ControllerResponse> actualizar(@RequestBody ProductoDto productoDto) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = productoService.actualizar(productoDto);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ControllerResponse> eliminar(@PathVariable Long id) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = productoService.eliminar(id);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }
}
