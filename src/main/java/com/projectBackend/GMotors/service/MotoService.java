package com.projectBackend.GMotors.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectBackend.GMotors.model.Moto;
import com.projectBackend.GMotors.repository.MotoRepository;

@Service
public class MotoService {

	@Autowired
	private MotoRepository motoRepository;

	// Crear moto
	public Moto crearMoto(Moto moto) {
		return motoRepository.save(moto);
	}

	// Buscar moto por ID
	public Optional<Moto> buscarPorId(Long id) {
		return motoRepository.findById(id);
	}

	// Listar todas las motos
	public List<Moto> listarTodas() {
		return motoRepository.findAll();
	}

	// Actualizar moto
	public Moto actualizarMoto(Long id, Moto motoActualizada) {
	    Moto motoDB = motoRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Moto no encontrada con ID: " + id));

	    if (motoActualizada.getPlaca() != null) {
	        motoDB.setPlaca(motoActualizada.getPlaca());
	    }
	    if (motoActualizada.getAnio() != null) {
	        motoDB.setAnio(motoActualizada.getAnio());
	    }
	    if (motoActualizada.getMarca() != null) {
	        motoDB.setMarca(motoActualizada.getMarca());
	    }
	    if (motoActualizada.getModelo() != null) {
	        motoDB.setModelo(motoActualizada.getModelo());
	    }
	    if (motoActualizada.getKilometraje() != null) {
	        motoDB.setKilometraje(motoActualizada.getKilometraje());
	    }
	    if (motoActualizada.getCilindraje() != null) {
	        motoDB.setCilindraje(motoActualizada.getCilindraje());
	    }
	    if (motoActualizada.getTipoMoto() != null) {
	        motoDB.setTipoMoto(motoActualizada.getTipoMoto()); 
	    }
	    if (motoActualizada.getRuta_imagenMotos() != null) {
	        motoDB.setRuta_imagenMotos(motoActualizada.getRuta_imagenMotos());  
	    }
	    if (motoActualizada.getId_usuario() != null) {
	        motoDB.setId_usuario(motoActualizada.getId_usuario());
	    }

	    return motoRepository.save(motoDB);
	}

}
