package com.example.mascotas.direccion;

import com.example.mascotas.clientes.Cliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "cliente")
@Entity
@Table(name = "direccion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDireccion;

    @Column(nullable = false, length = 100)
    private String calle;

    @Column(nullable = false, length = 20)
    private String numero;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    @JsonBackReference("cliente-direccion")
    private Cliente cliente;
}