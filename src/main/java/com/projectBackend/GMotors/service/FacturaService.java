package com.projectBackend.GMotors.service;

import com.projectBackend.GMotors.model.Factura;
import com.projectBackend.GMotors.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    // Listar todas las facturas
    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    // Buscar factura por ID
    public Optional<Factura> findById(Long id) {
        return facturaRepository.findById(id);
    }

    // Crear o actualizar factura
    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    // Eliminar factura por ID
    public void deleteById(Long id) {
        facturaRepository.deleteById(id);
    }
}
