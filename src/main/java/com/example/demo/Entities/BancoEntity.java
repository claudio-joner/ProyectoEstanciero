package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "banco")
@Entity
public class BancoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Chacras")
    private Integer cantidadChacras;

    @Column(name = "Estancias")
    private Integer cantidadEstancias;

    @Column(name = "Propiedades")
    private Integer cantidadPropiedades;

}
