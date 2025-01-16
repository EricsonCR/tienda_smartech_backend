package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.dto.VentaDetalleDto;
import com.ericson.tiendasmartech.dto.VentaDto;
import com.ericson.tiendasmartech.entity.Cliente;
import com.ericson.tiendasmartech.entity.Producto;
import com.ericson.tiendasmartech.entity.Venta;
import com.ericson.tiendasmartech.entity.VentaDetalle;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.ClienteRepository;
import com.ericson.tiendasmartech.repository.ProductoRepository;
import com.ericson.tiendasmartech.repository.VentaRepository;
import com.ericson.tiendasmartech.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {
    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    @Override
    public ServiceResponse registrar(VentaDto ventaDto) {
        ServiceResponse response = new ServiceResponse();
        boolean flagProducto = true;
        try {
            Venta venta = dtoToEntity(ventaDto);
            if (clienteRepository.existsById(venta.getCliente().getId())) {
                for (VentaDetalle ventaDetalle : venta.getVentaDetalle()) {
                    long id = ventaDetalle.getProducto().getId();
                    if (productoRepository.existsById(id)) ventaDetalle.setVenta(venta);
                    else flagProducto = false;
                }
                if (flagProducto) {
                    ventaRepository.save(venta);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Venta registrada exitosamente");
                } else {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setMessage("El producto no existe");
                }
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Cliente no encontrado");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al registrar venta");
        }
        return response;
    }

    @Override
    public ServiceResponse listar() {
        ServiceResponse response = new ServiceResponse();
        List<VentaDto> listaVentaDto = new ArrayList<>();
        try {
            List<Venta> listaVenta = ventaRepository.findAll();
            for (Venta venta : listaVenta) listaVentaDto.add(entityToDto(venta));
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Venta listada exitosamente");
            response.setData(listaVentaDto);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al listar venta");
        }
        return response;
    }

    @Override
    public ServiceResponse actualizar(VentaDto ventaDto) {
        ServiceResponse response = new ServiceResponse();
        boolean flagProducto = true;
        try {
            Venta venta = dtoToEntity(ventaDto);
            Optional<Venta> optional = ventaRepository.findById(venta.getId());
            if (optional.isPresent()) {
                for (VentaDetalle ventaDetalle : venta.getVentaDetalle()) {
                    long id = ventaDetalle.getProducto().getId();
                    if (productoRepository.existsById(id)) ventaDetalle.setVenta(venta);
                    else flagProducto = false;
                }
                if (flagProducto) {
                    ventaRepository.save(venta);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Venta actualizada exitosamente");
                } else {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setMessage("El producto no existe");
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al actualizar venta: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse buscarPorId(long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Venta> optional = ventaRepository.findById(id);
            if (optional.isPresent()) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Venta encontrada exitosamente");
                response.setData(entityToDto(optional.get()));
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Venta no encontrada");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al buscar venta");
        }
        return response;
    }

    @Override
    public ServiceResponse eliminar(long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Venta> optional = ventaRepository.findById(id);
            if (optional.isPresent()) {
                ventaRepository.deleteById(id);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Venta eliminada exitosamente");
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Venta no encontrada");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al eliminar venta");
        }
        return response;
    }

    private Venta dtoToEntity(VentaDto ventaDto) {
        Cliente cliente = clienteRepository.findById(ventaDto.getClienteId()).orElse(new Cliente());
        List<VentaDetalle> listaVentaDetalle = new ArrayList<>();
        ventaDto.getVentaDetalle().forEach(ventaDetalleDto -> {
            Producto producto = productoRepository.findById(ventaDetalleDto.getProductoId()).orElse(new Producto());
            VentaDetalle ventaDetalle = new VentaDetalle(
                    ventaDetalleDto.getId(),
                    ventaDetalleDto.getCantidad(),
                    ventaDetalleDto.getPrecio(),
                    ventaDetalleDto.getTotal(),
                    ventaDetalleDto.getDescuento(),
                    ventaDetalleDto.getImpuesto(),
                    ventaDetalleDto.getComentarios(),
                    ventaDetalleDto.getMedida(),
                    producto,
                    new Venta()
            );
            listaVentaDetalle.add(ventaDetalle);
        });
        return new Venta(
                ventaDto.getId(),
                ventaDto.getTotal(),
                ventaDto.getDescuento(),
                ventaDto.getImpuesto(),
                ventaDto.getMetodo(),
                ventaDto.getEstado(),
                ventaDto.getComentarios(),
                ventaDto.getRegistro(),
                ventaDto.getActualiza(),
                cliente,
                listaVentaDetalle
        );
    }

    private VentaDto entityToDto(Venta venta) {
        List<VentaDetalleDto> listaVentaDetalleDto = new ArrayList<>();
        venta.getVentaDetalle().forEach(ventaDetalle -> {
            VentaDetalleDto ventaDetalleDto = new VentaDetalleDto(
                    ventaDetalle.getId(),
                    ventaDetalle.getCantidad(),
                    ventaDetalle.getPrecio(),
                    ventaDetalle.getTotal(),
                    ventaDetalle.getDescuento(),
                    ventaDetalle.getImpuesto(),
                    ventaDetalle.getComentarios(),
                    ventaDetalle.getMedida(),
                    ventaDetalle.getProducto().getId(),
                    ventaDetalle.getVenta().getId()
            );
            listaVentaDetalleDto.add(ventaDetalleDto);
        });
        return new VentaDto(
                venta.getId(),
                venta.getCliente().getId(),
                venta.getTotal(),
                venta.getDescuento(),
                venta.getImpuesto(),
                venta.getMetodo(),
                venta.getEstado(),
                listaVentaDetalleDto,
                venta.getComentarios(),
                venta.getRegistro(),
                venta.getActualiza()
        );
    }
}
