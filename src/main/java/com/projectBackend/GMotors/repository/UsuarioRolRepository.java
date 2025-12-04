package com.projectBackend.GMotors.repository;

import com.projectBackend.GMotors.model.UsuarioRol;
import com.projectBackend.GMotors.model.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {

	// 🔹 Buscar todas las relaciones ACTIVAS por usuario
	List<UsuarioRol> findByIdUsuarioAndEstado(Integer idUsuario, Integer estado);

	// 🔹 Buscar todas las relaciones ACTIVAS por rol
	List<UsuarioRol> findByIdRolAndEstado(Integer idRol, Integer estado);

	// 🔹 Buscar relación usuario + rol sin importar estado
	Optional<UsuarioRol> findByIdUsuarioAndIdRol(Integer idUsuario, Integer idRol);

	// 🔹 Buscar relación usuario + rol SOLO si está activa
	Optional<UsuarioRol> findByIdUsuarioAndIdRolAndEstado(Integer idUsuario, Integer idRol, Integer estado);

	// 🔹 Listar TODAS las relaciones activas (útil para mostrar en el front)
	List<UsuarioRol> findByEstado(Integer estado);

	//---------
	// Buscar todas las relaciones por id_usuario
	List<UsuarioRol> findByIdUsuario(Integer idUsuario);

	// Buscar todas las relaciones por id_rol
	List<UsuarioRol> findByIdRol(Integer idRol);
}

/*
 * ¿Por qué JpaRepository<UsuarioRol, UsuarioRol>?
 * 
 * Porque:
 * 
 * El primer UsuarioRol es el modelo.
 * 
 * El segundo UsuarioRol es la clave primaria compuesta, que es la misma clase
 * (como lo definiste con @IdClass(UsuarioRol.class)).
 */
