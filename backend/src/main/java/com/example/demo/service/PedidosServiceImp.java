package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.HistorialDTO;
import com.example.demo.dto.VentaDTO;
import com.example.demo.model.DetallesPedido;
import com.example.demo.model.Envios;
import com.example.demo.model.Pagos;
import com.example.demo.model.Pedidos;
import com.example.demo.model.Productos;
import com.example.demo.repository.EnviosRepository;
import com.example.demo.repository.MetodosEnvioRepository;
import com.example.demo.repository.PagosRepository;
import com.example.demo.repository.PedidosRepository;
import com.example.demo.repository.ProductosRepository;
import com.example.demo.repository.UsuariosRepository;



@Service
public class PedidosServiceImp implements PedidosService {

    @Autowired private PedidosRepository pedidosRepo;
    @Autowired private ProductosRepository productosRepo;
    @Autowired private UsuariosRepository usuariosRepo;
    @Autowired private PagosRepository pagosRepo;
    @Autowired private EnviosRepository enviosRepo;
    @Autowired private MetodosEnvioRepository metodosEnvioRepo;

    @Override
    public List<Pedidos> listar() {
        return pedidosRepo.findAll();
    }

    // --- AQUÍ ESTÁ EL MÉTODO QUE TE FALTABA ---
    @Override
    public List<HistorialDTO> listarHistorial() {
        List<Pedidos> pedidos = pedidosRepo.findAll();
        List<HistorialDTO> historial = new ArrayList<>();

        for (Pedidos p : pedidos) {
            HistorialDTO dto = new HistorialDTO();
            dto.setIdPedido(p.getId());
            dto.setFecha(p.getFecha());
            dto.setTotal(p.getTotal());
            
            // 1. Email del Usuario
            usuariosRepo.findById(p.getUsuarioId())
                .ifPresent(u -> dto.setEmailUsuario(u.getEmail()));

            // 2. Datos de Pago
            if (p.getPago() != null) {
                dto.setMetodoPago(p.getPago().getMetodoPago());
            } else {
                dto.setMetodoPago("Pendiente");
            }

            // 3. Datos de Envío
            if (p.getEnvio() != null) {
                dto.setEstadoEnvio(p.getEnvio().getEstado());
                dto.setTracking(p.getEnvio().getNumeroTracking());
            } else {
                dto.setEstadoEnvio("No asignado");
                dto.setTracking("-");
            }

            historial.add(dto);
        }
        return historial;
    }

    // --- TU MÉTODO DE REGISTRAR VENTA (Super Transacción) ---
    @Override
    @Transactional 
    public int registrarVenta(VentaDTO dto) {
        try {
            if (!usuariosRepo.existsById(dto.getUsuarioId())) return 0;

            Pedidos pedido = new Pedidos();
            pedido.setUsuarioId(dto.getUsuarioId());
            double totalCalculado = 0.0;

            for (VentaDTO.ItemVentaDTO item : dto.getItems()) {
                Optional<Productos> prodOpt = productosRepo.findById(item.getProductoId());
                if (prodOpt.isEmpty()) return 0;

                Productos productoReal = prodOpt.get();
                
                if (productoReal.getStock() < item.getCantidad()) {
                    throw new RuntimeException("Sin stock: " + productoReal.getNombre());
                }
                productoReal.setStock(productoReal.getStock() - item.getCantidad());
                productosRepo.save(productoReal);

                DetallesPedido detalle = new DetallesPedido();
                detalle.setProducto(productoReal);
                detalle.setCantidad(item.getCantidad());
                detalle.setPrecioUnitario(item.getPrecio());
                
                totalCalculado += (item.getCantidad() * item.getPrecio());
                pedido.agregarDetalle(detalle);
            }
            
            pedido.setTotal(totalCalculado);
            Pedidos pedidoGuardado = pedidosRepo.save(pedido); 

            // Registrar Pago
            Pagos pago = new Pagos();
            pago.setPedido(pedidoGuardado);
            pago.setMetodoPago(dto.getMetodoPago()); 
            pagosRepo.save(pago);

            // Generar Envío
            Envios envio = new Envios();
            envio.setPedido(pedidoGuardado);
            envio.setEstado("PENDIENTE");
            envio.setNumeroTracking(UUID.randomUUID().toString().substring(0, 8).toUpperCase()); 
            
            if(dto.getMetodoEnvioId() != null) {
                metodosEnvioRepo.findById(dto.getMetodoEnvioId())
                    .ifPresent(envio::setMetodoEnvio);
            }
            
            enviosRepo.save(envio);

            return 1; 
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

