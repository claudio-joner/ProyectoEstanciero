package com.example.demo.services;

import com.example.demo.Model.Partida;
import com.example.demo.dtos.PartidaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PartidaService {
    Partida savePartida(Partida partida);
    Partida getPartidaById(Long idGame);
    List<PartidaDto> getAllPartidasInGame();
}
