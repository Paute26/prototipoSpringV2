package com.projectBackend.GMotors.service;

import com.projectBackend.GMotors.model.Tipo;
import com.projectBackend.GMotors.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    // Crear un tipo
    public Tipo crearTipo(Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    // Obtener todos los tipos
    public List<Tipo> obtenerTodos() {
        return tipoRepository.findAll();
    }

    // Buscar tipo por ID
    public Optional<Tipo> obtenerPorId(Long id) {
        return tipoRepository.findById(id);
    }

    // Actualizar tipo
    public Tipo actualizarTipo(Long id, Tipo tipoActualizado) {
        Tipo tipoDB = tipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

        tipoDB.setNombre(tipoActualizado.getNombre());
        tipoDB.setDescripcion(tipoActualizado.getDescripcion());

        return tipoRepository.save(tipoDB);
    }

    // Eliminar tipo
    public void eliminarTipo(Long id) {
        tipoRepository.deleteById(id);
    }
}
