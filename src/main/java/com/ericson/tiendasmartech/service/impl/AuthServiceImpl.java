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

import java.security.SecureRandom;

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
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Email no encontrado");
                return response;
            }
            if (!usuarioRepository.existsByEmailAndVerificadoIsTrue(authDto.getEmail())) {
                response.setStatus(HttpStatus.CONFLICT.value());
                response.setMessage("Email y Password son correctos, per el usuario no verificado");
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
            response.setMessage("Error al autenticar usuario, contraseña incorrecta");
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
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al registrar el usuario");
        }
        return response;
    }

    @Override
    public ServiceResponse enviarEmailDeRegistro(String email) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (!usuarioRepository.existsByEmail(email)) {
                response.setStatus(HttpStatus.CONFLICT.value());
                response.setMessage("Email no encontrado");
                return response;
            }
            String subject = "Validacion de registro nuevo usuario";
            String body = generatedBodyDeRegistro(jwtService.getToken(email));
            if (mailService.sendEmail(new EmailDto(email, subject, body, null))) {
                response.setMessage("Email enviado exitosamente");
                response.setStatus(HttpStatus.OK.value());
            } else {
                response.setMessage("Email NO enviado");
                response.setStatus(HttpStatus.CONFLICT.value());
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al enviar el email");
        }
        return response;
    }

    @Override
    public ServiceResponse enviarEmailDeRecuperacion(String email) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (!usuarioRepository.existsByEmail(email)) {
                response.setStatus(HttpStatus.CONFLICT.value());
                response.setMessage("Email no encontrado");
                return response;
            }
            String password = passwordAleatorio();
            String subject = "Recuperacion de contraseña: nuevas credenciales";
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(new Usuario());
            String body = generatedBodyDeRecuperacion(usuario.getNombres(), password);
            if (mailService.sendEmail(new EmailDto(email, subject, body, null))) {
                usuario.setPassword(passwordEncoder.encode(password));
                usuarioRepository.save(usuario);
                response.setMessage("Email enviado exitosamente");
                response.setStatus(HttpStatus.OK.value());
            } else {
                response.setMessage("Email NO enviado");
                response.setStatus(HttpStatus.CONFLICT.value());
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al enviar el email");
        }
        return response;
    }

    @Override
    public ServiceResponse validarEmail(String token) {
        ServiceResponse response = new ServiceResponse();
        try {
            if (jwtService.expiredToken(token)) {
                String email = jwtService.getUsernameToken(token);
                if (usuarioRepository.existsByEmailAndVerificadoIsTrue(email)) {
                    response.setStatus(HttpStatus.CONFLICT.value());
                    response.setMessage("Email ya esta validado");
                    return response;
                } else if (usuarioRepository.existsByEmail(email)) {
                    Usuario usuario = usuarioRepository.findByEmail(email).orElse(new Usuario());
                    usuario.setVerificado(true);
                    usuarioRepository.save(usuario);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Usuario validado exitosamente");
                } else {
                    response.setStatus(HttpStatus.NOT_FOUND.value());
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

    private String generatedBodyDeRecuperacion(String username, String password) {
        return "Hola " + username + ".\n" +
                "Esta es tu nueva contraseña temporal: " + password + "\n" +
                "Puedes ingresar a tu cuenta temporalmente utilizando esta contraseña, " +
                "pero recuerda que debes cambiarlo por tu mayor seguridad.";
    }

    private String generatedBodyDeRegistro(String token) {
        String username = jwtService.getUsernameToken(token);
        return "Hola " + username + ", bienvenido a nuestra tienda SmarTech.\n" +
                "Puedes validar tu registro haciendo click al siguiente enlace:\n" +
                "http://localhost:8080/api/auth/validarEmail/" + token;
    }

    private String passwordAleatorio() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
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
                true,
                false
        );
    }
}
