package com.projectBackend.GMotors.model;

import jakarta.persistence.*;

@Entity
@Table(name = "motos", uniqueConstraints = @UniqueConstraint(columnNames = "placa"))
public class Moto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_moto")
	private Long idMoto;

	@Column(nullable = false, length = 20)
	private String placa;

	@Column(nullable = false)
	private Integer anio;

	@Column(nullable = false, length = 100)
	private String marca;

	@Column(nullable = false, length = 100)
	private String modelo;

	@Column(name = "tipo_moto", nullable = false, length = 255)
	private String tipoMoto;
	
	@Column(nullable = false)
	private Integer kilometraje;

	@Column(nullable = false)
	private Integer cilindraje;
	
	@Column(name = "id_usuario", nullable = false)
	private Long idUsuario;


	// ==========================
	// ✅ NUEVA COLUMNA (faltaban)
	// ==========================

	@Column(name = "ruta_imagen_motos", nullable = false, length = 255)
	private String rutaImagenMotos = "Desconocido"; // valor por defecto en Java también

	// ==========================
	// CONSTRUCTORES
	// ==========================
	public Moto() {
	}

	public Moto(Long id_moto, String placa, Integer anio, String marca, String modelo, Integer kilometraje,
			Integer cilindraje, Long id_usuario, String tipoMoto, String ruta_imagenMotos) {
		super();
		this.idMoto = id_moto;
		this.placa = placa;
		this.anio = anio;
		this.marca = marca;
		this.modelo = modelo;
		this.kilometraje = kilometraje;
		this.cilindraje = cilindraje;
		this.tipoMoto = tipoMoto;
		this.idUsuario = id_usuario;
		this.rutaImagenMotos = ruta_imagenMotos;
	}

	// ==========================
	// GETTERS & SETTERS
	// ==========================

	public Long getIdMoto() {
		return idMoto;
	}

	public void setIdMoto(Long idMoto) {
		this.idMoto = idMoto;
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

	public String getRutaImagenMotos() {
		return rutaImagenMotos;
	}

	public void setRutaImagenMotos(String rutaImagenMotos) {
		this.rutaImagenMotos = rutaImagenMotos != null ? rutaImagenMotos : "Desconocido";
	}

	public String getTipoMoto() {
		return tipoMoto;
	}

	public void setTipoMoto(String tipoMoto) {
		this.tipoMoto = tipoMoto;
	}

	public Long getId_usuario() {
		return idUsuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.idUsuario = id_usuario;
	}

	
}