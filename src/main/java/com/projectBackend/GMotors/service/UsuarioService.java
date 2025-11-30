package com.projectBackend.GMotors.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projectBackend.GMotors.model.Usuario;
import com.projectBackend.GMotors.repository.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	// METODOS

	// Crear Usuario
	public Usuario crearUsuario(Usuario usuario) {
		
        // Encriptar la contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Guardar en BD
        Usuario nuevo = usuarioRepository.save(usuario);

        // No enviar la contraseña al frontend
        nuevo.setContrasena(null);

        return nuevo;
	}

	// Modificar Usuario
	public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
		// ✅ Usa .orElseThrow() → devuelve Usuario directamente
		Usuario usuarioExistente = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));

		// Ahora sí: usuarioExistente es de tipo Usuario (no Optional)
		// Actualización de los campos
        usuarioExistente.setNombre_completo(usuarioActualizado.getNombre_completo());
        usuarioExistente.setNombre_usuario(usuarioActualizado.getNombre_usuario());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setPais(usuarioActualizado.getPais());
        usuarioExistente.setCiudad(usuarioActualizado.getCiudad());
        usuarioExistente.setDescripcion(usuarioActualizado.getDescripcion());
        usuarioExistente.setRutaimagen(usuarioActualizado.getRutaimagen());
        
     // Si envían nueva contraseña → encriptar
        if (usuarioActualizado.getContrasena() != null && !usuarioActualizado.getContrasena().isBlank()) {
            usuarioExistente.setContrasena(
                passwordEncoder.encode(usuarioActualizado.getContrasena())
            );
        }

        Usuario actualizado = usuarioRepository.save(usuarioExistente);
        actualizado.setContrasena(null);

        return actualizado;
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
      
	// Metodo para verificar usuario
    public Usuario login(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        // Comparar hash
        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // No enviar hash al frontend
        usuario.setContrasena(null);

        return usuario;
    }
}
