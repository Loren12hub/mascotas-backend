package com.example.mascotas.mascotas;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface MascotaRepository extends CrudRepository<Mascota, Long> {

    @Query("SELECT m FROM Mascota m LEFT JOIN FETCH m.cliente WHERE m.idMascota = ?1")
    Optional<Mascota> findByIdWithCliente(Long id);

    @Query("SELECT m FROM Mascota m LEFT JOIN FETCH m.cliente")
    Iterable<Mascota> findAllWithCliente();
}