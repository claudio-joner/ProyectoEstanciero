package com.example.demo.Repositories;

import com.example.demo.Entities.EscrituraEntity;
import com.example.demo.Entities.JugadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EscrituraRepositoryJpa extends JpaRepository<EscrituraEntity,Long> {
 List<EscrituraEntity> findByDuenio(JugadorEntity duenio);
}
