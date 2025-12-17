package com.projectBackend.GMotors.model;

import jakarta.persistence.*;

@Entity
@Table(name = "motos")
public class Moto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_moto;
	private String placa;
	private Integer anio;
	private String marca;
	private String modelo;
	
	@Column(name = "tipo_moto")
	private String tipoMoto;
	private Integer kilometraje;
	private Integer cilindraje;
	private Long id_usuario;
	
	@Column(name = "ruta_imagen_motos")
	private String ruta_imagenMotos;  
	
	// ==========================
	// CONSTRUCTORES
	// ==========================
	public Moto() {
	}

	public Moto(Long id_moto, String placa, Integer anio, String marca, String modelo, Integer kilometraje,
			Integer cilindraje, Long id_usuario, String tipoMoto, String ruta_imagenMotos) {
		super();
		this.id_moto = id_moto;
		this.placa = placa;
		this.anio = anio;
		this.marca = marca;
		this.modelo = modelo;
		this.kilometraje = kilometraje;
		this.cilindraje = cilindraje;
		this.tipoMoto = tipoMoto;
		this.id_usuario = id_usuario;
		this.ruta_imagenMotos = ruta_imagenMotos;
	}
	
	// Getters y Setters

	public Long getId_moto() {
		return id_moto;
	}

	public void setId_moto(Long id_moto) {
		this.id_moto = id_moto;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public String getTipoMoto() {
		return tipoMoto;
	}

	public void setTipoMoto(String tipoMoto) {
		this.tipoMoto = tipoMoto;
	}
	
	public String getRuta_imagenMotos() {
		return ruta_imagenMotos;
	}

	public void setRuta_imagenMotos(String ruta_imagenMotos) {
		this.ruta_imagenMotos = ruta_imagenMotos;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getKilometraje() {
		return kilometraje;
	}

	public void setKilometraje(Integer kilometraje) {
		this.kilometraje = kilometraje;
	}

	public Integer getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

}
