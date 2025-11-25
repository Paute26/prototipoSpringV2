package com.projectBackend.GMotors.controller;

import com.projectBackend.GMotors.model.UsuarioRol;
import com.projectBackend.GMotors.service.UsuarioRolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario_rol")
public class UsuarioRolController {

    @Autowired
    private UsuarioRolService usuarioRolService;

    // 🔹 Crear relación usuario-rol
    @PostMapping
    public ResponseEntity<UsuarioRol> crear(@RequestBody UsuarioRol usuarioRol) {
        UsuarioRol nuevaRelacion = usuarioRolService.crearRelacion(usuarioRol);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRelacion);
    }

    // 🔹 Obtener una relación por id_usuario e id_rol
    @GetMapping("/{idUsuario}/{idRol}")
    public ResponseEntity<UsuarioRol> obtenerPorId(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idRol) {

        Optional<UsuarioRol> relacion = usuarioRolService.buscarPorId(idUsuario, idRol);

        return relacion.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Listar todas las relaciones
    @GetMapping
    public ResponseEntity<List<UsuarioRol>> listarTodas() {
        List<UsuarioRol> relaciones = usuarioRolService.listarTodas();
        return ResponseEntity.ok(relaciones);
    }

    // 🔹 Eliminar relación usuario-rol
    @DeleteMapping("/{idUsuario}/{idRol}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idRol) {

        try {
            usuarioRolService.eliminar(idUsuario, idRol);
            return ResponseEntity.noContent().build(); // 204 ok sin contenido
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }
    }
}
