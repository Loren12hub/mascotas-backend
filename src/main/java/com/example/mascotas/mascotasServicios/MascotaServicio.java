package com.example.mascotas.mascotasServicios;

import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.servicios.Servicio;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"mascota", "servicio"})
@Entity
@Table(name = "mascota_servicio")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MascotaServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMascotasServicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(length = 180)
    private String nota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mascota")
    @JsonBackReference("mascota-servicios")
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_servicio")
    @JsonBackReference("servicio-asignaciones")
    private Servicio servicio;
}