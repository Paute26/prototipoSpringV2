package com.projectBackend.GMotors.controller;

import com.projectBackend.GMotors.dto.UsuarioRolDTO;
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
//@CrossOrigin(origins = "*")
public class UsuarioRolController {

    @Autowired
    private UsuarioRolService usuarioRolService;

    // ====================================================
    // CREAR relación
    // ====================================================
    @PostMapping
    public ResponseEntity<UsuarioRol> crear(@RequestBody UsuarioRol usuarioRol) {
        return new ResponseEntity<>(usuarioRolService.crearRelacion(usuarioRol), HttpStatus.CREATED);
    }

    // ====================================================
    // OBTENER relación (sin importar estado)
    // ====================================================
    @GetMapping("/{idUsuario}/{idRol}")
    public ResponseEntity<UsuarioRol> obtenerPorId(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idRol) {

        Optional<UsuarioRol> relacion = usuarioRolService.buscarPorId(idUsuario, idRol);
        return relacion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ====================================================
    // LISTAR todas
    // ====================================================
    @GetMapping
    public ResponseEntity<List<UsuarioRol>> listarTodas() {
        return ResponseEntity.ok(usuarioRolService.listarTodas());
    }

    // ====================================================
    // ELIMINAR relación (solo si quieres borrar físicamente)
    // ====================================================
    @DeleteMapping("/{idUsuario}/{idRol}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idRol) {

        try {
            usuarioRolService.eliminar(idUsuario, idRol);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ====================================================
    // DESACTIVAR relación
    // ====================================================
    @PutMapping("/{idUsuario}/{idRol}/desactivar")
    public ResponseEntity<UsuarioRol> desactivar(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idRol) {

        Optional<UsuarioRol> result = usuarioRolService.desactivarRelacion(idUsuario, idRol);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ====================================================
    // ACTIVAR relación
    // ====================================================
    @PutMapping("/{idUsuario}/{idRol}/activar")
    public ResponseEntity<UsuarioRol> activar(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idRol) {

        Optional<UsuarioRol> result = usuarioRolService.activarRelacion(idUsuario, idRol);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ====================================================
    // LISTAR roles por usuario (todos)
    // ====================================================
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<UsuarioRol>> obtenerRolesPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(usuarioRolService.listarRolesPorUsuario(idUsuario));
    }

    // ====================================================
    // LISTAR roles ACTIVOS por usuario
    // ====================================================
    @GetMapping("/usuario/{idUsuario}/activos")
    public ResponseEntity<List<UsuarioRol>> obtenerRolesActivosPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(usuarioRolService.listarRolesActivosPorUsuario(idUsuario));
    }

    // ====================================================
    // LISTAR usuarios por rol (todos)
    // ====================================================
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<UsuarioRol>> obtenerUsuariosPorRol(@PathVariable Integer idRol) {
        return ResponseEntity.ok(usuarioRolService.listarUsuariosPorRol(idRol));
    }

    // ====================================================
    // LISTAR usuarios ACTIVOS por rol
    // ====================================================
    @GetMapping("/rol/{idRol}/activos")
    public ResponseEntity<List<UsuarioRol>> obtenerUsuariosActivosPorRol(@PathVariable Integer idRol) {
        return ResponseEntity.ok(usuarioRolService.listarUsuariosActivosPorRol(idRol));
    }

    // ====================================================
    // LISTAR relaciones ACTIVAS
    // ====================================================
    @GetMapping("/activos")
    public ResponseEntity<List<UsuarioRol>> listarActivos() {
        return ResponseEntity.ok(usuarioRolService.listarActivas());
    }
    

    // ====================================================
    // DTO con detalles (nombre usuario y rol)
    // ====================================================
    @GetMapping("rol/detalles")
    public ResponseEntity<List<UsuarioRolDTO>> listarConDetalles() {
        return ResponseEntity.ok(usuarioRolService.listarConDetalles());
    }

}
