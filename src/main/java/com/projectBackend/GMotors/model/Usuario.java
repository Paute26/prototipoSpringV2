package com.projectBackend.GMotors.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    private String nombre_completo;
    private String nombre_usuario;
    private String correo;
    private String contrasena;
    private String pais;
    private String ciudad;
    private String descripcion;
    private String ruta_imagen;    
    
    //COSTRUCTORES
    public Usuario() {}  

	public Usuario(Long id_usuario, String nombre_completo, String nombre_usuario, String correo, String contrasena,
			String pais, String ciudad, String descripcion, String ruta_imagen) {
		super();
		this.id_usuario = id_usuario;
		this.nombre_completo = nombre_completo;
		this.nombre_usuario = nombre_usuario;
		this.correo = correo;
		this.contrasena = contrasena;
		this.pais = pais;
		this.ciudad = ciudad;
		this.descripcion = descripcion;
		this.ruta_imagen = ruta_imagen;
	}



	// =====================
    // Getters y Setters
    // =====================

	public String getCorreo() {
		return correo;
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getPais() {return pais;}
	public void setPais(String pais) {this.pais = pais;	}

	public String getCiudad() {return ciudad;	}
	public void setCiudad(String ciudad) {this.ciudad = ciudad;	}

	public String getDescripcion() {return descripcion;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

	public String getRutaimagen() {return ruta_imagen;	}
	public void setRutaimagen(String rutaimagen) {this.ruta_imagen = rutaimagen;}
    
}
