package com.projectBackend.GMotors.controller;

import com.projectBackend.GMotors.model.Registro;
import com.projectBackend.GMotors.service.RegistroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
@CrossOrigin(origins = "*")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    // ===================== LISTAR =====================
    @GetMapping
    public List<Registro> listarTodos() {
        return registroService.listarTodos();
    }

    // ===================== BUSCAR POR ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<Registro> obtenerPorId(@PathVariable Long id) {
        return registroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ===================== CREAR =====================
    @PostMapping
    public ResponseEntity<Registro> crearRegistro(@RequestBody Registro registro) {
        Registro nuevo = registroService.crearRegistro(registro);
        return ResponseEntity.ok(nuevo);
    }

    // ===================== ACTUALIZAR =====================
    @PutMapping("/{id}")
    public ResponseEntity<Registro> actualizarRegistro(
            @PathVariable Long id,
            @RequestBody Registro registroActualizado) {

        try {
            Registro actualizado = registroService.actualizarRegistro(id, registroActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ===================== ELIMINAR =====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Long id) {
        registroService.eliminarRegistro(id);
        return ResponseEntity.noContent().build();
    }
}
