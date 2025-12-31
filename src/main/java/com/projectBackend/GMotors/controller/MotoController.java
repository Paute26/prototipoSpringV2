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
    public ResponseEntity<Moto> crearMoto(@RequestBody Moto moto) {
        try {
            Moto motoCreada = motoService.crearMoto(moto);
            return ResponseEntity.status(HttpStatus.CREATED).body(motoCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // ======================================================
    // BUSCAR MOTO POR ID
    // ======================================================
    @GetMapping("/{id}")
    public ResponseEntity<Moto> obtenerPorId(@PathVariable Long id) {
        Optional<Moto> moto = motoService.buscarPorId(id);
        return moto.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // ======================================================
    // LISTAR TODAS LAS MOTOS
    // ======================================================
    @GetMapping
    public ResponseEntity<List<Moto>> listarTodas() {
        List<Moto> motos = motoService.listarTodas();
        return ResponseEntity.ok(motos);
    }
    
    // ======================================================
    // LISTAR MOTOS POR USUARIO
    // ======================================================
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Moto>> listarMotosPorUsuario(@PathVariable Long idUsuario) {
        List<Moto> motos = motoService.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(motos);
    }

    // ======================================================
    // ACTUALIZAR MOTO
    // ======================================================
    @PutMapping("/{id}")
    public ResponseEntity<Moto> actualizarMoto(
            @PathVariable Long id, 
            @RequestBody Moto motoActualizada) {
        try {
            Moto moto = motoService.actualizarMoto(id, motoActualizada);
            return ResponseEntity.ok(moto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // ======================================================
    // ELIMINAR MOTO
    // ======================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMoto(@PathVariable Long id) {
        try {
            motoService.eliminarMoto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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
 // BUSCAR USUARIO POR PLACA (CON OCR)
 // ======================================================
 @PostMapping("/ocr/buscar-dueno")
 public ResponseEntity<?> buscarDuenoPorPlaca(
         @RequestParam("image") MultipartFile image
 ) {
     try {
         System.out.println("Recibiendo imagen para buscar dueño...");
         //System.out.println("Tamaño: " + image.getSize() + " bytes");
         
         // Detectar placa con OCR
         String placaDetectada = flaskOcrClient.detectarPlaca(image);

         if (placaDetectada == null || placaDetectada.isBlank()) {
             System.out.println("❌ [CONTROLLER] No se detectó placa");
             return ResponseEntity.ok(Map.of(
                 "success", false,
                 "mensaje", "No se pudo detectar la placa en la imagen"
             ));
         }

         System.out.println("Placa detectada: " + placaDetectada);

         // 2Buscar moto por placa en BD
         Optional<Moto> motoOpt = motoService.buscarPorPlaca(placaDetectada);

         if (motoOpt.isEmpty()) {
             System.out.println("No se encontró vehículo con placa: " + placaDetectada);
             return ResponseEntity.ok(Map.of(
                 "success", false,
                 "placa", placaDetectada,
                 "mensaje", "No se encontró vehículo registrado con esta placa"
             ));
         }

         Moto moto = motoOpt.get();
         System.out.println("Vehículo encontrado ID: " + moto.getIdMoto());

         // Verificar que tenga usuario asociado
         if (moto.getUsuario() == null) {
             System.out.println("El vehículo no tiene usuario asociado");
             return ResponseEntity.ok(Map.of(
                 "success", false,
                 "placa", placaDetectada,
                 "mensaje", "El vehículo no tiene un dueño registrado"
             ));
         }

         // Preparar respuesta con datos del usuario y moto
         Map<String, Object> respuesta = Map.of(
             "success", true,
             "placa", placaDetectada,
             "idUsuario", moto.getId_usuario(),
             "nombreCompleto", moto.getUsuario().getNombre_completo(),
             "idMoto", moto.getIdMoto(),
             "modelo", moto.getModelo(),
             "marca", moto.getMarca()
         );

         System.out.println("Usuario encontrado: " + moto.getUsuario().getNombre_completo());
         return ResponseEntity.ok(respuesta);

     } catch (Exception e) {
         System.err.println("Error en buscar usuario:");
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body(Map.of(
                     "success", false,
                     "mensaje", "Error al procesar la solicitud: " + e.getMessage()
                 ));
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