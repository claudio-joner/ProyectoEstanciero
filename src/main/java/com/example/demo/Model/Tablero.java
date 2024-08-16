package com.example.demo.Model;
import com.example.demo.Menu.MenuHelpers.LetterByLetterPrinter;
import com.example.demo.Model.Enums.TipoCarta;
import com.example.demo.Model.Enums.tipoEspecial;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

@Data
public class Tablero {

    private static class TableroSingleton
    {
        private static Tablero INSTANCE = new Tablero();
        private static void resetInstance(){
            INSTANCE = new Tablero();
        }
    }

    public static Tablero getTableroInstance()
    {
        return TableroSingleton.INSTANCE;
    }
    private Dado dado;
    private ArrayList<Casillero> casilleros;
    private String input_regex = "[yYnN]";
    private Stack<Carta> listaSuerte;
    private Stack<Carta> listaDestino;


    private Tablero()
    {
        casilleros = new ArrayList<>();
        dado = new Dado();
        inicializarCasilleros();
        InicializarMazo();
        mezclarMazo(List.of(listaDestino));
        mezclarMazo(List.of(listaSuerte));
    }
    public void jugar(Jugador jugador,Banco banco){
        if(jugador.getTurnoRetenido()>0){
            jugador.setTurnoRetenido(jugador.getTurnoRetenido()-1);
        }
        // Si estas en la carcel y tenes que esperar
        if(jugador.getPosicion()==14 && jugador.getTurnoRetenido()>0){
            // Menu preso
            if(jugador.getBot()){
                presoBot(jugador);
            }
            else {
                presoJugador(jugador);
            }
        }
        // Si estas en descanso y podes esperar
        if(jugador.getPosicion()==21 && jugador.getTurnoRetenido()>0){
            // Menu descanso
            if(jugador.getBot()){
                descansoBot(jugador);
            }else {
                descansoJugador(jugador);
            }
        }
        // Si el jugador puede avanzar
        if(jugador.getTurnoRetenido()==0){

            avanzar(jugador, banco);
        }
    }
    private void avanzar(Jugador jugador, Banco banco){
        int tiradas = 0;
        boolean doble = false;
        do{
            int avance =  dado.tirarDados();
            doble = dado.compararValores();
            if(doble) tiradas++;
            System.out.println("Valores de los Dados "+dado.getValorDadoA()+ " || "+dado.getValorDadoB());
            if (tiradas >= 3)
            {

                System.out.println("Saco Par 3 veces, debe marchar preso");
                jugador.marchePreso();
                break;
            }

            jugador.avanzarPosicion(avance,casilleros.size());
            System.out.println("El jugador avanza " + avance + " casilleros");

            Casillero casillero = casilleros.stream().filter(m-> m.getPosicion().equals(jugador.getPosicion())).findFirst().orElse(null);
            if(casillero == null)throw new RuntimeException("Posicion no encontrado");
            System.out.println("Avanza hasta el casillero "+casillero.getDescripcion());
            if(casillero instanceof CasilleroEspecial casilleroEspecial) {
                casilleroEspecial.accion(jugador,this, casilleroEspecial.getTipo());
            }
            else if(casillero instanceof Escritura escritura){
                if(escritura.getDuenio()==null){
                    if(!jugador.getBot())
                    {
                        jugador.Comprar(escritura,banco);

                    }
                    else
                    {
                        if(!jugador.getPerfil().comprarPorPreferenciaPropiedadV2(escritura,jugador)){
                            jugador.getPerfil().comprarFueraDePreferenciaPropiedad(escritura,jugador);
                        }
                    }

                }
                else{
                    escritura.CobrarAlquiler(jugador);
                }
            }
            else if(casillero instanceof Servicio servicio){
                if(servicio.getDuenio()==null){
                    if(!jugador.getBot())
                    {
                        jugador.Comprar(servicio,banco);
                    }
                    else
                    {
                        if(!jugador.getPerfil().comprarPorPreferenciaPropiedadV2(servicio,jugador)){
                            jugador.getPerfil().comprarFueraDePreferenciaPropiedad(servicio,jugador);
                        }
                    }

                }else{
                    servicio.cobrarAlquiler(jugador, dado.tirarDados());
                }
            }
            if(doble)
            {
                System.out.println("Sacaste doble, Juegue Nuevamente");
            }
        } while (doble);
    }
    private void presoJugador(Jugador jugador){
        Scanner sc = new Scanner(System.in);
        String read;
        do{
            LetterByLetterPrinter.println("Caiste en la carcel, marche preso.");
            LetterByLetterPrinter.println("Para salir tiene que pagar $1000 o sacar doble en datos dados. 1-Pagar 2-Sacar Doble");
            read = sc.next();
        }while (!matchRegex(read, "[1-2]"));
        int respuesta = Integer.parseInt(read);
        //sc.close();
        if(jugador.getSalirComisaria() != null)
        {
            String inputConsola;
            do {
               LetterByLetterPrinter.println("¿Desea salir utilizando su carta?");
                inputConsola = sc.next().trim();
            } while (!matchRegex(inputConsola));
            if (inputConsola.equalsIgnoreCase("y"))
            {
                if(jugador.getSalirComisaria().getTipo() == TipoCarta.DESTINO)
                {
                    jugador.pagarCarcel(listaDestino);
                }
                else
                {
                    jugador.pagarCarcel(listaSuerte);
                }
            }
        }
        else
        {
        switch (respuesta){
            case 1:
                jugador.pagarCarcel();
                LetterByLetterPrinter.println("Pago $1000, sale de la carcel.");
                break;
            case 2:

                if (jugador.pagarCarcel(dado)){
                    LetterByLetterPrinter.println("Saco doble no tiene que pagar mil,sale de la carcel.");
                }
                else{
                    do{
                        LetterByLetterPrinter.println("No saco doble, pague $1000 o espere un turno.");
                        LetterByLetterPrinter.println("1-Pagar\n2-Saltear turno");
                        read = sc.next();
                    }while (!matchRegex(read, "[1-2]"));

                    if(Integer.parseInt(read)==1){
                        jugador.pagarCarcel();
                    }
                }
                break;
            }
        }
    }
    private void presoBot(Jugador jugador){
        if(jugador.getSalirComisaria() != null)
        {
            if(jugador.getSalirComisaria().getTipo() == TipoCarta.DESTINO)
            {
                jugador.pagarCarcel(listaDestino);
            }
            else
            {
                jugador.pagarCarcel(listaSuerte);
            }
        }
        else{
            if (!jugador.pagarCarcel(dado)){
                jugador.pagarCarcel();
            }
        }

    }
    private void InicializarMazo() {
        var suerte = new Suerte();
        var destino = new Destino();
        listaSuerte = suerte.GenerarMazo();
        listaDestino = destino.GenerarMazo();
    }
    private void mezclarMazo(List<Stack<Carta>> listaCartas){
        Collections.shuffle(listaCartas);
    }
    private void descansoJugador(Jugador jugador){
        String inputConsola;
        do {
            System.out.print("Desea permanecer un turno en descanso? y:SI n:NO");
            Scanner sc = new Scanner(System.in);
            inputConsola = sc.next().trim();
        } while (!matchRegex(inputConsola));
        if (inputConsola.equalsIgnoreCase("y")){
            if(jugador.descanso(dado)) jugador.finDescanso();
        }
        else{
            jugador.finDescanso();
        }
    }
    private void descansoBot(Jugador jugador){
        if(jugador.descanso(dado)) jugador.finDescanso();

    }
    public boolean matchRegex(String input) {
        Pattern pattern = Pattern.compile(input_regex);
        if (pattern.matcher(input).matches()) {
            return true;
        } else {
            System.out.println("Error en el formato del input.");
            return false;
        }
    }
    public boolean matchRegex(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(input).matches()) {
            return true;
        } else {
            System.out.println("Error en el formato del input.\n");
            return false;
        }
    }

  
    private void  inicializarCasilleros(){
        Escritura escritura1 = new Escritura();
        Servicio servicio1 = new Servicio();

        var escrituras = escritura1.InicializarEscritura();
        var servicios = servicio1.InicializarServicios();
        this.casilleros.addAll(escrituras);
        this.casilleros.addAll(servicios);


        Casillero casilleroEspecial = new CasilleroEspecial(10,"Destino", tipoEspecial.DESTINO);
        casilleros.add(casilleroEspecial);
        Casillero casilleroEspecial1 = new CasilleroEspecial(25,"Destino", tipoEspecial.DESTINO);
        casilleros.add(casilleroEspecial1);
        Casillero casilleroEspecial2 = new CasilleroEspecial(38,"Destino", tipoEspecial.DESTINO);
        casilleros.add(casilleroEspecial2);
        Casillero casilleroEspecial3 = new CasilleroEspecial(15,"Suerte", tipoEspecial.SUERTE);
        casilleros.add(casilleroEspecial3);
        Casillero casilleroEspecial4 = new CasilleroEspecial(36,"Suerte", tipoEspecial.SUERTE);
        casilleros.add(casilleroEspecial4);
        Casillero casilleroEspecial5 = new CasilleroEspecial(0,"Salida", tipoEspecial.SALIDA);
        casilleros.add(casilleroEspecial5);
        Casillero casilleroEspecial6 = new CasilleroEspecial(4,"Impuesto a los Reditos", tipoEspecial.IMPUESTO);
        casilleros.add(casilleroEspecial6);
        Casillero casilleroEspecial7 = new CasilleroEspecial(7,"Premio Ganadero", tipoEspecial.PREMIO);
        casilleros.add(casilleroEspecial7);
        Casillero casilleroEspecial8 = new CasilleroEspecial(14,"Comisaria", tipoEspecial.CARCEL);
        casilleros.add(casilleroEspecial8);
        Casillero casilleroEspecial9 = new CasilleroEspecial(21,"Descanso", tipoEspecial.DESCANSO);
        casilleros.add(casilleroEspecial9);
        Casillero casilleroEspecial10 = new CasilleroEspecial(28,"Libre Estacionamiento", tipoEspecial.ELIBRE);
        casilleros.add(casilleroEspecial10);
        Casillero casilleroEspecial11 = new CasilleroEspecial(35,"Marche Preso", tipoEspecial.MPRESO);
        casilleros.add(casilleroEspecial11);
        Casillero casilleroEspecial12 = new CasilleroEspecial(41,"Impuesto a las Ventas", tipoEspecial.IMPUESTO);
        casilleros.add(casilleroEspecial12);



    }

    /**
     * Reinicia la instancia de tablero.
     * Usar cuando se quiere arrancar una nueva
     */
    public void reiniciarTablero(){
        TableroSingleton.resetInstance();
    }
}
