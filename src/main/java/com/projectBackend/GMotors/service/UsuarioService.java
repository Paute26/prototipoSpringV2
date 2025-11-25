package com.projectBackend.GMotors.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectBackend.GMotors.model.Usuario;
import com.projectBackend.GMotors.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// METODOS

	// Crear Usuario
	public Usuario crearUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	// Modificar Usuario
	public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
		// ✅ Usa .orElseThrow() → devuelve Usuario directamente
		Usuario usuarioExistente = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));

		// Ahora sí: usuarioExistente es de tipo Usuario (no Optional)
		usuarioExistente.setNombre_completo(usuarioActualizado.getNombre_completo());
		usuarioExistente.setCorreo(usuarioActualizado.getCorreo());

		return usuarioRepository.save(usuarioExistente);
	}

	// Método para buscar por ID
	public Optional<Usuario> buscarPorId(Long id) {
		return usuarioRepository.findById(id);
	}

	// Método para listar todos
	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	// En UsuarioService.java
	public void eliminarPorId(Long id) {
		if (!usuarioRepository.existsById(id)) {
			throw new RuntimeException("Usuario con ID " + id + " no encontrado");
		}
		usuarioRepository.deleteById(id);
	}

}
