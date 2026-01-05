package com.projectBackend.GMotors.service;

import com.projectBackend.GMotors.dto.RegistroCreateDTO;
import com.projectBackend.GMotors.dto.RegistroListadoDTO;
import com.projectBackend.GMotors.model.Factura;
import com.projectBackend.GMotors.model.Moto;
import com.projectBackend.GMotors.model.Registro;
import com.projectBackend.GMotors.model.Tipo;
import com.projectBackend.GMotors.model.Usuario;
import com.projectBackend.GMotors.repository.MotoRepository;
import com.projectBackend.GMotors.repository.RegistroRepository;
import com.projectBackend.GMotors.repository.TipoRepository;
import com.projectBackend.GMotors.repository.UsuarioRepository;

import java.util.List;
import java.util.Comparator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * [BE:REG-SVC]: Ubicacion de error segun LOGs
 * BE (BackEnd)
 * REG (registro)
 * SVC (Service)
 */

@Service
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final UsuarioRepository usuarioRepository;
    private final MotoRepository motoRepository;
    private final TipoRepository tipoRepository;
    private final FacturaService facturaService;

    public RegistroService(
            RegistroRepository registroRepository,
            UsuarioRepository usuarioRepository,
            MotoRepository motoRepository,
            TipoRepository tipoRepository,
            FacturaService facturaService
    ) {
        this.registroRepository = registroRepository;
        this.usuarioRepository = usuarioRepository;
        this.motoRepository = motoRepository;
        this.tipoRepository = tipoRepository;
        this.facturaService = facturaService;
    }

    @Transactional
    public Registro crearRegistro(RegistroCreateDTO dto) {

        // 1️⃣ Validaciones de alto nivel
        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            throw new IllegalArgumentException(
                    "[BE:REG-SVC]: El registro debe contener al menos un detalle"
            );
        }

        // 2️⃣ Obtener entidades base
        Usuario cliente = usuarioRepository.findById(dto.getIdCliente())
                .orElseThrow(() ->
                        new RuntimeException("[BE:REG-SVC]:Cliente no encontrado"));

        Usuario encargado = usuarioRepository.findById(dto.getIdEncargado())
                .orElseThrow(() ->
                        new RuntimeException("[BE:REG-SVC]:Encargado no encontrado"));

        Moto moto = motoRepository.findById(dto.getIdMoto())
                .orElseThrow(() ->
                        new RuntimeException("[BE:REG-SVC]:Moto no encontrada"));

        Tipo tipo = tipoRepository.findById(dto.getIdTipo())
                .orElseThrow(() ->
                        new RuntimeException("[BE:REG-SVC]:Tipo de mantenimiento no encontrado"));

        // 3️⃣ Crear factura (delegado)
        Factura factura = facturaService.crearFactura(
                dto.getDetalles(),
                cliente.getId_usuario()
        );

        // 4️⃣ Crear registro
        Registro registro = new Registro();
        registro.setFecha(java.time.LocalDate.now());
        registro.setObservaciones(dto.getObservaciones());
        registro.setEstado(dto.getEstado());

        registro.setFactura(factura);
        registro.setCliente(cliente);
        registro.setEncargado(encargado);
        registro.setMoto(moto);
        registro.setTipo(tipo);

        // 5️⃣ Persistir registro
        return registroRepository.save(registro);
    }
    
    
    // ================= LISTAR TODOS =================
    @Transactional(readOnly = true)
    public List<RegistroListadoDTO> listarTodos() {
        return registroRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ================= LISTAR POR CLIENTE =================
    @Transactional(readOnly = true)
    public List<RegistroListadoDTO> listarPorCliente(Long idCliente) {
        return registroRepository.findByCliente_IdUsuario(idCliente)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ================= HISTORIAL DE MANTENIMIENTOS POR CLIENTE =================
    @Transactional(readOnly = true)
    public List<RegistroListadoDTO> obtenerHistorialPorCliente(Long idCliente) {
        // Validar que el cliente existe
        usuarioRepository.findById(idCliente)
                .orElseThrow(() ->
                        new RuntimeException("[BE:REG-SVC]: Cliente no encontrado"));

        return registroRepository.findByCliente_IdUsuarioOrderByFechaDesc(idCliente)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
    // ================= LISTAR POR ENCARGADO =================
    @Transactional(readOnly = true)
    public List<RegistroListadoDTO> listarPorEncargado(Long idEncargado) {
        return registroRepository.findByEncargado_IdUsuario(idEncargado)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ================= MAPEO A DTO =================
    private RegistroListadoDTO mapToDTO(Registro registro) {

        RegistroListadoDTO dto = new RegistroListadoDTO();

        dto.setIdRegistro(registro.getIdRegistro());
        dto.setFecha(registro.getFecha());
        dto.setDescripcion(registro.getObservaciones());
        dto.setEstado(registro.getEstado());

        dto.setNombreCliente(
                registro.getCliente().getNombre_completo()
        );

        dto.setMarcaMoto(
                registro.getMoto().getMarca()
        );

        dto.setModeloMoto(
                registro.getMoto().getModelo()
        );

        dto.setRutaImagenMoto(
                registro.getMoto().getRutaImagenMotos()
        );

        dto.setTipoMantenimiento(
                registro.getTipo().getNombre()
        );

        return dto;
    }
    
    public RegistroListadoDTO obtenerDetalle(Long idRegistro) {

        Registro registro = registroRepository.findById(idRegistro)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        return mapToDTO(registro);
    }

}
