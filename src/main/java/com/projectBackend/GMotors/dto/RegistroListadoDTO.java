package com.projectBackend.GMotors.dto;

import java.time.LocalDate;

public class RegistroListadoDTO {

    // ================== IDENTIFICACIÓN ==================
    private Long idRegistro;

    // ================== CLIENTE ==================
    private String nombreCliente;

    // ================== MOTO ==================
    private String marcaMoto;
    private String modeloMoto;
    private String rutaImagenMoto;

    // ================== MANTENIMIENTO ==================
    private LocalDate fecha;
    private String descripcion;
    private String tipoMantenimiento;

    // ================== ESTADO ==================
    private Integer estado;

    // ================== GETTERS & SETTERS ==================

    public Long getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Long idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getMarcaMoto() {
        return marcaMoto;
    }

    public void setMarcaMoto(String marcaMoto) {
        this.marcaMoto = marcaMoto;
    }

    public String getModeloMoto() {
        return modeloMoto;
    }

    public void setModeloMoto(String modeloMoto) {
        this.modeloMoto = modeloMoto;
    }

    public String getRutaImagenMoto() {
        return rutaImagenMoto;
    }

    public void setRutaImagenMoto(String rutaImagenMoto) {
        this.rutaImagenMoto = rutaImagenMoto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public void setTipoMantenimiento(String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
