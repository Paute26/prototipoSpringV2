package com.projectBackend.GMotors.controller;

import com.projectBackend.GMotors.model.Producto;
import com.projectBackend.GMotors.service.ProductoService;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto p = productoService.getProductoById(id);
        if (p != null) return ResponseEntity.ok(p);
        return ResponseEntity.notFound().build();
    }

    // Crear
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.createProducto(producto));
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @PathVariable Long id,
            @RequestBody Producto producto
    ) {
        Producto updated = productoService.updateProducto(id, producto);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }
    

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        if (productoService.deleteProducto(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/eliminar")
    public ResponseEntity<Map<String, String>> eliminarProductos(@RequestBody List<Long> ids) {
        productoService.deleteProductos(ids);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Productos eliminados correctamente");

        return ResponseEntity.ok(response);
    }
    
    
 // ======================================================
    // SUBIR PRODUCTOS FOTO
    // ======================================================
    @PostMapping("/upload")
    public ResponseEntity<String> subirImagen(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        try {
            // Carpeta física
            String carpeta = "C:/Users/USUARIO/Desktop/prototipoSpring/gmotors/uploads/productos/";
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

            String urlImagen = baseUrl + "/images/productos/" + nombreArchivo;

            return ResponseEntity.ok(urlImagen);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen");
        }
    }
}
