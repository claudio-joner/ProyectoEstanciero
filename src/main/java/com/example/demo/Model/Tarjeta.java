package com.example.demo.Model;

import com.example.demo.Menu.MenuHelpers.LetterByLetterPrinter;

import java.util.List;

public abstract class Tarjeta {
    abstract List<Carta> GenerarMazo();


    public void Avanzar(Carta carta, Jugador jugador)
    {
        int cantAvance = 0;
        cantAvance = carta.getCantidad();
        jugador.setPosicion(jugador.getPosicion()+cantAvance);

        System.out.println(carta.getAccion());
    }
    public void Retroceder(Carta carta, Jugador jugador)
    {
        int cantRetr = 0;
        cantRetr = carta.getCantidad();
        jugador.setPosicion(jugador.getPosicion()-cantRetr);

        System.out.println(carta.getAccion());
    }
    public void AvanzarCasillero(Carta carta, Jugador jugador)
    {
        int posicion = 0;
        posicion = carta.getCantidad();
        jugador.setPosicion(posicion);

        //este if es para la carta3 de Suerte: "Haga un paseo hasta la Bodega. Si pasa por la salida cobre 5.000"
        // si el jugador esta en la pos 17, para llegar a la bodega, tiene q dar toda la vuelta,
        // osea, paso por la salida (+5000)
        if (posicion==16 && jugador.getPosicion()>16){
            jugador.setCuenta(jugador.getCuenta()+5000);
        }

        //if para carta5 Suerte, lo mismo del de arriba, pero yendo hasta Salta Norte
        if (posicion==13 && jugador.getPosicion()>13){
            jugador.setCuenta(jugador.getCuenta()+5000);
        }

        //if para carta13 Suerte, lo mismo del de arriba, pero yendo hasta Santa Fe Norte
        if (posicion==26 && jugador.getPosicion()>26){
            jugador.setCuenta(jugador.getCuenta()+5000);
        }

        //if para carta14 Destino y carta9 Suerte, lo mismo del de arriba, pero yendo hasta la Salida
        if (posicion==0 && jugador.getPosicion()>0){
            jugador.setCuenta(jugador.getCuenta()+5000);
        }

        System.out.println(carta.getAccion());
    }
    public void RetrocederCasillero(Carta carta, Jugador jugador)
    {
        int posicion = 0;
        posicion = carta.getCantidad();
        jugador.setPosicion(posicion);

        System.out.println(carta.getAccion());
    }
    public void Cobrar(Carta carta, Jugador jugador)
    {
        int cantCobrar = 0;
        cantCobrar = carta.getCantidad();
        jugador.setCuenta(jugador.getCuenta()+cantCobrar);
        System.out.println(carta.getAccion());

    }
    public void Pagar(Carta carta, Jugador jugador)
    {
        int cantPagar = 0;
        cantPagar = carta.getCantidad();
        jugador.setCuenta(jugador.getCuenta()-cantPagar);

        System.out.println(carta.getAccion());
    }

    //ver carta12 y carta14 de Suerte para entender pq es asi este metodo
    public void PagarEnElMomento(Carta carta, Jugador jugador){
        int cantPagar = 0;

        int acumChacras = 0;
        int acumEstancias = 0;
        int costoXChacra = carta.getCantidad();

        for (Escritura e : jugador.getEscrituras()){
            acumChacras += e.getCantChacras();
            acumEstancias += e.getCantEstancias();
        };

        cantPagar = acumChacras * costoXChacra + acumEstancias * 3000;

        jugador.setCuenta(jugador.getCuenta()-cantPagar);

        System.out.println(carta.getAccion());
    }
    public void marcharPreso(Jugador jugador)
    {
        jugador.setPosicion(14); //Comisaria
        System.out.println("Marche Preso");
    }
    public void salirComisaria(Jugador jugador, Carta carta)
    {
        if(jugador.getSalirComisaria() == null)
        {
            jugador.setSalirComisaria(carta);
        }
        else LetterByLetterPrinter.println("Ya tiene la carta");

        LetterByLetterPrinter.println("Salir de comisaria mas tarde");
    }
}
