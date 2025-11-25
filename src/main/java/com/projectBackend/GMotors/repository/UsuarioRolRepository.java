package com.projectBackend.GMotors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projectBackend.GMotors.model.UsuarioRol;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRol.UsuarioRolId> {

}

/*
 * ¿Por qué JpaRepository<UsuarioRol, UsuarioRol>?

Porque:

El primer UsuarioRol es el modelo.

El segundo UsuarioRol es la clave primaria compuesta, 
que es la misma clase (como lo definiste con @IdClass(UsuarioRol.class)).
 */
