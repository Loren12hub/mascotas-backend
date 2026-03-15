package com.example.mascotas.mascotasServicios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MascotaServicioRepository extends CrudRepository<MascotaServicio, Long> {

    // Buscar todas las asignaciones por ID de mascota
    List<MascotaServicio> findByMascotaIdMascota(Long idMascota);

    // Buscar todas las asignaciones por ID de servicio
    List<MascotaServicio> findByServicioIdServicio(Long idServicio);

    // También puedes agregar otros métodos útiles:
    // List<MascotaServicio> findByMascotaIdMascotaOrderByFechaDesc(Long idMascota);
}