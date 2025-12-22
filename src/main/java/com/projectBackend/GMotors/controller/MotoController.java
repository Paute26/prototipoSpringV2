package com.projectBackend.GMotors.controller;


import com.projectBackend.GMotors.config.FlaskOcrClient;
import com.projectBackend.GMotors.model.Moto;
import com.projectBackend.GMotors.service.MotoService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;
    
    @Autowired
    private FlaskOcrClient flaskOcrClient;

    // ======================================================
    // CREAR MOTO
    // ======================================================
    @PostMapping
    public Moto crearMoto(@RequestBody Moto moto) {
        return motoService.crearMoto(moto);
    }

    // ======================================================
    // BUSCAR MOTO POR ID
    // ======================================================
    @GetMapping("/{id}")
    public Optional<Moto> obtenerPorId(@PathVariable Long id) {
        return motoService.buscarPorId(id);
    }

    // ======================================================
    // LISTAR TODAS LAS MOTOS
    // ======================================================
    @GetMapping
    public List<Moto> listarTodas() {
        return motoService.listarTodas();
    }
    
    // ======================================================
    // LISTAR MOTOS POR USUARIO
    // ======================================================
    
    @GetMapping("/usuario/{idUsuario}")
    public List<Moto> listarMotosPorUsuario(@PathVariable Long idUsuario) {
        return motoService.listarPorUsuario(idUsuario);
    }

    // ======================================================
    // ACTUALIZAR MOTO
    // ======================================================
    @PutMapping("/{id}")
    public Moto actualizarMoto(@PathVariable Long id, @RequestBody Moto motoActualizada) {
        return motoService.actualizarMoto(id, motoActualizada);
    }
    
 // ======================================================
    // DETECTAR PLACA CON OCR 
    // ======================================================
    
    @PostMapping("/ocr/placa")
    public ResponseEntity<Map<String, String>> detectarPlaca(
            @RequestParam("image") MultipartFile image
    ) {
        try {
            System.out.println("[CONTROLLER] Recibiendo imagen para OCR...");
            System.out.println("[CONTROLLER] Nombre: " + image.getOriginalFilename());
            System.out.println("[CONTROLLER] Tamaño: " + image.getSize() + " bytes");
            
            // Consumir Flask OCR
            String placaDetectada = flaskOcrClient.detectarPlaca(image);

            if (placaDetectada == null || placaDetectada.isBlank()) {
                System.out.println("❌ [CONTROLLER] No se detectó placa");
                return ResponseEntity.ok(Map.of("placa", ""));
            }

            System.out.println("✅ [CONTROLLER] Placa detectada: " + placaDetectada);
            return ResponseEntity.ok(Map.of("placa", placaDetectada));

        } catch (Exception e) {
            System.err.println("❌ [CONTROLLER] Error en OCR:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("placa", "", "error", e.getMessage()));
        }
    }
    
    
    // ======================================================
    // ACTUALIZAR PLACA CON OCR (FLASK)
    // ======================================================
    
    @PostMapping("/{id}/ocr-placa")
    public ResponseEntity<Moto> actualizarPlacaConOCR(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            // Consumir Flask OCR
            String placaDetectada = flaskOcrClient.detectarPlaca(image);

            if (placaDetectada == null || placaDetectada.isBlank()) {
                return ResponseEntity.badRequest().build();
            }

            // Reutilizar el service existente
            Moto patch = new Moto();
            patch.setPlaca(placaDetectada);

            Moto motoActualizada = motoService.actualizarMoto(id, patch);

            return ResponseEntity.ok(motoActualizada);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // ======================================================
    // SUBIR FOTO MOTO
    // ======================================================
    @PostMapping("/upload")
    public ResponseEntity<String> subirImagen(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        try {
            // Carpeta física
            String carpeta = "C:/Users/USUARIO/Desktop/prototipoSpring/gmotors/uploads/motos/";
            Path carpetaPath = Paths.get(carpeta);

            if (!Files.exists(carpetaPath)) {
                Files.createDirectories(carpetaPath);
            }

            // Nombre del archivo
            String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path rutaArchivo = carpetaPath.resolve(nombreArchivo);

            // Guardar archivo
            Files.copy(file.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            // Detecta automáticamente host + puerto
            String baseUrl =
                    request.getScheme() + "://" +
                    request.getServerName() + ":" +
                    request.getServerPort();

            String urlImagen = baseUrl + "/images/motos/" + nombreArchivo;

            return ResponseEntity.ok(urlImagen);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen");
        }
    }
}