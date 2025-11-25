package com.projectBackend.GMotors.controller;

import com.projectBackend.GMotors.model.Factura;
import com.projectBackend.GMotors.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    // LISTAR TODAS LAS FACTURAS
    @GetMapping
    public List<Factura> getAll() {
        return facturaService.findAll();
    }

    // OBTENER UNA FACTURA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Factura> getById(@PathVariable Long id) {
        return facturaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREAR FACTURA
    @PostMapping
    public Factura create(@RequestBody Factura factura) {
        return facturaService.save(factura);
    }

    // ACTUALIZAR FACTURA
    @PutMapping("/{id}")
    public ResponseEntity<Factura> update(@PathVariable Long id, @RequestBody Factura factura) {
        return facturaService.findById(id)
                .map(existing -> {
                    factura.setIdFactura(id); // asegurar que actualiza la correcta
                    return ResponseEntity.ok(facturaService.save(factura));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
