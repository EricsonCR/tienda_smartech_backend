package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public ServiceResponse registrar(UsuarioDto usuarioDto) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuarioDto.getEmail());
            Optional<Usuario> usuarioNumero = usuarioRepository.findByNumero(usuarioDto.getNumero());
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

            Usuario usuario = dtoToEntity(usuarioDto);
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
                UsuarioDto usuarioDto = entityToDto(usuario);
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
    public ServiceResponse actualizar(UsuarioDto usuarioDto) {
        ServiceResponse response = new ServiceResponse();
        try {
            Optional<Usuario> optional = usuarioRepository.findByEmail(usuarioDto.getEmail());
            if (optional.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("El email no existe");
                return response;
            }
            Usuario usuario = dtoToEntity(usuarioDto);
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
                UsuarioDto usuarioDto = entityToDto(optional.get());
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
    public ServiceResponse buscarPorEmail(String email) {
        ServiceResponse response = new ServiceResponse();
        try {
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
            if (usuario != null) {
                System.out.println(usuario.getNombres());
                UsuarioDto usuarioDto = entityToDto(usuario);
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

    private Usuario dtoToEntity(UsuarioDto usuarioDto) {
        return new Usuario(
                usuarioDto.getId(),
                usuarioDto.getDocumento(),
                usuarioDto.getNumero(),
                usuarioDto.getRol(),
                usuarioDto.getNombres(),
                usuarioDto.getApellidos(),
                usuarioDto.getEmail(),
                usuarioDto.getTelefono(),
                usuarioDto.getDireccion(),
                "",
                usuarioDto.getNacimiento(),
                null,
                null,
                usuarioDto.isEstado(),
                usuarioDto.isVerificado()
        );
    }

    private UsuarioDto entityToDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getDocumento(),
                usuario.getNumero(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getRol(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getNacimiento(),
                usuario.isEstado(),
                usuario.isVerificado());
    }

}
