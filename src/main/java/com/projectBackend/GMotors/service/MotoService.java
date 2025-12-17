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

		Moto motoDB = motoRepository.findById(id).orElseThrow(() -> new RuntimeException("Moto no encontrada"));

		motoDB.setPlaca(motoActualizada.getPlaca());
		motoDB.setAnio(motoActualizada.getAnio());
		motoDB.setMarca(motoActualizada.getMarca());
		motoDB.setModelo(motoActualizada.getModelo());
		motoDB.setKilometraje(motoActualizada.getKilometraje());
		motoDB.setCilindraje(motoActualizada.getCilindraje());
		motoDB.setruta_imagenMotos(motoActualizada.getruta_imagenMotos());
		motoDB.setId_usuario(motoActualizada.getId_usuario());
		return motoRepository.save(motoDB);
	}

}
