package com.projectBackend.GMotors.service;

import com.projectBackend.GMotors.model.Producto;
import com.projectBackend.GMotors.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Obtener producto por ID
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Crear producto
    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar producto
    public Producto updateProducto(Long id, Producto producto) {
        Producto existing = productoRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setNombre(producto.getNombre());
            existing.setCosto(producto.getCosto());
            existing.setDescripcion(producto.getDescripcion());
            existing.setId_categoria(producto.getId_categoria());
            existing.setStock(producto.getStock());

            return productoRepository.save(existing);
        }
        return null;
    }

    // Eliminar producto
    public boolean deleteProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
