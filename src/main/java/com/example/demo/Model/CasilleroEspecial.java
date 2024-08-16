package com.example.demo.Model;
import com.example.demo.Menu.MenuHelpers.LetterByLetterPrinter;
import com.example.demo.Model.Enums.TipoCarta;
import com.example.demo.Model.Enums.tipoEspecial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;

import java.util.*;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
public class CasilleroEspecial extends Casillero {

    private tipoEspecial tipo;
    private String input_regex = "[yYnN]";

    private Tarjeta tarjeta = new Destino();


    public CasilleroEspecial(Integer posicion, String descripcion) {
        super(posicion, descripcion);
    }

    public CasilleroEspecial(Integer posicion) {
        super(posicion);
    }

    public CasilleroEspecial(Integer posicion, String descripcion, tipoEspecial tipo) {
        super(posicion, descripcion);
        this.tipo = tipo;
    }

    public void accion(Jugador jugador, Tablero tablero, tipoEspecial tipoEspecial) {

        switch (tipoEspecial){
            case MPRESO -> carcel(jugador);
            case PREMIO, IMPUESTO -> darDinero(jugador);
            case SALIDA -> jugador.pasarXSalida();
            case SUERTE -> tomarCarta(jugador,tablero.getListaSuerte());
            case DESTINO -> tomarCarta(jugador,tablero.getListaDestino());
            case DESCANSO -> descanso(jugador);
            case CARCEL,ELIBRE -> {return;}
        }
    }

    public Carta tomarCarta(Jugador jugador, Stack<Carta> cartas)
    {
        Carta cartaRetornada;

        cartaRetornada = cartas.pop();
        if(!(cartaRetornada.getMetodo().equals("salirComisaria")))
        {
            cartas.add(0, cartaRetornada);
        }
        realizarAccion(cartaRetornada, jugador);
        return cartaRetornada;
    }

    private void darDinero(Jugador jugador) {
        switch (this.getPosicion()){
            case 4:
                jugador.setCuenta(jugador.getCuenta() - 5000);
                break;
            case 7:
                jugador.setCuenta(jugador.getCuenta() + 2500);
                break;
            case 41:
                jugador.setCuenta(jugador.getCuenta() - 2000);
                break;
        }
    }

    private void carcel(Jugador jugador) {
        jugador.marchePreso();//Ver posicion de carcel


    }

    private void descanso(Jugador jugador) {
        jugador.iniciarDescanso();

    }

    private boolean matchRegex(String input) {
        Pattern pattern = Pattern.compile(input_regex);
        if (pattern.matcher(input).matches()) {
            return true;
        } else {
            System.out.println("Error en el formato del input.");
            return false;
        }
    }


    public void realizarAccion(Carta carta, Jugador jugador)
    {
        switch(carta.getMetodo())
        {
            case "Avanzar":
                tarjeta.Avanzar(carta, jugador);
                break;
            case "Retroceder":
                tarjeta.Retroceder(carta, jugador);
                break;
            case "AvanzarCasillero":
                tarjeta.AvanzarCasillero(carta, jugador);
                break;
            case "RetrocederCasillero":
                tarjeta.RetrocederCasillero(carta, jugador);
                break;
            case "Cobrar":
                tarjeta.Cobrar(carta, jugador);
                break;
            case "Pagar":
                tarjeta.Pagar(carta, jugador);
                break;
            case "PagarEnElMomento":
                tarjeta.PagarEnElMomento(carta, jugador);
                break;
            case "Preso":
                tarjeta.marcharPreso(jugador);
                break;
            case "SalirComisaria":
                tarjeta.salirComisaria(jugador, carta);
                break;
        }
    }

}
