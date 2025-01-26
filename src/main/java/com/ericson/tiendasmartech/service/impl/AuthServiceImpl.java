package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.dto.AuthDto;
import com.ericson.tiendasmartech.dto.EmailDto;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.enums.Rol;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.AuthService;
import com.ericson.tiendasmartech.service.JwtService;
import com.ericson.tiendasmartech.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final UserDetailsService userDetailsService;
    private final MailService mailService;

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
            UserDetails userDetails = userDetailsService.loadUserByUsername(authDto.getEmail());
            String token = jwtService.getToken(userDetails);

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
                response.setMessage("Email ya pertenece a otro usuario");
                return response;
            }
            if (usuarioRepository.existsByNumero(authDto.getNumero())) {
                response.setStatus(HttpStatus.CONFLICT.value());
                response.setMessage("Numero ya pertenece a otro usuario");
                return response;
            }
            Usuario usuario = dtoToEntity(authDto);
            usuario.setPassword(passwordEncoder.encode(authDto.getPassword()));
            usuarioRepository.save(usuario);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Usuario registrado exitosamente");

            EmailDto emailDto = new EmailDto();
            emailDto.setToUser(usuario.getEmail());
            emailDto.setSubject("Validacion de registro nuevo usuario");
            emailDto.setBody(generatedBody(jwtService.getToken(usuario.getEmail())));
            if (mailService.sendEmail(emailDto)) {
                String mensaje = response.getMessage() + ", Email enviado exitosamente a: " + usuario.getEmail();
                response.setMessage(mensaje);
            }

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al registrar el usuario");
        }
        return response;
    }

    private String generatedBody(String token) {
        String username = jwtService.getUsernameToken(token);
        return "Hola " + username + ", bienvenido a nuestra tienda SmarTech.\n" +
                "Puedes validar tu registro haciendo click al siguiente enlace:\n" +
                "http://localhost:8080/api/auth/verifyEmail/" + token;
    }

    @Override
    public ServiceResponse validarEmail(String token) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (jwtService.expiredToken(token)) {
                String email = jwtService.getUsernameToken(token);
                if (usuarioRepository.existsByEmail(email)) {
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Usuario validado exitosamente");
                } else {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setMessage("Usuario no existe");
                }
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Token expirado");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al validar el token");
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
