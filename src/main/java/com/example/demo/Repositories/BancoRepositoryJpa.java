package com.example.demo.Repositories;

import com.example.demo.Entities.BancoEntity;
import com.example.demo.Entities.JugadorEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepositoryJpa extends JpaRepository<BancoEntity,Long> {
    //BancoEntity findByIdOrderByIdIdDesc(Long id);
}
