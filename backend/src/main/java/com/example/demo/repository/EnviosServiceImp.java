package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EnvioDTO;
import com.example.demo.model.Envios;
import com.example.demo.model.Usuarios;

@Service
public class EnviosServiceImp implements EnviosService {

    @Autowired private EnviosRepository enviosRepo;
    @Autowired private UsuariosRepository usuariosRepo;

    @Override
    public List<EnvioDTO> listar() {
        List<Envios> lista = enviosRepo.findAll();
        List<EnvioDTO> dtos = new ArrayList<>();

        for (Envios e : lista) {
            EnvioDTO dto = new EnvioDTO();
            dto.setId(e.getId());
            dto.setTracking(e.getNumeroTracking());
            dto.setEstado(e.getEstado());
            
            if (e.getMetodoEnvio() != null) {
                dto.setMetodo(e.getMetodoEnvio().getNombre());
            }

            if (e.getPedido() != null) {
                dto.setPedidoId(e.getPedido().getId());
                
                // Buscar datos del cliente
                Integer userId = e.getPedido().getUsuarioId();
                Optional<Usuarios> uOpt = usuariosRepo.findById(userId);
                
                if (uOpt.isPresent()) {
                    Usuarios u = uOpt.get();
                    dto.setCliente(u.getEmail());
                    
                    // Si el usuario llenó su perfil, mostramos la dirección
                    if (u.getDetalles() != null) {
                        dto.setDireccion(u.getDetalles().getDireccion());
                    } else {
                        dto.setDireccion("Sin dirección registrada");
                    }
                }
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void actualizarEstado(Integer id, String nuevoEstado) {
        Optional<Envios> envioOpt = enviosRepo.findById(id);
        if (envioOpt.isPresent()) {
            Envios envio = envioOpt.get();
            envio.setEstado(nuevoEstado);
            enviosRepo.save(envio);
        }
    }
}