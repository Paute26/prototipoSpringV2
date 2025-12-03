package com.projectBackend.GMotors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projectBackend.GMotors.config.JwtUtil;
import com.projectBackend.GMotors.dto.AuthResponse;
import com.projectBackend.GMotors.model.Usuario;
import com.projectBackend.GMotors.service.UsuarioService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtUtil jwtUtil;

    // ✅ POST /api/usuarios → Crear
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario); // 201
    }

    // ✅ GET /api/usuarios/{id} → Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        // Tu service devuelve Optional → lo manejamos aquí
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get()); // 200 + usuario
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // ✅ PUT /api/usuarios/{id} → Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuarioActualizado) {
        
        try {
            Usuario usuarioActualizadoDB = usuarioService.actualizarUsuario(id, usuarioActualizado);
            return ResponseEntity.ok(usuarioActualizadoDB); // 200 + datos actualizados
        } catch (RuntimeException e) {
            // Captura la excepción de "no encontrado" y devuelve 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // ✅ GET /api/usuarios → Listar todos
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios); // 200 + lista (aunque esté vacía)
    }

    // ✅ DELETE /api/usuarios/{id} → Eliminar (opcional, pero útil)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            // Primero verificamos que exista (usando tu método buscarPorId)
            if (usuarioService.buscarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build(); // 404 si no existe
            }
            usuarioService.eliminarPorId(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Login - autenticación 
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioLogueado = usuarioService.login(
                usuario.getCorreo(), 
                usuario.getContrasena()
            );

            String token = jwtUtil.generarToken(usuarioLogueado.getCorreo());

            return ResponseEntity.ok(
                new AuthResponse(usuarioLogueado, token)
            );

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

}
