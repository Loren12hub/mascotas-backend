package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.clientes.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mascota")
@CrossOrigin(origins = "http://localhost:5173")
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<ObjectNode>> findAll() {
        System.out.println("🔍 Buscando TODAS las mascotas con cliente...");

        List<Mascota> mascotas = new ArrayList<>();
        mascotaRepository.findAll().forEach(mascotas::add);

        List<ObjectNode> resultado = new ArrayList<>();

        for (Mascota m : mascotas) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("idMascota", m.getIdMascota());
            node.put("nombre", m.getNombre());
            node.put("sexo", m.getSexo());
            node.put("tipo", m.getTipo());
            node.put("edad", m.getEdad());
            node.put("enPeligro", m.isEnPeligro());

            // Agregar cliente si existe
            if (m.getCliente() != null) {
                ObjectNode clienteNode = objectMapper.createObjectNode();
                clienteNode.put("idCliente", m.getCliente().getIdCliente());
                clienteNode.put("nombre", m.getCliente().getNombre());
                clienteNode.put("apPaterno", m.getCliente().getApPaterno());
                clienteNode.put("apMaterno", m.getCliente().getApMaterno());
                clienteNode.put("email", m.getCliente().getEmail());
                node.set("cliente", clienteNode);
                System.out.println("✅ Cliente agregado: " + m.getCliente().getNombre());
            } else {
                System.out.println("⚠️ Mascota sin cliente: " + m.getNombre());
            }

            resultado.add(node);
        }

        System.out.println("📦 Enviando " + resultado.size() + " mascotas con clientes");
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> findById(@PathVariable Long id) {
        System.out.println("🔍 Buscando mascota ID: " + id);
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(id);

        if (mascotaOpt.isPresent()) {
            Mascota m = mascotaOpt.get();
            ObjectNode node = objectMapper.createObjectNode();
            node.put("idMascota", m.getIdMascota());
            node.put("nombre", m.getNombre());
            node.put("sexo", m.getSexo());
            node.put("tipo", m.getTipo());
            node.put("edad", m.getEdad());
            node.put("enPeligro", m.isEnPeligro());

            if (m.getCliente() != null) {
                ObjectNode clienteNode = objectMapper.createObjectNode();
                clienteNode.put("idCliente", m.getCliente().getIdCliente());
                clienteNode.put("nombre", m.getCliente().getNombre());
                clienteNode.put("apPaterno", m.getCliente().getApPaterno());
                clienteNode.put("apMaterno", m.getCliente().getApMaterno());
                clienteNode.put("email", m.getCliente().getEmail());
                node.set("cliente", clienteNode);
            }

            return ResponseEntity.ok(node);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Mascota> create(@RequestBody Mascota mascota) {
        Optional<Cliente> cliente = clienteRepository.findById(mascota.getCliente().getIdCliente());
        if (cliente.isEmpty()) return ResponseEntity.badRequest().build();
        mascota.setCliente(cliente.get());
        Mascota saved = mascotaRepository.save(mascota);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> update(@PathVariable Long id, @RequestBody Mascota detalles) {
        return mascotaRepository.findById(id).map(m -> {
            m.setNombre(detalles.getNombre());
            m.setSexo(detalles.getSexo());
            m.setTipo(detalles.getTipo());
            m.setEdad(detalles.getEdad());
            m.setEnPeligro(detalles.isEnPeligro());

            if (detalles.getCliente() != null && detalles.getCliente().getIdCliente() != null) {
                Optional<Cliente> nuevoCliente = clienteRepository.findById(detalles.getCliente().getIdCliente());
                nuevoCliente.ifPresent(m::setCliente);
            }

            return ResponseEntity.ok(mascotaRepository.save(m));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!mascotaRepository.existsById(id)) return ResponseEntity.notFound().build();
        mascotaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}