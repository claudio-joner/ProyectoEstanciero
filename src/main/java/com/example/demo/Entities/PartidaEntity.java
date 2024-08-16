package com.example.demo.Entities;

import com.example.demo.Model.Enums.EstadoPartida;
import com.example.demo.Model.Enums.TipoPartida;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partidas")
@Data
public class PartidaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estados")
    @Enumerated(EnumType.STRING)
    private EstadoPartida estadoPartida;

    @Column(name = "tipos")
    @Enumerated(EnumType.STRING)
    private TipoPartida tipoPartida;

    @Column(name = "fecha_Creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_Finalizacion")
    private LocalDateTime fechaFinalizacion;

    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JugadorEntity> jugador;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banco_id", referencedColumnName = "id")
    private BancoEntity banco;

    @Column(name = "Monto_Victoria")
    private int montoVictoria;

    @Column(name = "turno")
    private Integer turno;

}
