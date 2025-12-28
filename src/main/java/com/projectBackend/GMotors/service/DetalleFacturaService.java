package com.projectBackend.GMotors.service;

import com.projectBackend.GMotors.dto.DetalleFacturaCreateDTO;
import com.projectBackend.GMotors.model.DetalleFactura;
import com.projectBackend.GMotors.model.Factura;
import com.projectBackend.GMotors.model.Producto;
import com.projectBackend.GMotors.repository.ProductoRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DetalleFacturaService {

    private final ProductoRepository productoRepository;

    public DetalleFacturaService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Crea un DetalleFactura con subtotal calculado
     */
    public DetalleFactura crearDetalle(
            DetalleFacturaCreateDTO dto,
            Factura factura
    ) {

        validarCamposBase(dto);

        DetalleFactura detalle = new DetalleFactura();
        detalle.setIdFactura(factura.getIdFactura());
        detalle.setDescripcion(dto.getDescripcion());
        detalle.setCantidad(dto.getCantidad());

        BigDecimal subtotal;

        // 1️⃣ CASO PRODUCTO
        if (dto.getIdProducto() != null) {

            Producto producto = productoRepository.findById(dto.getIdProducto())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Backend-DetallesFactS: Producto no encontrado: " + dto.getIdProducto())
                    );

            BigDecimal precioUnitario = producto.getPvp();

            subtotal = precioUnitario
                    .multiply(BigDecimal.valueOf(dto.getCantidad()))
                    .setScale(2, RoundingMode.HALF_UP);

            detalle.setId_producto(producto.getId_producto());
            // ✅ Descripción tomada del producto
            detalle.setDescripcion(producto.getDescripcion());

        }
        // 2️⃣ CASO SERVICIO
        else {

            if (dto.getPrecioUnitario() == null) {
                throw new IllegalArgumentException(
                        "Backend-DetallesFactS: PrecioUnitario es obligatorio para servicios"
                );
            }

            if (dto.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                        "Backend-DetallesFactS: El precio no puede ser negativo"
                );
            }
            if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
                throw new IllegalArgumentException("Backend-DetallesFactS: La descripción es obligatoria");
            }

            subtotal = dto.getPrecioUnitario()
                    .setScale(2, RoundingMode.HALF_UP);

            detalle.setId_producto(null);
        }

        detalle.setSubtotal(subtotal);
        return detalle;
    }

    // ---------------- VALIDACIONES ----------------

    private void validarCamposBase(DetalleFacturaCreateDTO dto) {

        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("Backend-DetallesFactS: La cantidad debe ser mayor a 0");
        }
    }
}
