package com.ericson.tiendasmartech.service.impl;

import com.ericson.tiendasmartech.entity.Categoria;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.CategoriaRepository;
import com.ericson.tiendasmartech.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Override
    public ServiceResponse listar() {
        ServiceResponse response = new ServiceResponse();
        try {
            List<Categoria> lista = categoriaRepository.findAll();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Lista de categorias");
            response.setData(lista);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al listar categorias");
        }
        return response;
    }
}
