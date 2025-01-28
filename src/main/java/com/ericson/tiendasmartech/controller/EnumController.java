package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.enums.*;
import com.ericson.tiendasmartech.model.ControllerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/enum")
public class EnumController {

    @GetMapping("/documento")
    public ResponseEntity<ControllerResponse> listarDocumentos() {
        ControllerResponse response = new ControllerResponse();
        ArrayList<String> lista = new ArrayList<>();
        for (Documento documento : Documento.values()) lista.add(documento.toString());
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Documentos encontrados");
        response.setData(lista);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estadoVenta")
    public ResponseEntity<ControllerResponse> listarEstadoVentas() {
        ControllerResponse response = new ControllerResponse();
        ArrayList<String> lista = new ArrayList<>();
        for (EstadoVenta estadoVenta : EstadoVenta.values()) lista.add(estadoVenta.toString());
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Estados venta encontrados");
        response.setData(lista);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estadoCuenta")
    public ResponseEntity<ControllerResponse> listarEstadoCuentas() {
        ControllerResponse response = new ControllerResponse();
        ArrayList<String> lista = new ArrayList<>();
        for (EstadoCuenta estadoCuenta : EstadoCuenta.values()) lista.add(estadoCuenta.toString());
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Estados cuenta encontrados");
        response.setData(lista);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/medida")
    public ResponseEntity<ControllerResponse> listarMedidas() {
        ControllerResponse response = new ControllerResponse();
        ArrayList<String> lista = new ArrayList<>();
        for (Medida medida : Medida.values()) lista.add(medida.toString());
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Medidas encontrados");
        response.setData(lista);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/metodoPago")
    public ResponseEntity<ControllerResponse> listarMetodoPagos() {
        ControllerResponse response = new ControllerResponse();
        ArrayList<String> lista = new ArrayList<>();
        for (MetodoPago metodoPago : MetodoPago.values()) lista.add(metodoPago.toString());
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Metodo de pago encontrados");
        response.setData(lista);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rol")
    public ResponseEntity<ControllerResponse> listarRol() {
        ControllerResponse response = new ControllerResponse();
        ArrayList<String> lista = new ArrayList<>();
        for (Rol rol : Rol.values()) lista.add(rol.toString());
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Rol encontrados");
        response.setData(lista);
        return ResponseEntity.ok(response);
    }
}
