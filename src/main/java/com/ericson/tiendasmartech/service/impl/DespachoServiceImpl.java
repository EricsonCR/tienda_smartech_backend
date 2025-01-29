package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.dto.CourierDto;
import com.ericson.tiendasmartech.dto.DespachoDto;
import com.ericson.tiendasmartech.entity.Courier;
import com.ericson.tiendasmartech.entity.Despacho;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.entity.Venta;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.CourierRepository;
import com.ericson.tiendasmartech.repository.DespachoRepository;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.repository.VentaRepository;
import com.ericson.tiendasmartech.service.DespachoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DespachoServiceImpl implements DespachoService {

    private final DespachoRepository despachoRepository;
    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CourierRepository courierRepository;

    @Override
    public ServiceResponse listar() {
        ServiceResponse response = new ServiceResponse();
        List<DespachoDto> lista = new ArrayList<>();
        try {
            for (Despacho despacho : despachoRepository.findAll()) lista.add(entityToDto(despacho));
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Listado de Despachos exitoso");
            response.setData(lista);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al listar despacho");
        }
        return response;
    }

    @Override
    public ServiceResponse buscarPorId(long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (despachoRepository.existsById(id)) {
                Despacho despacho = despachoRepository.findById(id).orElse(new Despacho());
                DespachoDto despachoDto = entityToDto(despacho);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Despacho encontrado");
                response.setData(despachoDto);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Despacho no encontrado");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al buscar despacho");
        }
        return response;
    }

    @Override
    public ServiceResponse registrar(DespachoDto despachoDto) {
        ServiceResponse response = new ServiceResponse();
        try {
            Despacho despacho = dtoToEntity(despachoDto);
            Venta venta = despacho.getVenta();
            if (venta.equals(new Venta())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("La venta no existe");
                return response;
            }
            if (despachoRepository.existsByVentaId(venta.getId())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El venta ya esta registrado en los despachos");
                return response;
            }
            Usuario usuario = despacho.getUsuario();
            if (usuario.equals(new Usuario())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El usuario no existe");
                return response;
            }
            Courier courier = despacho.getCourier();
            if (courierRepository.existsByNombre(courier.getNombre())) despacho.setCourier(courier);
            else courierRepository.save(courier);

            despachoRepository.save(despacho);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Despacho registrado correctamente");

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al registrar despacho");
        }
        return response;
    }

    @Override
    public ServiceResponse actualizar(DespachoDto despachoDto) {
        ServiceResponse response = new ServiceResponse();
        try {
            Despacho despacho = dtoToEntity(despachoDto);
            if (despachoRepository.existsById(despacho.getId())) {
                if (despacho.getVenta().equals(new Venta())) {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setMessage("La venta no existe");
                    return response;
                }
                if (despacho.getUsuario().equals(new Usuario())) {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setMessage("El usuario no existe");
                    return response;
                }
                Courier courier = despacho.getCourier();
                if (courierRepository.existsByNombre(courier.getNombre())) despacho.setCourier(courier);
                else courierRepository.save(courier);

                despachoRepository.save(despacho);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Despacho actualizado correctamente");
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Despacho no encontrado");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al actualizar despacho");
        }
        return response;
    }

    @Override
    public ServiceResponse eliminar(long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (despachoRepository.existsById(id)) {
                despachoRepository.deleteById(id);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Despacho eliminado correctamente");
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Despacho no encontrado");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al eliminar despacho");
        }
        return response;
    }

    private Despacho dtoToEntity(DespachoDto despachoDto) {
        Venta venta = ventaRepository.findById(despachoDto.getIdVenta()).orElse(new Venta());
        Usuario usuario = usuarioRepository.findById(despachoDto.getIdUsuario()).orElse(new Usuario());
        String nombre = despachoDto.getCourier().getNombre();
        Courier courier = courierRepository
                .findByNombre(nombre)
                .orElse(new Courier(0, nombre, null, null, null));
        return new Despacho(
                despachoDto.getId(),
                venta,
                usuario,
                courier,
                despachoDto.getTracking(),
                despachoDto.getDireccion(),
                despachoDto.getEstado(),
                despachoDto.getComentarios(),
                despachoDto.getFechaEnvio(),
                despachoDto.getFechaEntrega(),
                despachoDto.getHoraInicio(),
                despachoDto.getHoraFin(),
                null,
                null
        );
    }

    private DespachoDto entityToDto(Despacho despacho) {
        CourierDto courierDto = new CourierDto(
                despacho.getCourier().getId(),
                despacho.getCourier().getNombre(),
                despacho.getCourier().getTelefono(),
                despacho.getCourier().getEmail(),
                despacho.getCourier().getDireccion()
        );
        return new DespachoDto(
                despacho.getId(),
                despacho.getVenta().getId(),
                despacho.getUsuario().getId(),
                courierDto,
                despacho.getDireccion(),
                despacho.getEstado(),
                despacho.getComentarios(),
                despacho.getTracking(),
                despacho.getFecha_envio(),
                despacho.getFecha_entrega(),
                despacho.getHora_inicio(),
                despacho.getHora_fin()
        );
    }
}
