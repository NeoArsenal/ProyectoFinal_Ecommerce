package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.model.Categorias;
import com.example.demo.model.MetodosEnvio;
import com.example.demo.model.Productos;
import com.example.demo.repository.CategoriasRepository;
import com.example.demo.repository.MetodosEnvioRepository;
import com.example.demo.repository.ProductosRepository;

/**
 * DataInitializer — Se ejecuta automáticamente al iniciar el backend.
 * Inserta datos de prueba en metodos_envio, categorias y productos
 * SOLO si las tablas están vacías. No duplica datos en reinicios.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private MetodosEnvioRepository metodosEnvioRepo;
    @Autowired private CategoriasRepository   categoriasRepo;
    @Autowired private ProductosRepository    productosRepo;

    @Override
    public void run(String... args) throws Exception {

        // ============================================================
        // 1. MÉTODOS DE ENVÍO
        // ============================================================
        if (metodosEnvioRepo.count() == 0) {
            MetodosEnvio express  = new MetodosEnvio();
            express.setNombre("Envío Express (1-2 días)");

            MetodosEnvio normal   = new MetodosEnvio();
            normal.setNombre("Envío Normal (3-5 días)");

            MetodosEnvio econ     = new MetodosEnvio();
            econ.setNombre("Envío Económico (7-10 días)");

            metodosEnvioRepo.saveAll(List.of(express, normal, econ));
            System.out.println("✅ [DataInitializer] Métodos de envío insertados.");
        } else {
            System.out.println("ℹ️  [DataInitializer] Métodos de envío ya existen, sin cambios.");
        }

        // ============================================================
        // 2. CATEGORÍAS
        // ============================================================
        if (categoriasRepo.count() == 0) {
            String[] nombres = { "Laptops", "Periféricos", "Monitores", "Almacenamiento", "Accesorios" };
            for (String nombre : nombres) {
                Categorias cat = new Categorias();
                cat.setNombre(nombre);
                categoriasRepo.save(cat);
            }
            System.out.println("✅ [DataInitializer] Categorías insertadas.");
        } else {
            System.out.println("ℹ️  [DataInitializer] Categorías ya existen, sin cambios.");
        }

        // ============================================================
        // 3. PRODUCTOS DE PRUEBA
        // ============================================================
        if (productosRepo.count() == 0) {
            Object[][] productos = {
                { "Laptop Gamer RTX 4060",    2499.90, 15 },
                { "Laptop Ultrabook Intel i7", 1899.90, 10 },
                { "Monitor 27\" 144Hz",        599.90,  20 },
                { "Teclado Mecánico RGB",       199.90,  30 },
                { "Mouse Gaming 12000 DPI",     129.90,  40 },
                { "SSD 1TB NVMe",               299.90,  25 },
                { "Auriculares Inalámbricos",   179.90,  18 },
                { "Webcam Full HD 1080p",        89.90,  22 },
            };

            for (Object[] data : productos) {
                Productos p = new Productos();
                p.setNombre((String) data[0]);
                p.setPrecio((Double) data[1]);
                p.setStock((Integer) data[2]);
                productosRepo.save(p);
            }
            System.out.println("✅ [DataInitializer] Productos de prueba insertados.");
        } else {
            System.out.println("ℹ️  [DataInitializer] Productos ya existen, sin cambios.");
        }

        System.out.println("🚀 [DataInitializer] Inicialización completada.");
    }
}
