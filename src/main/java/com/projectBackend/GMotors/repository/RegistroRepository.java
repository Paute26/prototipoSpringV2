package com.projectBackend.GMotors.repository;

import com.projectBackend.GMotors.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroRepository extends JpaRepository<Registro, Long> {

    List<Registro> findByCliente_IdUsuario(Long idCliente);

    List<Registro> findByEncargado_IdUsuario(Long idEncargado);

    List<Registro> findByEstado(Integer estado);
}
