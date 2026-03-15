package com.example.mascotas.mascotasServicios;

import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.mascotas.MascotaRepository;
import com.example.mascotas.servicios.Servicio;
import com.example.mascotas.servicios.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/mascotaservicio")
@CrossOrigin(origins = "http://localhost:5173")
public class MascotaServicioController {

    @Autowired
    private MascotaServicioRepository mascotaServicioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    // LISTAR todas las asignaciones
    @GetMapping
    public ResponseEntity<Iterable<MascotaServicio>> findAll() {
        return ResponseEntity.ok(mascotaServicioRepository.findAll());
    }

    // VER asignación por ID
    @GetMapping("/{id}")
    public ResponseEntity<MascotaServicio> findById(@PathVariable Long id) {
        return mascotaServicioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // VER historial por mascota (NUEVO MÉTODO)
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<Iterable<MascotaServicio>> findByMascotaId(@PathVariable Long idMascota) {
        return ResponseEntity.ok(mascotaServicioRepository.findByMascotaIdMascota(idMascota));
    }

    // CREAR nueva asignación
    @PostMapping
    public ResponseEntity<MascotaServicio> create(@RequestBody MascotaServicio mascotaServicio) {
        // Verificar que la mascota existe
        Optional<Mascota> mascota = mascotaRepository.findById(mascotaServicio.getMascota().getIdMascota());
        if (mascota.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Verificar que el servicio existe
        Optional<Servicio> servicio = servicioRepository.findById(mascotaServicio.getServicio().getIdServicio());
        if (servicio.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        mascotaServicio.setMascota(mascota.get());
        mascotaServicio.setServicio(servicio.get());

        MascotaServicio saved = mascotaServicioRepository.save(mascotaServicio);
        return ResponseEntity.ok(saved);
    }

    // ACTUALIZAR asignación
    @PutMapping("/{id}")
    public ResponseEntity<MascotaServicio> update(@PathVariable Long id, @RequestBody MascotaServicio detalles) {
        return mascotaServicioRepository.findById(id).map(existente -> {
            existente.setFecha(detalles.getFecha());
            existente.setNota(detalles.getNota());
            return ResponseEntity.ok(mascotaServicioRepository.save(existente));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR asignación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!mascotaServicioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mascotaServicioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}