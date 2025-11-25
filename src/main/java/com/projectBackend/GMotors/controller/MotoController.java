package com.projectBackend.GMotors.controller;


import com.projectBackend.GMotors.model.Moto;
import com.projectBackend.GMotors.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

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
    // ACTUALIZAR MOTO
    // ======================================================
    @PutMapping("/{id}")
    public Moto actualizarMoto(@PathVariable Long id, @RequestBody Moto motoActualizada) {
        return motoService.actualizarMoto(id, motoActualizada);
    }
}