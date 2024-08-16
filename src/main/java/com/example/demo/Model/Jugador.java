package com.example.demo.Model;

import com.example.demo.Menu.MenuHelpers.LetterByLetterPrinter;
import com.example.demo.Model.Enums.EstadoJugador;
import com.example.demo.Model.Enums.Peon;
import com.example.demo.Model.Enums.PerfilEnum;
import com.example.demo.Model.Perfil.Perfil;

import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import com.example.demo.Model.Perfil.PerfilAgresivo;
import com.example.demo.Model.Perfil.PerfilConservador;
import com.example.demo.Model.Perfil.PerfilEquilibrado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Scanner;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Jugador {

    private Integer id;
    private Integer cuenta = 35000; //TODO NO BORRAR SIRVE PARA TEST
    private Peon peon;
    private Integer posicion;
    private Perfil perfil;
    private String nombre;
    private Boolean bot; //Asignar si es bot o no
    private Integer turno; //Número que saco en el dado para asignar turno
    private Carta salirComisaria;
    private List<Escritura> escrituras = new ArrayList<>();
    private List<Servicio> servicios = new ArrayList<>();
    private EstadoJugador estado;
    private Integer turnoRetenido;
    private PerfilEnum perfilEnum;


    //region Variables para consola consola
    private Scanner sc = new Scanner(System.in);
    private String input_regex = "[yYnN]";
    private String decision_regex = "[eEsS]";
    private String ANSI_RESET = "\u001B[0m";
    //endregion


    public Jugador(Integer cuenta, Peon peon, Integer posicion,PerfilEnum perfil, String nombre) {
        this.cuenta = cuenta;
        this.peon = peon;
        this.posicion = posicion;
        this.perfilEnum = perfil;
        this.nombre = nombre;
        salirComisaria = null;
        bot = true;
        this.estado = EstadoJugador.VIVO;
        turnoRetenido=0;
    }

    public Jugador(String nombreUsuario) {
        cuenta = 35000;
        peon = Peon.ROJO;
        this.nombre = nombreUsuario;
        perfil = null;
        bot = false;
        salirComisaria = null;
        posicion = 0;
        this.estado = EstadoJugador.VIVO;
        turnoRetenido=0;
    }


    public String getNombreWhitColor(){
        String result = getColor()+nombre;
         return result;
    }
    public String getColor(){
        String result = switch (peon) {
            case AZUL -> "\u001B[34m";
            case ROJO -> "\u001B[31m";
            case AMARILLO -> "\u001B[93m";
            case VERDE -> "\u001B[32m";
            case NARANJA -> "\u001B[38;5;208m";
            case VIOLETA -> "\u001B[35m";
            default -> "";
        };
        return result;
    }
    public Perfil getPerfil(){
       if (perfil == null && perfilEnum != null){
           switch (perfilEnum){
               case AGRESIVO -> perfil = new PerfilAgresivo();
               case CONSERVADOR -> perfil = new PerfilConservador();
               case EQUILIBRADO -> perfil = new PerfilEquilibrado();
           }
       }
       return perfil;
    };

    public Peon elegirPeon(int opcion)
    {
        switch (opcion){
            case 1:
                setPeon(Peon.AZUL);
//                setColor("\u001B[34m");
//                setNombre("\u001B[34m" + nombre);
                break;
            case 2:
                setPeon(Peon.ROJO);
//                setColor("\u001B[31m");
//                setNombre("\u001B[31m" + nombre);
                break;
            case 3:
                setPeon(Peon.AMARILLO);
//                setColor("\u001B[93m");
//                setNombre("\u001B[93m" + nombre);
                break;
            case 4:
                setPeon(Peon.VERDE);
//                setColor("\u001B[32m");
//                setNombre("\u001B[32m" + nombre);
                break;
            case 5:
                setPeon(Peon.NARANJA);
//                setColor("\u001B[38;5;208m");
//                setNombre("\u001B[38;5;208m" + nombre);
                break;
            case 6:
                setPeon(Peon.VIOLETA);
//                setColor("\u001B[35m");
//                setNombre("\u001B[35m" + nombre);
                break;
        }
        return getPeon();
    }


    public Boolean Comprar(Casillero casillero, Banco banco){
        //Método que permite que el jugador compre dicha escritura. Agregar validaciones.

        if (casillero instanceof Escritura){
            Escritura escritura = (Escritura) casillero;
            if(escritura.getDuenio() == null && this.getPosicion() == casillero.getPosicion()){
                String respuesta;
                do {
                    System.out.println("Escritura: " + escritura.getProvincia() + " - " + escritura.getZona());
                    System.out.println("Desea comprar esta escritura? y-Si n-No");
                    respuesta = sc.next();
                } while (!matchRegex(respuesta, input_regex));
//                sc.close();

                if (respuesta.equalsIgnoreCase("y"))
                {
                    escritura.setDuenio(this);
                    escritura.getDuenio().setCuenta(this.getCuenta()- escritura.getValorEscritura());
                    escrituras.add(escritura); //Añado a la lista del jugador
                    banco.setCantidadPropiedades(banco.getCantidadPropiedades()-1);
                    System.out.println("Escritura comprada.");
                    return true;
                }
                else
                {
                    System.out.println("Escritura no comprada.");
                    return false;
                }


            }
        } else if (casillero instanceof Servicio) {
            Servicio servicio = (Servicio) casillero;
            if(servicio.getDuenio() == null && this.getPosicion() == casillero.getPosicion()){
                String respuesta;
                do {
                    System.out.println("Escritura: " + servicio.getTipoServicio());
                    System.out.println("Desea comprar este servicio? y-Si n-No");
                    respuesta = sc.next();
                } while (!matchRegex(respuesta, input_regex));
//                sc.close();

                if (respuesta.equalsIgnoreCase("y"))
                {
                    servicio.setDuenio(this);
                    servicio.getDuenio().setCuenta(this.getCuenta()-servicio.getValorServicio());
                    servicios.add(servicio); //Añado a la lista del jugador
                    banco.setCantidadPropiedades(banco.getCantidadPropiedades()-1);
                    System.out.println("Servicio comprado.");
                    return true;
                }
                else
                {
                    System.out.println("Servicio no comprado.");
                    return false;
                }
            }
        }
        return false;
    }


    public Boolean Vender(ArrayList<Casillero> casilleros, Banco banco){ //Método que permite que el jugador venda dicha escritura.
        String respuesta;
        do {
            LetterByLetterPrinter.println("\n¿Que quiere vender? \nE-Escrituras(E) \nS-Servicios(S)");
            respuesta = sc.next();
        } while (!matchRegex(respuesta, decision_regex));
//           sc.close();

        if (respuesta.equalsIgnoreCase("e"))
        {

                    if (!(getEscrituras() == null) && (!getEscrituras().isEmpty()))
                    {
                        String ventaString;
                        String format;
                        Integer ne = 0;
                        do{
                            ne = 0;
                            System.out.println("Escrituras de " + getNombreWhitColor() + ANSI_RESET + ":");
                            for (Escritura e : getEscrituras())
                            {
                                LetterByLetterPrinter.println(ne + "-Provincia: " + e.getProvincia().toString() +
                                        "|Zona: "+ e.getZona().toString() +"\n");
                                ne++;
                            }
                            LetterByLetterPrinter.println(ne + "- Cancelar");
                            LetterByLetterPrinter.println("¿Cual desea vender?");
                            ventaString = sc.next();
                            format = "[0-"+ne+"]";
                        }while (!matchRegex(ventaString, format));
                        if(ventaString.equals(ne.toString())) return false;
                        int venta = Integer.parseInt(ventaString);
                        for(Casillero casillero : casilleros) {
                            if (casillero instanceof Escritura) {
                                if(casillero.getPosicion() == escrituras.get(venta).getPosicion())
                                {
                                    ((Escritura) casillero).setDuenio(null);
                                    setCuenta(getCuenta() + getEscrituras().get(venta).getValorEscritura()); //Actualizamos la cuenta
                                    LetterByLetterPrinter.println("¡Su propiedad ha sido vendida!, Se acredito en cuenta " + getEscrituras().get(venta).getValorEscritura() + "\n");
                                    escrituras.remove(venta);
                                    banco.setCantidadPropiedades(banco.getCantidadPropiedades()+1);
                                    return true;
                                }
                            }
                        }

                    }
                    else System.out.println("Usted no posee Escrituras por el momento");
                    return false;
        }
        else
        {
            if(getServicios()!=null&&!getServicios().isEmpty()){
                String ventaString;
                String format;
                Integer ns = 0;
                do{
                    ns=0;
                    System.out.println("Servicios de " + getNombreWhitColor() + ANSI_RESET + ":");
                    for (Servicio s : getServicios())
                    {
                        LetterByLetterPrinter.println(ns + "-Rubro: " + s.getTipoServicio().toString() +
                                "|Nombre: "+s.getDescripcion() +"\n");
                        ns++;
                    }
                    LetterByLetterPrinter.println(ns + "- Cancelar");
                    LetterByLetterPrinter.println("¿Cual desea vender?");
                    ventaString = sc.next();
                    format = "[0-"+ns+"]";
                }while (!matchRegex(ventaString, format));
                if(ventaString.equals(ns.toString())) return false;
                int venta = Integer.parseInt(ventaString);
                for(Casillero casillero : casilleros) {
                    if (casillero instanceof Servicio) {
                        if (servicios.get(venta).getPosicion() == casillero.getPosicion()) {
                            getServicios().get(venta).setDuenio(null); //Vendido
                            setCuenta(getCuenta() + getServicios().get(venta).getValorServicio()); //Actualizamos la cuenta
                            LetterByLetterPrinter.println("¡Su propiedad ha sido vendida!, Se acredito en cuenta " + getServicios().get(venta).getValorServicio());
                            servicios.remove(venta);
                            banco.setCantidadPropiedades(banco.getCantidadPropiedades()+1);
                            return true;
                        }
                    }
                }


            }
            else System.out.println("Usted no posee Servicios por el momento");

        }
        return false;
    }

    public void construirMejoras(Banco banco){
        int ne = 0;

        if(getEscrituras().isEmpty())
        {
            LetterByLetterPrinter.println("Usted no posee Propiedades por el momento");
        }
        else
        {
            for (Escritura e : getEscrituras())
            {
                if(e.ProvinciaCompleta(e))
                {
                    LetterByLetterPrinter.println(ne + "-Provincia: " + e.getProvincia().toString() +
                            " | Zona: " + e.getZona().toString() +"\n");
                }
                ne++;
            }
            LetterByLetterPrinter.println("¿Cual desea Mejorar entre esas opciones?");

            int mejora = sc.nextInt();

            if(getEscrituras().get(mejora).getCantChacras() < 4 && getEscrituras().get(mejora).getCantChacras() >= 0 && getEscrituras().get(mejora).getCantEstancias() == 0)
            {
                //Comprar chacras
                if(banco.getCantidadChacras() == 0)
                {
                    LetterByLetterPrinter.println("El banco no tiene chacras disponibles");
                }
                else
                {
                    LetterByLetterPrinter.println("Usted Compro una chacra en " + getEscrituras().get(mejora).getProvincia() + " - " + getEscrituras().get(mejora).getZona());
                    getEscrituras().get(mejora).setCantChacras(getEscrituras().get(mejora).getCantChacras()+1);
                    banco.setCantidadChacras(banco.venderChacras());
                    LetterByLetterPrinter.println("Usted Tiene  " + getEscrituras().get(mejora).getCantChacras() + " Chacras");
                }
            }
            else if(getEscrituras().get(mejora).getCantChacras() == 4)
            {
                if(banco.getCantidadEstancias() == 0)
                {
                    LetterByLetterPrinter.println("El banco no tiene estancias disponibles");
                }
                else
                {
                    if(getEscrituras().get(mejora).getCantEstancias()>= 1){
                        LetterByLetterPrinter.println("Ya tiene una estancia");
                    }
                    else {
                        LetterByLetterPrinter.println("Usted Compro una estancia en " + getEscrituras().get(mejora).getProvincia() + " - " + getEscrituras().get(mejora).getZona());
                        getEscrituras().get(mejora).setCantEstancias(getEscrituras().get(mejora).getCantEstancias()+1);
                        banco.setCantidadEstancias(banco.venderEstancias());

                        banco.setCantidadChacras(banco.getCantidadChacras()+getEscrituras().get(mejora).getCantChacras());
                        getEscrituras().get(mejora).setCantChacras(0);
                    }
                }
            }
            else LetterByLetterPrinter.println("Formato incorrecto.");
        }

    }
    public boolean hipotecar(){

        String respuesta;
        do {
            LetterByLetterPrinter.println("¿Desea Hipotecar? \n1-Si(y) \n2-No(n)");
            respuesta = sc.next();
        } while (!matchRegex(respuesta, input_regex));
//        sc.close();

        if(respuesta.equalsIgnoreCase("y")){
            for (Escritura e : getEscrituras()){
                System.out.println("Seleccione una escritura:\n"+
                        getEscrituras().indexOf(e)+ "-"
                        + "|Nombre:" + e.getProvincia() +
                        " |Zona:"+e.getZona() );
            }
            int escrituraElegia = sc.nextInt();
//            sc.close();
            setCuenta(getCuenta() + getEscrituras().get(escrituraElegia).getValorHipoteca());
            getEscrituras().get(escrituraElegia).setEstadoHipoteca(true);
            return true;
        }
        return false;
    }


    public Integer calcularPatrimonio(){
        int patrimonio = 0;

        patrimonio += this.getCuenta(); //su dinero

        if(!(this.servicios == null))
        {
            for (Servicio s : this.servicios){
                patrimonio += s.ValorTotal(); // mas el valor de cada Servicio
            }
        }
        if(!(this.escrituras == null))
        {
            for (Escritura e : this.escrituras){
                patrimonio += e.ValorTotal(); // mas el valor de cada Escritura
            }
        }
        return patrimonio;
    }

    /**
     * Avanza los casilleros indicados
     * @param cantidad Cantidad de casilleros que se debe avanzar
     * @param largo Largo del tablero, usado para verificar una vuelta al tablero
     */
    public void avanzarPosicion(int cantidad, int largo){
        if(largo<0) return;
        int posFinal = this.posicion + cantidad;
        if(posFinal >= largo){
            posFinal -= largo;
            if(posFinal !=0){
                pasarXSalida();
            }
        }
        this.posicion = posFinal;
    }
    public void marchePreso(){
        this.posicion = 14;
        this.turnoRetenido=2; // Usar dos pq al inicio del turno se descuenta uno.
    }

    /**
     * Metedo que se usa para pagar la carcel con la carta de salida libre
     * @param cartas Recibe el mazo de cartas de donde proviene la carta para devolverla al fondo
     */
    public void pagarCarcel(Stack<Carta> cartas){
        if(this.salirComisaria==null) throw new RuntimeException("La carta no existe");
        this.turnoRetenido=0;
        cartas.add(this.salirComisaria);
        this.salirComisaria=null;
    }

    /**
     * Metedo que se usa para pagar la carcel lanzando los dados
     * @param dados Recibe los dados para lanzar
     * @return retorno True si los dados fueron iguales
     */
    public boolean pagarCarcel(Dado dados){
        dados.tirarDados();
        if(dados.compararValores()){
            this.turnoRetenido=0;
        }
        return dados.compararValores();
    }

    /**
     * Metedo que se usa para pagar la carcel, descuenta $1000 directamente de la cuenta del jugador
     */
    public void pagarCarcel(){
        this.turnoRetenido=0;
        this.cuenta-= 1000;
    }

    /**
     * Metodo que asigna el monto por pasar por el casillero de salida
     */
    public void pasarXSalida(){
        this.cuenta+=5000;
    }
    public void iniciarDescanso(){
        this.turnoRetenido=3;
    }
    public boolean descanso(Dado dados){
        dados.tirarDados();
        return dados.compararValores();
    }
    public void finDescanso(){
        this.turnoRetenido=0;
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


}

