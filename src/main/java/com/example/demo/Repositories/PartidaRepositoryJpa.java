package com.example.demo.Repositories;

import com.example.demo.Entities.JugadorEntity;
import com.example.demo.Entities.PartidaEntity;
import com.example.demo.Model.Enums.EstadoPartida;
import com.example.demo.Model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartidaRepositoryJpa extends JpaRepository<PartidaEntity,Long> {
  List<PartidaEntity>findPartidaEntitiesByestadoPartida(EstadoPartida estadoPartida);

//    /**
//     * Busca un partida completa, incluyendo todas las relaciones
//      * @param id Id de la partida
//     * @return Un Optional que contiene PartidaEntity
//     */
//   @Query("SELECT p FROM PartidaEntity p " +
//            "LEFT JOIN FETCH p.jugador j " +
//            "LEFT JOIN FETCH p.banco b " +
//            "LEFT JOIN FETCH j.escrituras e " +
//            "LEFT JOIN FETCH j.servicios s " +
//            "WHERE p.id = :id")
//   Optional<PartidaEntity> findPartidaByIdWithRelations(@Param("id") Long id);
}
