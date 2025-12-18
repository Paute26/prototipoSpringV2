package com.projectBackend.GMotors.repository;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectBackend.GMotors.model.Moto;
import java.util.List;


public interface MotoRepository extends JpaRepository<Moto, Long> {

    // Listar motos por usuario usando JPQL
    @Query("SELECT m FROM Moto m WHERE m.id_usuario = :idUsuario")
    List<Moto> findByUsuarioId(@Param("idUsuario") Long idUsuario);
}