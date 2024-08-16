package com.example.demo.Entities;
import com.example.demo.Model.Jugador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "escrituras")
@Data
public class EscrituraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado_hipotecado")
    private boolean estadoHipoteca;

    @Column(name = "estancias")
    private Integer cantEstancias;

    @Column(name = "posicion")
    private Integer posicion;

    @Column(name = "chacras")
    private Integer cantChacras;

    @ManyToOne
    @JoinColumn(name = "id_jugador", referencedColumnName = "id")
    private JugadorEntity duenio;

}
