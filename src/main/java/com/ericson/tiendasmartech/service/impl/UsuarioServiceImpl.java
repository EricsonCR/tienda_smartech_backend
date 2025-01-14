package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.dto.RolDto;
import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.Rol;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.RolRepository;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Override
    @Transactional
    public ServiceResponse registrar(Usuario usuario) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());
            Optional<Usuario> usuarioNumero = usuarioRepository.findByNumero(usuario.getNumero());
            if (usuarioEmail.isPresent()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El email ya existe");
                return response;
            }
            if (usuarioNumero.isPresent()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El numero de documento ya existe");
                return response;
            }

            Optional<Rol> rolExistente = rolRepository.findByNombre(usuario.getRol().getNombre());
            if (rolExistente.isPresent()) usuario.setRol(rolExistente.get());
            else rolRepository.save(usuario.getRol());

            usuarioRepository.save(usuario);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Usuario registrado correctamente");
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al registrar el usuario");
        }
        return response;
    }

    @Override
    public ServiceResponse listar() {
        ServiceResponse response = new ServiceResponse();
        List<UsuarioDto> lista = new ArrayList<>();
        try {
            for (Usuario usuario : usuarioRepository.findAll()) {
                UsuarioDto usuarioDto = new UsuarioDto(
                        usuario.getId(),
                        usuario.getDocumento(),
                        usuario.getNumero(),
                        usuario.getNombres(),
                        usuario.getApellidos(),
                        new RolDto(usuario.getRol().getId(), usuario.getRol().getNombre()),
                        usuario.getTelefono(),
                        usuario.getDireccion(),
                        usuario.getNacimiento());
                lista.add(usuarioDto);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Usuarios listados correctamente");
            response.setData(lista);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al listar el usuario");
        }
        return response;
    }

    @Override
    public ServiceResponse actualizar(Usuario usuario) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());
            Optional<Rol> rolExistente = rolRepository.findByNombre(usuario.getRol().getNombre());
            if (usuarioEmail.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El email no existe");
                return response;
            }
            if (rolExistente.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El rol no existe");
                return response;
            }

            usuario.setRol(rolExistente.get());
            usuarioRepository.save(usuario);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Usuario actualizado correctamente");
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al actualizar el usuario");
        }
        return response;
    }

    @Override
    public ServiceResponse buscarPorId(long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Usuario> optional = usuarioRepository.findById(id);
            if (optional.isPresent()) {
                Usuario usuario = optional.get();
                UsuarioDto usuarioDto = new UsuarioDto(
                        usuario.getId(),
                        usuario.getDocumento(),
                        usuario.getNumero(),
                        usuario.getNombres(),
                        usuario.getApellidos(),
                        new RolDto(usuario.getRol().getId(), usuario.getRol().getNombre()),
                        usuario.getTelefono(),
                        usuario.getDireccion(),
                        usuario.getNacimiento()
                );
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Usuario encontrado correctamente");
                response.setData(usuarioDto);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El usuario no existe");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al buscar el usuario");
        }
        return response;
    }

    @Override
    public ServiceResponse eliminar(long id) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Usuario> optional = usuarioRepository.findById(id);
            if (optional.isPresent()) {
                Usuario usuario = optional.get();
                usuarioRepository.delete(usuario);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Usuario eliminado correctamente");
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El usuario no existe");
            }

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al eliminar el usuario");
        }
        return response;
    }

}
