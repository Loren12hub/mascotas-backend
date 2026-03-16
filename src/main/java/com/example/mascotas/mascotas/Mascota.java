package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.mascotasServicios.MascotaServicio;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"cliente", "mascotaServicios"})
@Entity
@Table(name = "mascota")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMascota;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 1)
    private String sexo;

    @Column(nullable = false, length = 100)
    private String tipo;

    private byte edad;
    private boolean enPeligro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    @JsonBackReference("cliente-mascotas")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "mascotas"})
    private Cliente cliente;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, fetch = FetchType.EAGER)  // ← CAMBIADO
    @JsonManagedReference("mascota-servicios")
    private List<MascotaServicio> mascotaServicios;
}