package com.ericson.tiendasmartech.repository;

import com.ericson.tiendasmartech.entity.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Long> {
    boolean existsByVentaId(Long id);
}
