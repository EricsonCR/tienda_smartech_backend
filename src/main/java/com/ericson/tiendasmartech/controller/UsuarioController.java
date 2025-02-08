package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<ControllerResponse> listar() {
        ControllerResponse controllerResponse = new ControllerResponse();
        ServiceResponse serviceResponse = usuarioService.listar();
        controllerResponse.setStatus(serviceResponse.getStatus());
        controllerResponse.setMessage(serviceResponse.getMessage());
        controllerResponse.setData(serviceResponse.getData());
        return ResponseEntity.ok(controllerResponse);
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ControllerResponse> buscarPorId(@PathVariable long id) {
        ControllerResponse controllerResponse = new ControllerResponse();
        ServiceResponse serviceResponse = usuarioService.buscarPorId(id);
        controllerResponse.setStatus(serviceResponse.getStatus());
        controllerResponse.setMessage(serviceResponse.getMessage());
        controllerResponse.setData(serviceResponse.getData());
        return ResponseEntity.ok(controllerResponse);
    }

    @GetMapping("/buscarPorEmail/{email}")
    public ResponseEntity<ControllerResponse> buscarPorEmail(@PathVariable String email) {
        ControllerResponse controllerResponse = new ControllerResponse();
        ServiceResponse serviceResponse = usuarioService.buscarPorEmail(email);
        controllerResponse.setStatus(serviceResponse.getStatus());
        controllerResponse.setMessage(serviceResponse.getMessage());
        controllerResponse.setData(serviceResponse.getData());
        return ResponseEntity.ok(controllerResponse);
    }

    @PostMapping("/registrar")
    public ResponseEntity<ControllerResponse> registrar(@RequestBody UsuarioDto usuarioDto) {
        ControllerResponse controllerResponse = new ControllerResponse();
        ServiceResponse serviceResponse = usuarioService.registrar(usuarioDto);
        controllerResponse.setStatus(serviceResponse.getStatus());
        controllerResponse.setMessage(serviceResponse.getMessage());
        controllerResponse.setData(serviceResponse.getData());
        return ResponseEntity.ok(controllerResponse);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ControllerResponse> actualizar(@RequestBody UsuarioDto usuarioDto) {
        ControllerResponse controllerResponse = new ControllerResponse();
        ServiceResponse serviceResponse = usuarioService.actualizar(usuarioDto);
        controllerResponse.setStatus(serviceResponse.getStatus());
        controllerResponse.setMessage(serviceResponse.getMessage());
        controllerResponse.setData(serviceResponse.getData());
        return ResponseEntity.ok(controllerResponse);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ControllerResponse> eliminar(@PathVariable long id) {
        ControllerResponse controllerResponse = new ControllerResponse();
        ServiceResponse serviceResponse = usuarioService.eliminar(id);
        controllerResponse.setStatus(serviceResponse.getStatus());
        controllerResponse.setMessage(serviceResponse.getMessage());
        controllerResponse.setData(serviceResponse.getData());
        return ResponseEntity.ok(controllerResponse);
    }
}
