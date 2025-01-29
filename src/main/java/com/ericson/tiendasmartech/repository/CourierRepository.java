package com.ericson.tiendasmartech.repository;

import com.ericson.tiendasmartech.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    Optional<Courier> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
