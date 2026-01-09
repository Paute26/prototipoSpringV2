package com.projectBackend.GMotors.service;

import com.projectBackend.GMotors.dto.DetalleFacturaCreateDTO;
import com.projectBackend.GMotors.dto.DetalleFacturaDTO;
import com.projectBackend.GMotors.model.DetalleFactura;
import com.projectBackend.GMotors.model.Factura;
import com.projectBackend.GMotors.repository.DetalleFacturaRepository;
import com.projectBackend.GMotors.repository.FacturaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final DetalleFacturaRepository detalleFacturaRepository;
    private final DetalleFacturaService detalleFacturaService;

    public FacturaService(
            FacturaRepository facturaRepository,
            DetalleFacturaRepository detalleFacturaRepository,
            DetalleFacturaService detalleFacturaService
    ) {
        this.facturaRepository = facturaRepository;
        this.detalleFacturaRepository = detalleFacturaRepository;
        this.detalleFacturaService = detalleFacturaService;
    }

    @Transactional
    public Factura crearFactura(
            List<DetalleFacturaCreateDTO> detallesDTO,
            Long idUsuarioCliente
    ) {
    	//Recibe la lista de detalls y el ID del cliente
        
    	if (detallesDTO == null || detallesDTO.isEmpty()) {
            throw new IllegalArgumentException(
                    "La factura debe contener al menos un detalle"
            );
        }
        

        // 1️⃣ Crear factura base
        Factura factura = new Factura();
        factura.setCostoTotal(BigDecimal.ZERO);
        factura.setFechaEmision(LocalDate.now()); // ✅ solo fecha del Server
        factura.setIdUsuario(idUsuarioCliente); // ✅ Ya esta validado en RegistroService
        factura = facturaRepository.save(factura);

        BigDecimal totalFactura = BigDecimal.ZERO;

        // 2️⃣ Crear y guardar detalles
        for (DetalleFacturaCreateDTO dto : detallesDTO) {

            DetalleFactura detalle =
                    detalleFacturaService.crearDetalle(dto, factura);

            detalleFacturaRepository.save(detalle);

            totalFactura = totalFactura.add(detalle.getSubtotal());
        }

        // 3️⃣ Actualizar total factura
        factura.setCostoTotal(totalFactura);
        return facturaRepository.save(factura);
    }
    
    // =====================================================
    // ACTUALIZAR FACTURA
    // =====================================================
    @Transactional
    public Factura actualizarFactura(
            Long idFactura,
            List<DetalleFacturaCreateDTO> detallesDTO
    ) {
        // 1️⃣ Validar que la factura existe
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Factura no encontrada con ID: " + idFactura
                ));

        if (detallesDTO == null || detallesDTO.isEmpty()) {
            throw new IllegalArgumentException(
                    "La factura debe contener al menos un detalle"
            );
        }

        // 2️⃣ Eliminar detalles antiguos
        detalleFacturaRepository.deleteByIdFactura(idFactura);

        BigDecimal totalFactura = BigDecimal.ZERO;

        // 3️⃣ Crear nuevos detalles
        for (DetalleFacturaCreateDTO dto : detallesDTO) {
            DetalleFactura detalle =
                    detalleFacturaService.crearDetalle(dto, factura);
            detalleFacturaRepository.save(detalle);
            totalFactura = totalFactura.add(detalle.getSubtotal());
        }

        // 4️⃣ Actualizar total factura
        factura.setCostoTotal(totalFactura);
        factura = facturaRepository.save(factura);  // ✅ ASEGÚRATE DE GUARDAR
        
        System.out.println("✅ Factura actualizada: " + factura.getIdFactura() + " → Total: " + totalFactura);
        
        return factura;
    }
    
    
    public List<DetalleFacturaDTO> obtenerDetallesPorFactura(Long idFactura) {
        List<DetalleFactura> detalles = detalleFacturaRepository.findByIdFactura(idFactura);
        return DetalleFacturaDTO.mapToDTOList(detalles);
    }
    
    // =====================================================
    // OBTENER FACTURA POR ID
    // =====================================================
    public Optional<Factura> obtenerFacturaPorId(Long idFactura) {
        return facturaRepository.findById(idFactura);
    }

    // =====================================================
    // OBTENER FACTURAS POR USUARIO
    // =====================================================
    public List<Factura> obtenerFacturasPorUsuario(Long idUsuario) {
        return facturaRepository.findByIdUsuario(idUsuario);
    }

    // =====================================================
    // ELIMINAR FACTURA
    // =====================================================
    @Transactional
    public void eliminarFactura(Long idFactura) {
        // Verificar que existe
        if (!facturaRepository.existsById(idFactura)) {
            throw new IllegalArgumentException(
                    "Factura no encontrada con ID: " + idFactura
            );
        }

        // Eliminar detalles primero
        detalleFacturaRepository.deleteByIdFactura(idFactura);

        // Eliminar factura
        facturaRepository.deleteById(idFactura);
    }
}
