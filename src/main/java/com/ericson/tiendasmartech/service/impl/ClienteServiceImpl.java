package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.dto.ClienteDto;
import com.ericson.tiendasmartech.entity.Cliente;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.ClienteRepository;
import com.ericson.tiendasmartech.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public ServiceResponse registrar(Cliente cliente) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (clienteRepository.existsByNumero(cliente.getNumero())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El numero ya existe");
                return response;
            }
            if (clienteRepository.existsByEmail(cliente.getEmail())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El email ya existe");
                return response;
            }
            clienteRepository.save(cliente);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Cliente registrado correctamente");
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al registrar");
        }
        return response;
    }

    @Override
    public ServiceResponse listar() {
        ServiceResponse response = new ServiceResponse();
        try {
            List<ClienteDto> listaClienteDto = new ArrayList<>();
            List<Cliente> listaCliente = clienteRepository.findAll();
            for (Cliente cliente : listaCliente) listaClienteDto.add(entityToDto(cliente));
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Lista de clientes");
            response.setData(listaClienteDto);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al listar");
        }
        return response;
    }

    @Override
    public ServiceResponse actualizar(Cliente cliente) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (clienteRepository.existsByNumeroAndIdNot(cliente.getNumero(), cliente.getId())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El numero ya esta en uso");
                return response;
            }
            if (clienteRepository.existsByEmailAndIdNot(cliente.getEmail(), cliente.getId())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El email ya esta en uso");
                return response;
            }
            clienteRepository.save(cliente);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Cliente actualizado correctamente");
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al actualizar");
        }
        return response;
    }

    @Override
    public ServiceResponse buscarPorId(long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Cliente> optionalCliente = clienteRepository.findById(id);
            if (optionalCliente.isPresent()) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Cliente encontrado");
                response.setData(entityToDto(optionalCliente.get()));
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Cliente no encontrado");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al buscar");
        }
        return response;
    }

    @Override
    public ServiceResponse eliminar(long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (clienteRepository.existsById(id)) {
                clienteRepository.deleteById(id);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Cliente eliminado correctamente");
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El id no existe");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al eliminar");
        }
        return response;
    }

    private ClienteDto entityToDto(Cliente cliente) {
        return new ClienteDto(
                cliente.getId(),
                cliente.getDocumento(),
                cliente.getNumero(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getEmail(),
                cliente.getNacimiento(),
                cliente.getRegistro(),
                cliente.getActualiza(),
                cliente.isEstado()
        );
    }
}
