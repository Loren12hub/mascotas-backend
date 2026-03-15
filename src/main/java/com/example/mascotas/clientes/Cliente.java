package com.example.mascotas.clientes;

import com.example.mascotas.direccion.Direccion;
import com.example.mascotas.mascotas.Mascota;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"direccion", "mascotas"})
@Entity
@Table(name = "cliente")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apPaterno;

    @Column(nullable = false, length = 50)
    private String apMaterno;

    @Column(nullable = false, length = 50)
    private String email;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonManagedReference("cliente-direccion")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cliente"})  // ← AGREGAR ESTO
    private Direccion direccion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("cliente-mascotas")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cliente"})  // ← AGREGAR ESTO
    private List<Mascota> mascotas = new ArrayList<>();
}