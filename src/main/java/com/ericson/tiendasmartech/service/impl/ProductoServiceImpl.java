package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.dto.CategoriaDto;
import com.ericson.tiendasmartech.dto.ProductoDto;
import com.ericson.tiendasmartech.entity.Categoria;
import com.ericson.tiendasmartech.entity.Producto;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.CategoriaRepository;
import com.ericson.tiendasmartech.repository.ProductoRepository;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ServiceResponse listar() {
        ServiceResponse response = new ServiceResponse();
        List<ProductoDto> listaProductos = new ArrayList<>();
        try {
            List<Producto> lista = productoRepository.findAll();
            for (Producto producto : lista) {
                ProductoDto productoDto = new ProductoDto(
                        producto.getId(),
                        new CategoriaDto(producto.getCategoria().getId(), producto.getCategoria().getNombre()),
                        producto.getUsuario().getEmail(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getImagen(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.isEstado()
                );
                listaProductos.add(productoDto);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Productos listados exitosamente");
            response.setData(listaProductos);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al listar");
        }
        return response;
    }

    @Override
    public ServiceResponse registrar(Producto producto) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Producto> optionalProducto = productoRepository.findByNombre(producto.getNombre());
            if (optionalProducto.isPresent()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El producto ya existe");
                return response;
            }

            Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(producto.getUsuario().getEmail());
            if (optionalUsuario.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El usuario no existe");
                return response;
            } else producto.setUsuario(optionalUsuario.get());

            Optional<Categoria> optionalCategoria = categoriaRepository.findByNombre(producto.getCategoria().getNombre());
            if (optionalCategoria.isEmpty()) categoriaRepository.save(producto.getCategoria());
            else producto.setCategoria(optionalCategoria.get());

            productoRepository.save(producto);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Producto creado exitosamente");
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al registrar");
        }
        return response;
    }

    @Override
    public ServiceResponse actualizar(Producto producto) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Producto> optionalProducto = productoRepository.findById(producto.getId());
            if (optionalProducto.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El producto no existe");
                return response;
            }

            Optional<Categoria> optionalCategoria = categoriaRepository.findByNombre(producto.getCategoria().getNombre());
            if (optionalCategoria.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("La categoria no existe");
                return response;
            }

            Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(producto.getUsuario().getEmail());
            if (optionalUsuario.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El usuario no existe");
                return response;
            }

            producto.setUsuario(optionalUsuario.get());
            producto.setCategoria(optionalCategoria.get());
            productoRepository.save(producto);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Producto actualizado exitosamente");

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al actualizar");
        }
        return response;
    }

    @Override
    public ServiceResponse eliminar(Long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Producto> optionalProducto = productoRepository.findById(id);
            if (optionalProducto.isPresent()) {
                productoRepository.deleteById(id);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Producto eliminado exitosamente");
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El producto no existe");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al eliminar");
        }
        return response;
    }

    @Override
    public ServiceResponse buscarPorId(Long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Producto> optional = productoRepository.findById(id);
            if (optional.isPresent()) {
                Producto producto = optional.get();
                ProductoDto productoDto = new ProductoDto(
                        producto.getId(),
                        new CategoriaDto(producto.getCategoria().getId(), producto.getCategoria().getNombre()),
                        producto.getUsuario().getEmail(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getImagen(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.isEstado()
                );
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Producto encontrado exitosamente");
                response.setData(productoDto);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El producto no existe");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al buscar");
        }
        return response;
    }
}
