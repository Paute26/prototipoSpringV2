package com.projectBackend.GMotors.controller;

import com.projectBackend.GMotors.model.DetalleFactura;
import com.projectBackend.GMotors.service.DetalleFacturaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles-factura")
public class DetalleFacturaController {

    @Autowired
    private DetalleFacturaService detalleFacturaService;

    // Listar todos los detalles
    @GetMapping
    public List<DetalleFactura> listarTodos() {
        return detalleFacturaService.findAll();
    }

    // Buscar detalle por ID
    @GetMapping("/{id}")
    public Optional<DetalleFactura> buscarPorId(@PathVariable Long id) {
        return detalleFacturaService.findById(id);
    }

    // Crear nuevo detalle
    @PostMapping
    public DetalleFactura crear(@RequestBody DetalleFactura detalleFactura) {
        return detalleFacturaService.save(detalleFactura);
    }

    // Actualizar detalle
    @PutMapping("/{id}")
    public DetalleFactura actualizar(@PathVariable Long id, @RequestBody DetalleFactura detalleFactura) {
        detalleFactura.setId_detalle(id);
        return detalleFacturaService.save(detalleFactura);
    }

    // Eliminar detalle por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        detalleFacturaService.deleteById(id);
    }
}
