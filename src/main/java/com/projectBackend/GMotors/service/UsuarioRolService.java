package com.projectBackend.GMotors.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectBackend.GMotors.model.UsuarioRol;
import com.projectBackend.GMotors.repository.UsuarioRolRepository;

@Service
public class UsuarioRolService {

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    // Crear relación usuario-rol
    public UsuarioRol crearRelacion(UsuarioRol usuarioRol) {
        return usuarioRolRepository.save(usuarioRol);
    }

    // Buscar una relación por su clave compuesta
    public Optional<UsuarioRol> buscarPorId(Integer idUsuario, Integer idRol) {
        UsuarioRol.UsuarioRolId clave = new UsuarioRol.UsuarioRolId(idUsuario, idRol);
        return usuarioRolRepository.findById(clave);
    }

    // Listar todas las relaciones
    public List<UsuarioRol> listarTodas() {
        return usuarioRolRepository.findAll();
    }

    // Eliminar relación usuario-rol
    public void eliminar(Integer idUsuario, Integer idRol) {
        UsuarioRol.UsuarioRolId clave = new UsuarioRol.UsuarioRolId(idUsuario, idRol);

        if (!usuarioRolRepository.existsById(clave)) {
            throw new RuntimeException("Relación usuario-rol no encontrada");
        }

        usuarioRolRepository.deleteById(clave);
    }
}
