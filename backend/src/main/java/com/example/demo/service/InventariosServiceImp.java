package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.InventarioDTO;
import com.example.demo.model.Almacenes;
import com.example.demo.model.Inventarios;
import com.example.demo.model.Productos;
import com.example.demo.repository.AlmacenesRepository;
import com.example.demo.repository.InventariosRepository;
import com.example.demo.repository.ProductosRepository;

import jakarta.transaction.Transactional;

@Service
public class InventariosServiceImp implements InventariosService {

    @Autowired private InventariosRepository invRepo;
    @Autowired private ProductosRepository prodRepo;
    @Autowired private AlmacenesRepository almRepo;

    @Override
    public List<InventarioDTO> listar() {
        List<Inventarios> lista = invRepo.findAll();
        List<InventarioDTO> dtos = new ArrayList<>();
        
        for (Inventarios i : lista) {
            InventarioDTO dto = new InventarioDTO();
            dto.setId(i.getId());
            dto.setCantidad(i.getCantidad());
            
            if (i.getProducto() != null) {
                dto.setProductoId(i.getProducto().getId());
                dto.setProductoNombre(i.getProducto().getNombre());
            }
            
            if (i.getAlmacen() != null) {
                dto.setAlmacenId(i.getAlmacen().getId());
                dto.setAlmacenNombre(i.getAlmacen().getNombre());
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    @Transactional // Importante porque modificamos 2 tablas (Inventarios y Productos)
    public int guardar(InventarioDTO dto) {
        try {
            // 1. Validar existencias
            Optional<Productos> prodOpt = prodRepo.findById(dto.getProductoId());
            Optional<Almacenes> almOpt = almRepo.findById(dto.getAlmacenId());

            if (prodOpt.isEmpty() || almOpt.isEmpty()) return 0;

            Productos producto = prodOpt.get();
            Almacenes almacen = almOpt.get();

            // 2. Buscar si ya existe este inventario
            Optional<Inventarios> existente = invRepo.findByProductoIdAndAlmacenId(producto.getId(), almacen.getId());
            
            Inventarios inventario;
            int cantidadAnterior = 0;

            if (existente.isPresent()) {
                // Actualizar existente
                inventario = existente.get();
                cantidadAnterior = inventario.getCantidad(); // Guardamos lo que había antes
                inventario.setCantidad(dto.getCantidad()); // Ponemos lo nuevo
            } else {
                // Crear nuevo registro
                inventario = new Inventarios();
                inventario.setProducto(producto);
                inventario.setAlmacen(almacen);
                inventario.setCantidad(dto.getCantidad());
            }

            // 3. Guardar en tabla Inventarios
            invRepo.save(inventario);

            // 4. LÓGICA DE NEGOCIO: Actualizar Stock Global del Producto
            // (Sumamos la diferencia al stock total)
            int diferencia = dto.getCantidad() - cantidadAnterior;
            // Ojo: Esto es una simplificación. Lo ideal es recalcular todo, pero esto funciona para sumar stock.
            // Si quieres que el stock global sea la suma de todos los almacenes, haríamos un SUM().
            
            // Opción simple: Actualizar stock global sumando lo nuevo
            if (producto.getStock() == null) producto.setStock(0);
            producto.setStock(producto.getStock() + diferencia);
            prodRepo.save(producto);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void eliminar(Integer id) {
        invRepo.deleteById(id);
    }
}
