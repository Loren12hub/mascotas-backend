package com.example.mascotas.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<Iterable<Cliente>> findAll(){
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente, UriComponentsBuilder uriBuilder){
        Cliente create = clienteRepository.save(cliente);
        URI uri = uriBuilder.path("/cliente/{idCliente}").buildAndExpand(create.getIdCliente()).toUri();
        return ResponseEntity.created(uri).body(create);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> findById(@PathVariable Long idCliente){
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
        return clienteOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> update(@PathVariable Long idCliente, @RequestBody Cliente clienteActualizado){
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
        if (clienteOptional.isEmpty()) return ResponseEntity.notFound().build();

        Cliente clienteExistente = clienteOptional.get();
        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setApPaterno(clienteActualizado.getApPaterno());
        clienteExistente.setApMaterno(clienteActualizado.getApMaterno());
        clienteExistente.setEmail(clienteActualizado.getEmail());

        return ResponseEntity.ok(clienteRepository.save(clienteExistente));
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(@PathVariable Long idCliente){
        if (clienteRepository.existsById(idCliente)){
            clienteRepository.deleteById(idCliente);
        }
        return ResponseEntity.noContent().build();
    }
}