package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.dto.AuthDto;
import com.ericson.tiendasmartech.dto.UserDetailsDto;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.enums.Rol;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.AuthService;
import com.ericson.tiendasmartech.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ServiceResponse login(AuthDto authDto) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (!usuarioRepository.existsByEmail(authDto.getEmail())) {
                response.setStatus(HttpStatus.CONFLICT.value());
                response.setMessage("Email no encontrado");
                return response;
            }
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword());
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDetailsDto userDetailsDto = new UserDetailsDto(authDto.getEmail(), authDto.getPassword());
            String token = jwtService.getToken(userDetailsDto);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Usuario autenticado exitosamente");
            response.setData(token);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al autenticar el usuario");
        }
        return response;
    }

    @Override
    public ServiceResponse register(AuthDto authDto) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (usuarioRepository.existsByEmail(authDto.getEmail())) {
                response.setStatus(HttpStatus.CONFLICT.value());
                response.setMessage("Email ya registrado");
                return response;
            }
            if (usuarioRepository.existsByNumero(authDto.getNumero())) {
                response.setStatus(HttpStatus.CONFLICT.value());
                response.setMessage("Numero ya registrado");
                return response;
            }
            Usuario usuario = dtoToEntity(authDto);
            usuario.setPassword(passwordEncoder.encode(authDto.getPassword()));
            usuarioRepository.save(usuario);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Usuario registrado exitosamente");
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al registrar el usuario");
        }
        return response;
    }

    private Usuario dtoToEntity(AuthDto authDto) {
        return new Usuario(
                0,
                authDto.getDocumento(),
                authDto.getNumero(),
                Rol.CLIENTE,
                authDto.getNombres(),
                authDto.getApellidos(),
                authDto.getEmail(),
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );
    }
}
