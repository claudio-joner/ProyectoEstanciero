package com.example.demo.Menu;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.example.demo.Menu.MenuHelpers.LetterByLetterPrinter;
import com.example.demo.Menu.MenuHelpers.MenuGenerator;
import com.example.demo.Model.*;
import com.example.demo.Model.Enums.*;
import com.example.demo.Model.Perfil.PerfilAgresivo;
import com.example.demo.Model.Perfil.PerfilConservador;
import com.example.demo.Model.Perfil.PerfilEquilibrado;
import com.example.demo.Model.Perfil.Perfil;
import jakarta.persistence.criteria.CriteriaBuilder;
import com.example.demo.Model.Enums.EstadoPartida;
import com.example.demo.Model.Enums.Peon;
import com.example.demo.Model.Enums.PerfilEnum;
import com.example.demo.Model.Enums.TipoPartida;
import com.example.demo.dtos.PartidaDto;
import com.example.demo.services.PartidaService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
public class MenuMetodos {
    @Autowired
    private PartidaService partidaService;

    private Partida partida;


    private Scanner sc = new Scanner(System.in);

//    private CircularListIterator<Jugador> iterador;
    private MenuGenerator generateMenu;
    private String regex_nombre_jugador = "^[A-Za-z]+$";
    String num_regex = "^[0-9]+$";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy 'a las' HH:mm:ss");
    String ANSI_RESET = "\u001B[0m";

    @Autowired
    public MenuMetodos(Partida partida) {
        this.partida = partida;
    }
    public void setPartida(Partida partida) {
        this.partida = partida;
        this.partida.reiniciarDatos();
//        iterador = new CircularListIterator<>(partida.getJugador());
        generateMenu = new MenuGenerator();
    }

    //----------------------------------MENU PRINCIPAL------------------------------------------------------------------

    public void menuPrincipal()
    {
        //Menú Principal
        boolean loop = true;
        while (loop) {
            String mainMenuTitle = "Estanciero";
            String mainMenuOptions = "1. Iniciar Nuevo Juego\n2. Cargar Juego Guardado\n3. Ver Reglas del Juego\n4. Salir\n";
            String mainMenuRegex = "[1-4]";
            Integer mainOption = generateMenu.generateMenu(mainMenuTitle, mainMenuRegex, mainMenuOptions);
            switch (mainOption) {
                case 1:
                    iniciarNuevoJuego();
                    loop = false;
                    break;
                case 2:
                    if (cargarPartida()) loop = false;
                    //System.out.println("Cargar juego guardado (función no implementada).\n");
                    break;
                case 3:
                    verReglasDelJuego();
                    break;
                case 4:
                    System.out.println("Saliendo del juego.\n");
                    loop = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.\n");
                    break;
            }
        }
    }


    private void iniciarNuevoJuego() {
        // Menú de Dificultad
        String menuTitle = "Estanciero, seleccione una dificultad de juego:";
        String options = "1. Fácil\n2. Medio\n3. Difícil\n";
        String regex = "[1-3]";
        Integer selectedOption = generateMenu.generateMenu(menuTitle, regex, options);

        switch (selectedOption) {
            case 1:
                LetterByLetterPrinter.println("\nHas seleccionado el modo Fácil.");
                configurarJuego(1);
                break;
            case 2:
                LetterByLetterPrinter.println("\nHas seleccionado el modo Medio.");
                configurarJuego(2);
                break;
            case 3:
                LetterByLetterPrinter.println("\nHas seleccionado el modo Difícil.");
                configurarJuego(3);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

        menuElegirPeon();

        // Menú de Opciones de Victoria
        boolean ganarPorValoresTotales = new MenuGenerator().generateMenuYesNo("Opciones de Victoria", "¿Deseas permitir ganar por cantidad de valores totales?");
        int montoVictoria = 0;
        String montoVictoriaString;
        if (ganarPorValoresTotales) {
            do
            {
                do
                {
                    System.out.println("\nIngresa el monto para ganar, debe ser superior a 35000");
                    montoVictoriaString = sc.next();
                } while (!numRegex(montoVictoriaString));
                montoVictoria = Integer.parseInt(montoVictoriaString);
            }while (montoVictoria <= 35000);

            partida.setMontoVictoria(montoVictoria);
        }

        iniciarJuego(montoVictoria);
    }

    private void iniciarJuego(int montoVictoria) {
        LetterByLetterPrinter.println("Iniciando el juego...\n");

        asignarTurnos(partida.getJugador());

        if (montoVictoria > 0) {
            LetterByLetterPrinter.println("\nEl juego terminará cuando un jugador acumule " + montoVictoria + " en valores totales.\n");
            partida.setTipoPartida(TipoPartida.MONTOMAX);
        } else {
            LetterByLetterPrinter.println("\nEl juego terminará cuando todos los demás jugadores hayan quedado en bancarrota.\n");
            partida.setTipoPartida(TipoPartida.BANCARROTA);
        }
    }

    private void configurarJuego(int opcion)
    {
        ArrayList<Jugador> listaJugadores = partida.getJugador();
        agregarJugadores(listaJugadores,opcion);
    }
    public void agregarJugadores(ArrayList<Jugador> lista, int opcion){

//        ArrayList<Perfil> listaPerfiles = new ArrayList<>();
//        listaPerfiles.add(new PerfilConservador());
//        listaPerfiles.add(new PerfilEquilibrado());//Moderado
//        listaPerfiles.add(new PerfilAgresivo());

        Peon[] peones = Peon.values();

        String nombre;
        do{
            LetterByLetterPrinter.println("\nIngrese su nombre");
            nombre = sc.next();
            if(nombre.length()<4 || nombre.length()>23 ){
                System.out.println("Su nombre tiene: "+nombre.length()+" caracteres.\n"+"El nombre debe tener min: 4 letras y max: 23.");
            }
        }while (!nameMatchRegex(nombre) ||(nombre.length()<4 || nombre.length()>23) );

        Jugador jugador = new Jugador(nombre);
        lista.add(jugador);
        LetterByLetterPrinter.println("\n¡Bienvenido " + jugador.getNombre()+ "!");

        int n = 0;
        if(opcion == 1 || opcion == 2 || opcion == 3)
        {
            lista.add(new Jugador(35000,peones[n],0, PerfilEnum.EQUILIBRADO,"botEquilibrado"));
            lista.add(new Jugador(35000,peones[n],0,PerfilEnum.CONSERVADOR,"botConservador"));
            if(opcion == 2 || opcion == 3)
            {
                lista.add(new Jugador(35000,peones[n],0,PerfilEnum.AGRESIVO,"botAgresivo"));
                if(opcion == 3)
                {
                    lista.add(new Jugador(35000,peones[n],0,PerfilEnum.EQUILIBRADO,"botEquilibrado2"));
                }
            }
        }
        partida.setJugador(lista);
    }

    private void asignarTurnos(ArrayList<Jugador> lJugadores) {

        for(Jugador jugador : lJugadores)
        {
            Integer valorDados = partida.getTable().getDado().tirarDados();
            jugador.setTurno(valorDados);
        }
        bubbleSort(lJugadores);

        int n = 1;
        for (Jugador jugador : lJugadores)
        {
            LetterByLetterPrinter.println("* !El jugador " + jugador.getNombreWhitColor() + ANSI_RESET + " Tiene el turno " + n + " Porque saco " + jugador.getTurno() + "!");
            n++;
        }
    }

    private void bubbleSort(ArrayList<Jugador> lJugadores) {
        for (int i = 0; i < lJugadores.size() - 1; i++) {
            for (int j = 0; j < lJugadores.size() - 1; j++) {
                Jugador jugador1 = lJugadores.get(j);
                Jugador jugador2 = lJugadores.get(j + 1);
                if (jugador1.getTurno() < jugador2.getTurno()) {
                    lJugadores.set(j, jugador2);
                    lJugadores.set(j + 1, jugador1);
                }
            }
        }
    }

    private void verReglasDelJuego() {
        LetterByLetterPrinter.println("\t\t\t\t\t\t\tReglas del juego:\n" +
                "\n" +
                "Inicio: Cada jugador recibe $35000.\n" +
                "Asignacion de Turnos: se tiran los dados y se asigna de mayor a menor segun el valor que se saquen en la tirada.\n" +
                "Carcel: cuando cae en el casillero, puede pagar $1000 y salir o tirar dados,sacar doble y no pagarlo.\n" +
                "Compra: Adquirir terrenos al caer en ellos, si es que no poseen terrenos.\n" +
                "Cobro: Cobrar alquiler a otros jugadores.\n" +
                "Construcción: Edificar chacras y estancias.\n" +
                "Impuestos: Pagar al caer en casillas específicas.\n" +
                "Fin del juego: Cuando se logra alguno de los objetivos.\n" +
                "Objetivos:\n" +
                "-Monto de victoria: ganas el 1er jugador que llega a dicho monto.\n" +
                "-Terrateniente: gana el jugador que tiene todas las propiedades.\n");
    }

    //--------------------------------------ELEGIR PEON-----------------------------------------------------------------

    private void menuElegirPeon() {
        String menuTitle = "Seleccione su Peon:";
        String options = "1. AZUL\n2. ROJO\n3. AMARILLO\n4. VERDE\n5. NARANJA\n6. VIOLETA\n";
        String regex = "[1-6]";
        Integer selectedOption = generateMenu.generateMenu(menuTitle, regex, options);

        elegirPeon(selectedOption, partida.getJugador());

    }

    private void elegirPeon(int opcion, ArrayList<Jugador> jugadores)
    {
        ArrayList<Integer> peonesUsados = new ArrayList<>();
        for (Jugador jugador : jugadores)
        {
            if(!jugador.getBot())
            {
                jugador.elegirPeon(opcion);
                peonesUsados.add(opcion);
            }
            else
            {
                int opcionPeonBot = 0;
                do
                {
                    opcionPeonBot = (int)(Math.random() * 6) + 1;
                }while(peonesUsados.contains(opcionPeonBot));
                peonesUsados.add(opcionPeonBot);
                jugador.elegirPeon(opcionPeonBot);

            }
            LetterByLetterPrinter.println("El peon elegido por " + jugador.getNombreWhitColor() +  " es " + jugador.getPeon().name() + ANSI_RESET);
        }
    }

    //--------------------------------------JUGAR TURNO-----------------------------------------------------------------

    public Jugador revisarTurno()
    {
        //return iterador.getNext();
        return partida.getNextJugador();
    }


    public void modoDeTurno(Jugador jugador)
    {
        if(!jugador.getBot())
        {
            System.out.println("Turno jugador:" + jugador.getNombreWhitColor() + ANSI_RESET);
            menuAcciones(jugador);
        }
        else verTurnoBots(jugador);
    }
    private void verTurnoBots(Jugador jugador) {
        if(jugador.getBot()){
            System.out.println("Turno BOT:" + jugador.getNombreWhitColor() + "\n" + ANSI_RESET);
            partida.getTable().jugar(jugador, partida.getBanco());
        }
    }

    //----------------------------------MENU ACCIONES----------------------------------------------------------------

    private void menuAcciones(Jugador jugador)
    {
        boolean loop =true;
        while (loop){
            String actionMenuTitle = "Acciones del Juego";
            String actionMenuOptions = "1. Tirar Dados\n2. Ver Propiedades" +
                    "\n3. Vender Propiedad\n4. Construir Mejoras\n5. Ver Saldo\n";
            String actionMenuRegex = "[1-5]";
            Integer actionOption = generateMenu.generateMenu(actionMenuTitle, actionMenuRegex, actionMenuOptions);

            switch (actionOption) {
                case 1:
                    tirarDados(jugador);
                    loop = false;
                    break;
                case 2:
                    verPropiedades(jugador);
                    break;
                case 3:
                    venderPropiedad(jugador);
                    break;
                case 4:
                    construirMejoras(jugador);
                    break;
                case 5:
                    verSaldo(jugador);
                    break;
                default:
                    System.out.println("Opción no válida.\n");
                    break;
            }
        }
    }


    private void tirarDados(Jugador jugador) {
        partida.getTable().jugar(jugador,partida.getBanco());
    }

    private void verPropiedades(Jugador jugador) {
        int ne = 0;
        int ns = 0;
        System.out.println("Propiedades de " + jugador.getNombreWhitColor() + ANSI_RESET +  " :");
        if(jugador.getEscrituras().isEmpty() && jugador.getServicios().isEmpty())
        {
            LetterByLetterPrinter.println("Usted no posee Propiedades por el momento");
        }
        else {
            for (Escritura e : jugador.getEscrituras())
            {
                LetterByLetterPrinter.println("Escrituras:");
                LetterByLetterPrinter.println(ne + "- " + e.getProvincia().toString() +" - "+ e.getZona().toString() + "\n");
                ne++;
            }
            LetterByLetterPrinter.println("Servicios:");
            for (Servicio s : jugador.getServicios())
            {
                LetterByLetterPrinter.println(ns + "- " + s.getTipoServicio() + "\n");
                ns++;
            }
        }
    }
    private void venderPropiedad(Jugador jugador) {
        if(jugador.getEscrituras().isEmpty() && jugador.getServicios().isEmpty())
        {
            LetterByLetterPrinter.println("Usted no posee Propiedades por el momento");
        }
        else jugador.Vender(partida.getTable().getCasilleros(), partida.getBanco());
    }

    private void construirMejoras(Jugador jugador) {
        if(jugador.getEscrituras().isEmpty() && jugador.getServicios().isEmpty())
        {
            LetterByLetterPrinter.println("Usted no posee Propiedades por el momento");
        }
        else jugador.construirMejoras(partida.getBanco());
    }
    private void verSaldo(Jugador jugador) {
        LetterByLetterPrinter.println("\nEl saldo de tu cuenta es: " + jugador.getCuenta() +  "\n"+
                                        "El patrimonio total es: " + jugador.calcularPatrimonio());
    }

    //----------------------------------LOGO ESTANCIERO-----------------------------------------------------------------
    public void mostrarLogoEstanciero() {

        LetterByLetterPrinter.println("                                                    d8,                        \n" +
                "                  d8P                              `8P                         \n" +
                "               d888888P                                                        \n" +
                " d8888b .d888b,  ?88'   d888b8b    88bd88b  d8888b  88b d8888b  88bd88b d8888b \n" +
                "d8b_,dP ?8b,     88P   d8P' ?88    88P' ?8bd8P' `P  88Pd8b_,dP  88P'  `d8P' ?88\n" +
                "88b       `?8b   88b   88b  ,88b  d88   88P88b     d88 88b     d88     88b  d88\n" +
                "`?888P'`?888P'   `?8b  `?88P'`88bd88'   88b`?888P'd88' `?888P'd88'     `?8888P'\n");
    }

    //----------------------------------TABLERO DE PARTIDA--------------------------------------------------------------

    public void mostrarTablero() {

        //---MATRIZ-----------
        int filas = 6;
        int columnas = 7;
        String[][] tablero = new String[filas][columnas];

        //------LISTAS NECESARIAS-----
        ArrayList<Jugador> listaJugadores = getPartida().getJugador();
        ArrayList<Casillero> listaCasilleros = getPartida().getTable().getCasilleros();
        bubbleSortCasillero(listaCasilleros);


        //---COLORES
        String azul       = "\u001B[34m";
        String verde      = "\u001B[32m";
        String amarillo   = "\u001B[33m";
        String violeta    = "\u001B[35m";
        String verdeClaro = "\u001B[92m";
        String naranja    = "\u001B[38;5;208m";
        String celeste    = "\u001B[96m";
        String rojo       = "\u001B[31m";

        String gris = "\u001B[38;5;242m";
        String azul_claro = "\u001B[94m";
        String marron = "\u001B[38;5;94m";
        String purpura = "\u001B[38;5;93m";



        String ANSI_RESET = "\u001B[0m";

        //------OTRAS VARIABLES----------
        int casilleroAncho = 30;  // Ancho fijo para todos los casilleros
        String color = celeste;

        //--------------STRINGS COMUNES CASILLERO----------------
        String lineaPuntos= String.valueOf('-').repeat(casilleroAncho) + "\n"; //LINEAS PUNTOS
        String lineasVacias= "|"+String.valueOf(' ').repeat(casilleroAncho-2)+"|"+"\n";
        String casilleroPeon= "|"+ ajustarTexto("peon",casilleroAncho-2)+"|"+"\n";

        String casilleroEspecial;
        String escritura;
        String servicios;


        for(Casillero casillero : listaCasilleros){
            int pos = casillero.getPosicion();
            int i = pos / columnas; // Calcular la fila
            int line = pos % columnas; // Calcular la columna 1 /

            //0=SUERTE,1=DESTINO,2=CARCEL,3=IMPUESTO,4=MPRESO,5=DESCANSO,6=ELIBRE,7=SALIDA,8=PREMIO


            if(casillero instanceof CasilleroEspecial){
                switch (((CasilleroEspecial) casillero).getTipo().toString()){
                    case "SUERTE" :
                        color = naranja;
                        break;
                    case "DESTINO":
                        color = verde;
                        break;
                    case "CARCEL":
                        color = gris;
                        break;
                    case "IMPUESTO":
                        color = ANSI_RESET;
                        break;
                    case "MPRESO":
                        color = gris;
                        break;
                    case "ELIBRE":
                        color = gris;
                        break;
                    case "SALIDA":
                        color = gris;
                        break;
                    case "PREMIO":
                        color = gris;
                        break;
                }
                casilleroEspecial =
                        lineaPuntos + //1
                                "|" + ajustarTexto(casillero.getPosicion().toString(), casilleroAncho - 2) +"|"+"\n"+ //2
                                "|" + color +ajustarTexto(casillero.getDescripcion(), casilleroAncho - 2) +ANSI_RESET+"|"+"\n" +//3
                                lineasVacias + //4
                                lineasVacias + //5
                                lineasVacias + //6
                                lineasVacias + //7
                                "|"+ANSI_RESET+ tieneJugador(casillero, listaJugadores)+ ANSI_RESET+"|"+"\n"//8
                                +lineaPuntos; //9
                tablero[i][line] = casilleroEspecial;

            } else if (casillero instanceof Escritura) {
                String duenio = "Sin Duenio";
                if(!(((Escritura) casillero).getDuenio() == null)){
                    duenio = ((Escritura) casillero).getDuenio().getNombreWhitColor();
                }
                switch (((Escritura) casillero).getProvincia().toString()){
                    case "FORMOSA" :
                        color = azul;
                        break;
                    case "RIO_NEGRO":
                        color = verde;
                        break;
                    case "SALTA":
                        color = amarillo;
                        break;
                    case "MENDOZA":
                        color = violeta;
                        break;
                    case "SANTA_FE":
                        color = verdeClaro;
                        break;
                    case "TUCUMAN":
                        color = naranja;
                        break;
                    case "CORDOBA":
                        color = celeste;
                        break;
                    case "BUENOS_AIRES":
                        color = rojo;
                        break;
                }

                escritura =
                        lineaPuntos +//1
                                "|" + ajustarTexto(casillero.getPosicion().toString(), casilleroAncho - 2) +"|"+"\n"+  //2
                                ANSI_RESET+"|" + color +ajustarTexto(((Escritura) casillero).getProvincia().toString(), casilleroAncho -2) +ANSI_RESET+"|"+"\n"+//3
                                ANSI_RESET+"|" + color +ajustarTexto(((Escritura) casillero).getZona().toString(), casilleroAncho - 2) +ANSI_RESET+"|"+"\n"+//4
                                "|" + ajustarTexto(String.valueOf(((Escritura) casillero).getValorEscritura()), casilleroAncho - 2) +"|"+"\n"+//5
                                "|" +ANSI_RESET+ajustarTexto(duenio, casilleroAncho - 2) +ANSI_RESET+ "|\n" +//6
                                lineasVacias+//7
                                "|"+ANSI_RESET+ tieneJugador(casillero, listaJugadores)+ ANSI_RESET+"|"+"\n"//8
                                + ANSI_RESET +lineaPuntos;//9
                tablero[i][line] = escritura;


            }
            else {
                String duenio = "Sin Duenio";
                if(!(((Servicio) casillero).getDuenio() == null)){
                    duenio = ((Servicio) casillero).getDuenio().getNombreWhitColor();
                }
                switch (((Servicio) casillero).getTipoServicio().toString()){
                    case "PETROLERA" :
                        color = celeste;
                        break;
                    case "FERROCARRIL":
                        color = marron;
                        break;
                    case "INGENIO":
                        color = gris;
                        break;
                    case "BODEGA":
                        color = verdeClaro;
                        break;
                }
                servicios =
                        lineaPuntos + //1
                                "|" + ajustarTexto(casillero.getPosicion().toString(), casilleroAncho - 2) +"|"+"\n" + //2
                                ANSI_RESET+"|" + color +ajustarTexto(((Servicio)casillero).getTipoServicio().toString(), casilleroAncho - 2) +ANSI_RESET+ "|"+"\n" +//3
                                "|" + ajustarTexto(String.valueOf(((Servicio)casillero).getValorServicio()), casilleroAncho - 2) +"|"+"\n" + //4
                                "|" +ANSI_RESET+ajustarTexto(duenio, casilleroAncho - 2) +ANSI_RESET+"|"+"\n" + //5
                                lineasVacias+ //6
                                lineasVacias+//7
                                "|"+ANSI_RESET+ tieneJugador(casillero, listaJugadores)+ ANSI_RESET+"|"+"\n"//8
                                + ANSI_RESET +lineaPuntos;//9
                tablero[i][line] = servicios;
            }
        }

        for (int i = 0; i < filas; ++i) {
            for (int line = 0; line < 9; ++line) {
                for (int j = 0; j < columnas; ++j) {
                    String[] casilleroLineas = tablero[i][j].split("\n");
                    System.out.print(casilleroLineas[line] + " ");
                }
                System.out.println();
            }
        }
        estadisticasJugador();

    }

    private void estadisticasJugador()
    {
        Jugador jugadorPrevio = partida.getPrevioJugador();
        System.out.println("================================ Datos de jugador =====================================");
        System.out.println("Nombre: " + jugadorPrevio.getNombreWhitColor() + ANSI_RESET + "\n" +
                "Saldo en Cuenta: " + jugadorPrevio.getCuenta() + "\n" +
                "Saldo Total: " + jugadorPrevio.calcularPatrimonio() + "\n" +
                "Peon: " + jugadorPrevio.getPeon() + ANSI_RESET);
        if(jugadorPrevio.getEscrituras() != null)
        {
            System.out.println("Cantidad de Escrituras: " + jugadorPrevio.getEscrituras().size());
        }
        if (jugadorPrevio.getServicios() != null)
        {
            System.out.println("Cantidad de Servicios: " + jugadorPrevio.getServicios().size());
        }
    }

    private String tieneJugador(Casillero casillero, ArrayList<Jugador> jugadores) {

        String peonJugador = ajustarTexto("-",28);
        for (Jugador jugador : jugadores)
        {
            if(casillero.getPosicion().equals(jugador.getPosicion()))
            {
                peonJugador = ajustarTexto(jugador.getColor()+jugador.getPeon().toString(),28);
            }
        }
        return peonJugador;
    }

    private void bubbleSortCasillero(ArrayList<Casillero> lCasilleros) {
        for (int i = 0; i < lCasilleros.size() - 1; i++) {
            for (int j = 0; j < lCasilleros.size() - 1 - i; j++) {
                Casillero casillero1 = lCasilleros.get(j);
                Casillero casillero2 = lCasilleros.get(j + 1);
                if (casillero1.getPosicion() > casillero2.getPosicion()) {
                    lCasilleros.set(j, casillero2);
                    lCasilleros.set(j + 1, casillero1);
                }
            }
        }
    }


    private String ajustarTexto(String texto, int longitud) {
        String ansiRegex = "\\u001B\\[[;\\d]*m";
        Pattern pattern = Pattern.compile(ansiRegex);
        Matcher matcher = pattern.matcher(texto);
        String textoSinANSI = matcher.replaceAll("");

        if (textoSinANSI.length() >= longitud) {
            return texto.substring(0, longitud);
        }

        int paddingTotal = longitud - textoSinANSI.length();
        int paddingStart = paddingTotal / 2;
        int paddingEnd = paddingTotal - paddingStart;

        StringBuilder resultado = new StringBuilder();
        int index = 0;
        while (matcher.find()) {
            if (index < matcher.start()) {
                resultado.append(" ".repeat(Math.max(0, paddingStart)));
                resultado.append(texto, index, matcher.start());
                paddingStart = 0;
            }
            resultado.append(matcher.group());
            index = matcher.end();
        }

        if (index < texto.length()) {
            resultado.append(" ".repeat(Math.max(0, paddingStart)));
            resultado.append(texto.substring(index));
            paddingStart = 0;
        }

        resultado.append(" ".repeat(paddingEnd));
        return resultado.toString();
    }



    //----------------------------------RESULTADOS PARTIDA--------------------------------------------------------------

    public void mostrarResultados()
    {
        LetterByLetterPrinter.println("Partida iniciada el " + partida.getFechaCreacion().format(formatter));
        tiempoJuego();
        mostrarTipoPartida();
        mostrarEstadoPartida();
    }

    private void tiempoJuego() {
        LocalDateTime fechaIni = partida.getFechaCreacion();
        LocalDateTime fechaFin = partida.getFechaFinalizacion();

        Duration duracion = Duration.between(fechaIni, fechaFin);
        long horas = duracion.toHours();
        long minutos = duracion.toMinutes() % 60;
        long segundos = duracion.getSeconds() % 60;
        LetterByLetterPrinter.println("Tiempo de Juego: " + String.format("%02d:%02d:%02d", horas, minutos, segundos));
    }

    private void mostrarTipoPartida()
    {
        if (partida.getTipoPartida() == TipoPartida.MONTOMAX)
        {
            LetterByLetterPrinter.println("Se eligio ganar la Partida por Monto Maximo");
            LetterByLetterPrinter.println("El monto de victoria elegido fue: " + partida.getMontoVictoria());
        }
        else LetterByLetterPrinter.println("Se eligio ganar la Partida por Bancarrota");
    }

    private void mostrarEstadoPartida()
    {
        if(partida.getEstadoPartida() == EstadoPartida.ENJUEGO)
        {
            LetterByLetterPrinter.println("La partida esta en juego\n");
        } else if (partida.getEstadoPartida() == EstadoPartida.CANCELADO)
        {
            LetterByLetterPrinter.println("Se cancelo la partida\n");
        }
        else LetterByLetterPrinter.println("La partida finalizo\n");
    }

    //------------------------------------------REGEX-------------------------------------------------------------------

    private boolean nameMatchRegex(String name) {
        Pattern pattern = Pattern.compile(regex_nombre_jugador);
        if (pattern.matcher(name).matches()) {
            return true;
        } else {
            System.out.println("Error en el formato del input.");
            return false;
        }
    }

    private boolean numRegex(String numero) {
        Pattern pattern = Pattern.compile(num_regex);
        if (pattern.matcher(numero).matches()) {
            return true;
        } else {
            System.out.println("Error en el formato del input.");
            return false;
        }
    }
    //--------------------------------------Cargar Partida--------------------------------------------------------------
    private boolean cargarPartida(){
        List<PartidaDto> partidas = partidaService.getAllPartidasInGame();
        if (partidas.isEmpty()){
            System.out.println("No se encontro partidas disponibles.");
            return false;
        }
        System.out.println("Se encontraron "+partidas.size()+" partidas.");
        int indice = 0;
        String opciones = "";
        for (PartidaDto partida : partidas) {
            opciones += indice + " - "+ partida.getId()+" - "+partida.getTipoPartida()+" - "+partida.getFechaCreacion().format(formatter)+"\n";
            indice++;
        }
        opciones += indice + " - Cancelar\n";
        String regex = "[ 0-"+ indice+ "]";
        Integer value = generateMenu.generateMenu("cargar partidas",regex,opciones);
        if(value == indice) return false;
        this.partida.loadPartida(partidaService.getPartidaById(partidas.get(value).getId()));
        return true;
    }
    public void savePartida(){
        Partida saved =partidaService.savePartida(partida);
        partida.loadPartida(saved);
    }
}
