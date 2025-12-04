package com.projectBackend.GMotors.service;

import com.projectBackend.GMotors.dto.UsuarioRolDTO;
import com.projectBackend.GMotors.model.UsuarioRol;
import com.projectBackend.GMotors.model.UsuarioRolId;
import com.projectBackend.GMotors.repository.UsuarioRolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioRolService {

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    // ---------------------------------------------------
    // 🔹 Crear una relación usuario–rol (ACTUALIZADO)
    // ---------------------------------------------------
    public UsuarioRol crearRelacion(UsuarioRol usuarioRol) {

        // poner estado y timestamps
        usuarioRol.setEstado(1);
        usuarioRol.setFechaModificacion(LocalDateTime.now());

        return usuarioRolRepository.save(usuarioRol);
    }

    // ---------------------------------------------------
    // 🔹 Buscar por idUsuario + idRol (sin importar estado)
    // ---------------------------------------------------
    public Optional<UsuarioRol> buscarPorId(Integer idUsuario, Integer idRol) {
        return usuarioRolRepository.findByIdUsuarioAndIdRol(idUsuario, idRol);
    }

    // ---------------------------------------------------
    // 🔹 Listar TODAS las relaciones (activas e inactivas)
    // ---------------------------------------------------
    public List<UsuarioRol> listarTodas() {
        return usuarioRolRepository.findAll();
    }

    // ---------------------------------------------------
    // 🔹 Eliminar relación usuario–rol (borrado físico)
    // ---------------------------------------------------
    public void eliminar(Integer idUsuario, Integer idRol) {

        UsuarioRolId pk = new UsuarioRolId(idUsuario, idRol);

        if (usuarioRolRepository.existsById(pk)) {
            usuarioRolRepository.deleteById(pk);
        } else {
            throw new RuntimeException("La relación usuario-rol no existe");
        }
    }

    // ---------------------------------------------------
    // 🔹 Obtener roles de un usuario (ACTIVAS E INACTIVAS)
    // ---------------------------------------------------
    public List<UsuarioRol> listarRolesPorUsuario(Integer idUsuario) {
        return usuarioRolRepository.findByIdUsuario(idUsuario);
    }

    // 🔹 SOLO roles activos de un usuario
    public List<UsuarioRol> listarRolesActivosPorUsuario(Integer idUsuario) {
        return usuarioRolRepository.findByIdUsuarioAndEstado(idUsuario, 1);
    }

    // ---------------------------------------------------
    // 🔹 Obtener usuarios de un rol (ACTIVAS E INACTIVAS)
    // ---------------------------------------------------
    public List<UsuarioRol> listarUsuariosPorRol(Integer idRol) {
        return usuarioRolRepository.findByIdRol(idRol);
    }

    // 🔹 SOLO usuarios activos del rol
    public List<UsuarioRol> listarUsuariosActivosPorRol(Integer idRol) {
        return usuarioRolRepository.findByIdRolAndEstado(idRol, 1);
    }

    // ---------------------------------------------------
    // 🔹 Listar SOLO relaciones activas
    // ---------------------------------------------------
    public List<UsuarioRol> listarActivas() {
        return usuarioRolRepository.findByEstado(1);
    }

    // ---------------------------------------------------
    // 🔹 Listar con detalles (DTO) — SOLO ACTIVAS
    // ---------------------------------------------------
    @Transactional(readOnly = true)
    public List<UsuarioRolDTO> listarConDetalles() {

        return usuarioRolRepository.findByEstado(1).stream()
                .map(rel -> new UsuarioRolDTO(
                        rel.getIdUsuario(),
                        rel.getUsuario() != null ? rel.getUsuario().getNombre_usuario() : null,
                        rel.getIdRol(),
                        rel.getRol() != null ? rel.getRol().getNombre() : null
                ))
                .toList();
    }
 // ---------------------------------------------------
 // 🔹 DESACTIVAR relación (estado = 0)
 // ---------------------------------------------------
 @Transactional
 public Optional<UsuarioRol> desactivarRelacion(Integer idUsuario, Integer idRol) {

     Optional<UsuarioRol> relacion = usuarioRolRepository.findByIdUsuarioAndIdRol(idUsuario, idRol);

     if (relacion.isPresent()) {
         UsuarioRol r = relacion.get();
         r.setEstado(0);
         r.setFechaModificacion(LocalDateTime.now());
         usuarioRolRepository.save(r);
     }

     return relacion;
 }

 // ---------------------------------------------------
 // 🔹 ACTIVAR relación (estado = 1)
 // ---------------------------------------------------
 @Transactional
 public Optional<UsuarioRol> activarRelacion(Integer idUsuario, Integer idRol) {

     Optional<UsuarioRol> relacion = usuarioRolRepository.findByIdUsuarioAndIdRol(idUsuario, idRol);

     if (relacion.isPresent()) {
         UsuarioRol r = relacion.get();
         r.setEstado(1);
         r.setFechaModificacion(LocalDateTime.now());
         usuarioRolRepository.save(r);
     }

     return relacion;
 }

}
