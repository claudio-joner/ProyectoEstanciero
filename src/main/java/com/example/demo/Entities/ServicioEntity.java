package com.example.demo.Entities;

import com.example.demo.Model.Enums.TipoServicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "servicios")
@Data
public class ServicioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "posicion")
    private Integer posicion;

    @ManyToOne
    @JoinColumn(name = "id_jugador", referencedColumnName = "id")
    private JugadorEntity duenio;
}
