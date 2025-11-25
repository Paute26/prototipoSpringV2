package com.projectBackend.GMotors.service;

import com.projectBackend.GMotors.model.Registro;
import com.projectBackend.GMotors.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    // Crear registro
    public Registro crearRegistro(Registro registro) {
        return registroRepository.save(registro);
    }

    // Buscar registro por ID
    public Optional<Registro> buscarPorId(Long id) {
        return registroRepository.findById(id);
    }

    // Listar todos los registros
    public List<Registro> listarTodos() {
        return registroRepository.findAll();
    }

    // Actualizar registro
    public Registro actualizarRegistro(Long id, Registro registroActualizado) {

        Registro registroDB = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        registroDB.setFecha(registroActualizado.getFecha());
        registroDB.setObservaciones(registroActualizado.getObservaciones());
        registroDB.setEstado(registroActualizado.getEstado());
        registroDB.setId_factura(registroActualizado.getId_factura());
        registroDB.setId_encargado(registroActualizado.getId_encargado());
        registroDB.setId_cliente(registroActualizado.getId_cliente());
        registroDB.setId_tipo(registroActualizado.getId_tipo());

        return registroRepository.save(registroDB);
    }

    // Eliminar registro
    public void eliminarRegistro(Long id) {
        registroRepository.deleteById(id);
    }
}
