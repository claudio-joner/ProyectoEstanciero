package com.example.demo.Repositories;

import com.example.demo.Entities.JugadorEntity;
import com.example.demo.Entities.PartidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadorRepositoryJpa extends JpaRepository<JugadorEntity,Long> {
List<JugadorEntity> findByPartidaOrderByTurnoDesc(PartidaEntity partida);
}
