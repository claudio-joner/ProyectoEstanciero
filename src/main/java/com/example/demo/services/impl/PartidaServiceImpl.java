package com.example.demo.services.impl;
import com.example.demo.Entities.EscrituraEntity;
import com.example.demo.Entities.JugadorEntity;
import com.example.demo.Entities.PartidaEntity;
import com.example.demo.Entities.ServicioEntity;
import com.example.demo.Model.Enums.EstadoPartida;
import com.example.demo.Model.Escritura;
import com.example.demo.Model.Jugador;
import com.example.demo.Model.Partida;
import com.example.demo.Model.Servicio;
import com.example.demo.Repositories.EscrituraRepositoryJpa;
import com.example.demo.Repositories.JugadorRepositoryJpa;
import com.example.demo.Repositories.PartidaRepositoryJpa;
import com.example.demo.Repositories.ServicioRepositoryJpa;
import com.example.demo.dtos.PartidaDto;

import com.example.demo.services.PartidaService;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartidaServiceImpl implements PartidaService {

    private final PartidaRepositoryJpa partidaRepositoryJpa;
    private final ModelMapper modelMapper;
    private final JugadorRepositoryJpa jugadorRepositoryJpa;
    private final EscrituraRepositoryJpa escrituraRepositoryJpa;
    private final ServicioRepositoryJpa servicioRepositoryJpa;

    @Autowired
    public PartidaServiceImpl(PartidaRepositoryJpa partidaRepositoryJpa,
                              ModelMapper modelMapper,
                              JugadorRepositoryJpa jugadorRepositoryJpa,
                              ServicioRepositoryJpa servicioRepositoryJpa,
                              EscrituraRepositoryJpa escrituraRepositoryJpa) {
        this.partidaRepositoryJpa = partidaRepositoryJpa;
        this.modelMapper = modelMapper;
        this.jugadorRepositoryJpa = jugadorRepositoryJpa;
        this.servicioRepositoryJpa = servicioRepositoryJpa;
        this.escrituraRepositoryJpa = escrituraRepositoryJpa;
    }
    @Transactional
    @Override
    public Partida savePartida(Partida partida) {
        PartidaEntity partidaEntity = modelMapper.map(partida, PartidaEntity.class);
        for (JugadorEntity jugador: partidaEntity.getJugador()){
            jugador.setPartida(partidaEntity);
        }
        partidaEntity = partidaRepositoryJpa.save(partidaEntity);
        Partida partidaSaved = modelMapper.map(partidaEntity, Partida.class);
        partidaSaved.getTable().reiniciarTablero();
        for (Jugador jugador : partidaSaved.getJugador()){
            List<Escritura> escriturasModificadas = new ArrayList<>();
            List<Servicio> serviciosModificados = new ArrayList<>();
            for (Escritura escritura : jugador.getEscrituras()){
                Escritura obj =(Escritura) partidaSaved.getTable().getCasilleros().stream().filter(m-> Objects.equals(m.getPosicion(),escritura.getPosicion())).findFirst().get();
                obj.setDuenio(escritura.getDuenio());
                obj.setEstadoHipoteca(escritura.isEstadoHipoteca());
                obj.setCantEstancias(escritura.getCantEstancias());
                obj.setCantChacras(escritura.getCantChacras());
                obj.setId(escritura.getId());
                escriturasModificadas.add(obj);
            }
            jugador.setEscrituras(escriturasModificadas);
            for (Servicio servicio : jugador.getServicios()){
                Servicio obj = (Servicio) partidaSaved.getTable().getCasilleros().stream().filter(m-> Objects.equals(m.getPosicion(),servicio.getPosicion())).findFirst().get();
                obj.setDuenio(servicio.getDuenio());
                obj.setId(servicio.getId());
                serviciosModificados.add(obj);
            }
            jugador.setServicios(serviciosModificados);
        }
        return partidaSaved;
    }



    @Override
    public Partida getPartidaById(Long idGame) {
        Optional<PartidaEntity> partidaEntity = partidaRepositoryJpa.findById(idGame);
        if (partidaEntity.isEmpty()) {
            throw new RuntimeException("No se encontro la partida");
        }
        Partida partida = new Partida();
        partida.setId(partidaEntity.get().getId());
        partida.setTipoPartida(partidaEntity.get().getTipoPartida());
        partida.setEstadoPartida(partidaEntity.get().getEstadoPartida());
        partida.setFechaCreacion(partidaEntity.get().getFechaCreacion());
        partida.setMontoVictoria(partidaEntity.get().getMontoVictoria());
        partida.getTable().reiniciarTablero();
        partida.getJugador().addAll(getJugadores(partidaEntity.get()));
        partida.setTurno(partidaEntity.get().getTurno());
        for (Jugador jugador : partida.getJugador()){
            List<Escritura> escriturasModificadas = new ArrayList<>();
            List<Servicio> serviciosModificados = new ArrayList<>();
            for (Escritura escritura : jugador.getEscrituras()){
                Escritura obj =(Escritura) partida.getTable().getCasilleros().stream().filter(m-> Objects.equals(m.getPosicion(),escritura.getPosicion())).findFirst().get();
                obj.setDuenio(escritura.getDuenio());
                obj.setEstadoHipoteca(escritura.isEstadoHipoteca());
                obj.setCantEstancias(escritura.getCantEstancias());
                obj.setCantChacras(escritura.getCantChacras());
                obj.setId(escritura.getId());
                escriturasModificadas.add(obj);
            }
            jugador.setEscrituras(escriturasModificadas);
            for (Servicio servicio : jugador.getServicios()){
                Servicio obj = (Servicio) partida.getTable().getCasilleros().stream().filter(m-> Objects.equals(m.getPosicion(),servicio.getPosicion())).findFirst().get();
                obj.setDuenio(servicio.getDuenio());
                obj.setId(servicio.getId());
                serviciosModificados.add(obj);
            }
            jugador.setServicios(serviciosModificados);
        }
        partida.getBanco().setCantidadPropiedades(partidaEntity.get().getBanco().getCantidadPropiedades());
        partida.getBanco().setCantidadChacras(partidaEntity.get().getBanco().getCantidadChacras());
        partida.getBanco().setCantidadEstancias(partidaEntity.get().getBanco().getCantidadEstancias());
        partida.getBanco().setId(partidaEntity.get().getBanco().getId());
        return  partida;
    }
    public List<Jugador> getJugadores(PartidaEntity partidaEntity){
        List<Jugador> jugadores = new ArrayList<>();
        List<JugadorEntity> jugadorEntities = jugadorRepositoryJpa.findByPartidaOrderByTurnoDesc(partidaEntity);
        for (JugadorEntity jugadorEntity: jugadorEntities){
            Jugador jugador = new Jugador();
            jugador.setId(jugadorEntity.getId().intValue());
            jugador.setNombre(jugadorEntity.getNombre());
            jugador.setPeon(jugadorEntity.getPeon());
            jugador.setPerfilEnum(jugadorEntity.getPerfilEnum());
            jugador.setEstado(jugadorEntity.getEstado());
            jugador.setTurnoRetenido(jugadorEntity.getTurnoRetenido());
            jugador.setCuenta(jugadorEntity.getCuenta());
            jugador.setBot(jugadorEntity.isBot());
            jugador.setPosicion(jugadorEntity.getPosicion());
            jugador.getServicios().addAll(getServicios(jugadorEntity,jugador));
            jugador.getEscrituras().addAll(getEscrituras(jugadorEntity,jugador));
            jugador.setTurno(jugadorEntity.getTurno());
            jugadores.add(jugador);

        }
        return jugadores;
    }
    private List<Servicio> getServicios(JugadorEntity jugadorEntity, Jugador jugador){
        List<Servicio> servicios = new ArrayList<>();
        List<ServicioEntity> servicioEntities = servicioRepositoryJpa.findByDuenio(jugadorEntity);
        for (ServicioEntity servicioEntity: servicioEntities){
            Servicio servicio = new Servicio();
            servicio.setId(servicioEntity.getId());
            servicio.setPosicion(servicioEntity.getPosicion());
            servicio.setDuenio(jugador);
            servicios.add(servicio);
        }
        return servicios;
    }
    private List<Escritura> getEscrituras(JugadorEntity jugadorEntity, Jugador jugador){
        List<Escritura> escrituras = new ArrayList<>();
        List<EscrituraEntity> escrituraEntities = escrituraRepositoryJpa.findByDuenio(jugadorEntity);
        for (EscrituraEntity escrituraEntity: escrituraEntities){
            Escritura escritura = new Escritura();
            escritura.setId(escrituraEntity.getId().intValue());
            escritura.setEstadoHipoteca(escrituraEntity.isEstadoHipoteca());
            escritura.setCantEstancias(escrituraEntity.getCantEstancias());
            escritura.setCantChacras(escrituraEntity.getCantChacras());
            escritura.setPosicion(escrituraEntity.getPosicion());
            escritura.setDuenio(jugador);
            escrituras.add(escritura);
        }
        return escrituras;
    }
    /**
     * Metodo que retorna las partidas diponibles
     * @return Lista de Partida, si no encuentra partidas disponibles, la lista esta vacia
     */
    @Override
    public List<PartidaDto> getAllPartidasInGame() {
        return getPartidasByEstado(EstadoPartida.ENJUEGO);
    }

    /**
     * Metodo que busca las partidas por estado
     * @param estadoPartida Estado por el cual se va filtrar
     * @return Lista de Partida, en caso de no haber resultados, la lista esta vacia
     */
    private List<PartidaDto> getPartidasByEstado(EstadoPartida estadoPartida) {
        List<PartidaEntity> optPartidas = partidaRepositoryJpa.
                findPartidaEntitiesByestadoPartida(estadoPartida);

        List<PartidaDto> partidaList = new ArrayList<>();
        if (Objects.nonNull(optPartidas)) {
            for (PartidaEntity partidaEntity : optPartidas) {
                partidaList.add(modelMapper.map(partidaEntity, PartidaDto.class));
            }
        }
        return partidaList;
    }
}
