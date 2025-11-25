package com.projectBackend.GMotors.service;

import com.projectBackend.GMotors.model.DetalleFactura;
import com.projectBackend.GMotors.repository.DetalleFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleFacturaService {

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    // Crear o guardar un detalle
    public DetalleFactura save(DetalleFactura detalle) {
        return detalleFacturaRepository.save(detalle);
    }

    // Listar todos los detalles
    public List<DetalleFactura> findAll() {
        return detalleFacturaRepository.findAll();
    }

    // Buscar detalle por ID
    public Optional<DetalleFactura> findById(Long id) {
        return detalleFacturaRepository.findById(id);
    }

    // Eliminar detalle por ID
    public void deleteById(Long id) {
        detalleFacturaRepository.deleteById(id);
    }
}
