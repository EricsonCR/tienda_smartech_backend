package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.VentaDto;
import com.ericson.tiendasmartech.entity.Venta;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/venta")
@RequiredArgsConstructor
public class VentaController {
    private final VentaService ventaService;

    @GetMapping("/listar")
    public ResponseEntity<ControllerResponse> listar() {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = ventaService.listar();
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registrar")
    ResponseEntity<ControllerResponse> registrar(@RequestBody VentaDto ventaDto) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = ventaService.registrar(ventaDto);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscarPorId/{id}")
    ResponseEntity<ControllerResponse> buscarPorId(@PathVariable long id) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = ventaService.buscarPorId(id);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizar")
    ResponseEntity<ControllerResponse> actualizar(@RequestBody VentaDto ventaDto) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = ventaService.actualizar(ventaDto);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<ControllerResponse> eliminar(@PathVariable long id) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = ventaService.eliminar(id);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }
}
