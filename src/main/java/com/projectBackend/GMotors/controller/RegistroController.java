package com.projectBackend.GMotors.controller;

import com.projectBackend.GMotors.dto.RegistroCreateDTO;
import com.projectBackend.GMotors.dto.RegistroListadoDTO;
import com.projectBackend.GMotors.model.Registro;
import com.projectBackend.GMotors.service.RegistroService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }
    
    // ✅ Crear registro (factura + detalles incluidos)
    @PostMapping
    public ResponseEntity<?> crearRegistro(
            @RequestBody RegistroCreateDTO dto
    ) {
        try {
            Registro registro = registroService.crearRegistro(dto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(registro);

        } catch (IllegalArgumentException e) {
            // Errores de validación de negocio
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (RuntimeException e) {
            // Entidades no encontradas u otros errores controlados
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (Exception e) {
            // Error inesperado
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el registro");
        }
    }
    
    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody Map<String, Object> body) {
        System.out.println("BODY = " + body);
        return ResponseEntity.ok(body);
    }

    
  //ControllerREG
 // ================= LISTAR TODOS =================
     @GetMapping
     public ResponseEntity<List<RegistroListadoDTO>> listarTodos() {
         return ResponseEntity.ok(registroService.listarTodos());
     }

     // ================= LISTAR POR CLIENTE =================
     @GetMapping("/cliente/{idCliente}")
     public ResponseEntity<List<RegistroListadoDTO>> listarPorCliente(
             @PathVariable Long idCliente
     ) {
         return ResponseEntity.ok(
                 registroService.listarPorCliente(idCliente)
         );
     }

     // ================= LISTAR POR ENCARGADO =================
     @GetMapping("/encargado/{idEncargado}")
     public ResponseEntity<List<RegistroListadoDTO>> listarPorEncargado(
             @PathVariable Long idEncargado
     ) {
         return ResponseEntity.ok(
                 registroService.listarPorEncargado(idEncargado)
         );
     }
     
     @GetMapping("/{id}")
     public RegistroListadoDTO obtenerDetalle(@PathVariable Long id) {
         return registroService.obtenerDetalle(id);
     }
     

}
