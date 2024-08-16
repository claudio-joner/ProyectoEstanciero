package com.example.demo.Model;

import com.example.demo.Menu.MenuHelpers.LetterByLetterPrinter;
import com.example.demo.Model.Enums.EstadoJugador;
import com.example.demo.Model.Enums.EstadoPartida;
import com.example.demo.Model.Enums.TipoPartida;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Data
@Component
public class Partida {
    private long Id;
    private Tablero table = Tablero.getTableroInstance();
    private Banco banco = Banco.getBancoInstance();
    private ArrayList<Jugador> jugador;
    private Scanner scanner;
    private static final String YES_NO_REGEX = "[yYnN]";
    private int montoVictoria;
    private EstadoPartida estadoPartida;
    private TipoPartida tipoPartida;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFinalizacion;
    private Integer turno;
    String ANSI_RESET = "\u001B[0m";

    public Partida() {
        scanner = new Scanner(System.in);
        montoVictoria = 0;
        jugador = new ArrayList<>();
        fechaCreacion = LocalDateTime.now();
        estadoPartida = EstadoPartida.ENJUEGO;
        turno = 0;
    }

    /**
     * Carga los datos de una partida guardada
     * @param partida
     */
    public void loadPartida(Partida partida) {
        this.Id = partida.Id;
        this.jugador = partida.jugador;
        this.estadoPartida = partida.estadoPartida;
        this.tipoPartida = partida.tipoPartida;
        this.fechaCreacion = partida.fechaCreacion;
        this.fechaFinalizacion = partida.fechaFinalizacion;
        this.montoVictoria = partida.montoVictoria;
        this.turno = partida.turno;
        this.banco.setCantidadPropiedades(partida.getBanco().getCantidadPropiedades());
        this.banco.setCantidadChacras(partida.getBanco().getCantidadChacras());
        this.banco.setCantidadPropiedades(partida.getBanco().getCantidadPropiedades());

    }
    public void reiniciarDatos(){
        table.reiniciarTablero();
        banco.reiniciarBanco();
        estadoPartida = EstadoPartida.ENJUEGO;
        tipoPartida = null;
        fechaCreacion = LocalDateTime.now();
        Id = 0;
        jugador.clear();
        montoVictoria = 0;
    }

    public Boolean quiereSeguirJugando() {
        Boolean answer = false;
        do {
            System.out.println("¿Quieres continuar la partida? (y/n)");
            String input = scanner.next();
            answer = getYesNoAnswer(input);
        } while (answer == null);
        if(!answer)
        {
            fechaFinalizacion = LocalDateTime.now();
        }
        return answer;
    }

    public boolean estaTerminada(ArrayList<Jugador> jugadores) {
        if(tipoPartida == TipoPartida.BANCARROTA)//Ganar por bancarrota
        {
            //Bancarrota
            int n = 0;
            for (Jugador jugador : jugadores)
            {
                if(jugador.getEstado() == EstadoJugador.VIVO)
                {
                    if(jugador.calcularPatrimonio() < 0)
                    {
                        if(jugador.getBot()){
                            jugador.setEstado(EstadoJugador.QUIEBRA);
                            LetterByLetterPrinter.println("¡"+ jugador.getNombreWhitColor() + ANSI_RESET + " quedo en Bancarrota!");
                            n++;
                        }
                        else {
                            if(!jugador.hipotecar()){
                                jugador.setEstado(EstadoJugador.QUIEBRA);
                                LetterByLetterPrinter.println("¡"+ jugador.getNombreWhitColor() + ANSI_RESET + " quedaste en Bancarrota!");
                                n++;
                            }
                        }
                    }
                }
            }
            if(n == (jugadores.size()-1))
            {
                for (Jugador jugador : jugadores)
                {
                    if(jugador.getEstado() == EstadoJugador.VIVO)
                    {
                        jugador.setEstado(EstadoJugador.GANADOR);
                        LetterByLetterPrinter.println("¡Felicidades " + jugador.getNombreWhitColor() + ANSI_RESET + " sos el ganador!");
                        fechaFinalizacion = LocalDateTime.now();
                        estadoPartida = EstadoPartida.FINALIZADO;
                        return true;
                    }
                }
            }
        }
        else
        {   //Monto Maximo
            for (Jugador jugador : jugadores)
            {
                if(jugador.calcularPatrimonio() >= montoVictoria)
                {
                    jugador.setEstado(EstadoJugador.GANADOR);
                    LetterByLetterPrinter.println("¡Felicidades " + jugador.getNombreWhitColor() + ANSI_RESET + " sos el ganador!");
                    fechaFinalizacion = LocalDateTime.now();
                    estadoPartida = EstadoPartida.FINALIZADO;
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public Boolean quiereJugarNuevamente() {
       Boolean answer = false;
           do {
                System.out.println("¿Quieres volver a jugar? (y/n)");
                String input = scanner.next();
                answer = getYesNoAnswer(input);
            } while (answer == null);
           if(answer)
           {
               reiniciarDatos();
           }
           else System.out.println("¡Gracias por jugar!");
        return answer;
    }
    private static Boolean getYesNoAnswer(String input) {
        Pattern pattern = Pattern.compile(YES_NO_REGEX);
        if(pattern.matcher(input).matches()) {
            if (input.toLowerCase().equals("y")) {
                return true;
            } else return false;
        }
        System.out.println("Por favor ingrese y,Y or N,n");
        return null;
    }

    /**
     * Metodo que gestiona los turnos
     * @return Devuelve el siguiente jugador
     */
    public Jugador getNextJugador() {
        Jugador jugador = this.jugador.get(this.turno);
        this.turno++;
        if (this.turno >= this.jugador.size()) this.turno = 0;
        if(jugador.getEstado() != EstadoJugador.VIVO) jugador = getNextJugador();
        return jugador;
    }

    /**
     * Metodo que devuelve al ultimo jugador, se utiliza para imprimir informacion en pantalla
     * @return Devuelve el ultima jugador que jugo
     */
    public Jugador getPrevioJugador() {
        int previousIndex = this.turno - 1;
        if (previousIndex < 0) {
            previousIndex = this.jugador.size() - 1;
        }
        return this.jugador.get(previousIndex);
    }

}
