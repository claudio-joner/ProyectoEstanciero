package com.example.demo.Entities;

import com.example.demo.Model.Enums.EstadoJugador;
import com.example.demo.Model.Enums.Peon;
import com.example.demo.Model.Enums.PerfilEnum;
import com.example.demo.Model.Perfil.Perfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jugadores")
@Data
public class JugadorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "peon")
    @Enumerated(EnumType.STRING)
    private Peon peon;

    @Column(name = "perfil")
    @Enumerated(EnumType.STRING)
    private PerfilEnum perfilEnum;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoJugador estado;

    @Column(name = "turno_retenido")
    private Integer turnoRetenido;

    @Column(name = "turno")
    private Integer turno;

    @Column(name = "cuenta")
    private Integer cuenta;

    @Column(name = "es_bot")
    private boolean bot;

    @Column(name = "posicion")
    private Integer posicion;

    @OneToMany(mappedBy = "duenio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EscrituraEntity> escrituras;

    @OneToMany(mappedBy = "duenio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicioEntity> servicios;

    @ManyToOne
    @JoinColumn(name = "id_partida", referencedColumnName = "id")
    private PartidaEntity partida;

}
