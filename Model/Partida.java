package Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Partida {
    private Tablero table;
    private Banco banco;

    public Tablero getTable() {
        return table;
    }

    public void setTable(Tablero table) {
        this.table = table;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Partida() {
        Tablero table = new Tablero();
        Banco banco = new Banco();

        this.table = table;
        this.banco = banco;
    }

    public void ganarMontoMax(){
        int montoMax = 0;
        int monto = 0;
        String descripcion = "";
        //Escritura escritura = new Escritura();
        //Servicio servicio= new Servicio();

        ArrayList<Jugadores> listaJugadores = getTable().getListaJugador();
        ArrayList<Escritura> listaEscrituras = getTable().getListaEscrituras();
        ArrayList<Servicio> listaServicios = getTable().getListaServicios();
        Scanner scan = new Scanner(System.in);

        System.out.println("Indique el monto de capital(dinero + valor de propiedades) para ganar la partida. ");
        monto = scan.nextInt();
        scan.close();

        for (Jugadores jugador: listaJugadores){
            for(Escritura escritura : listaEscrituras){
                if(escritura.getJugador().nombre == jugador.getNombre()){
                    montoMax += escritura.getValorCompra() + (escritura.getCampos() * 10) + (escritura.getCharcras() * 100) +(escritura.getEstancias() *1000) ;
                }
            }
            for(Servicio servicio : listaServicios){
                if(servicio.getJugador().nombre == jugador.getNombre()){
                    montoMax += servicio.getValorCompra() ;
                }
            }

            for (int i = 0; i < listaPropiedadesJugador.size() ; i++) {
                listaPropiedadesJugador[i]
            }
            //Continuar metodo
        }
    }

    public void ganarPropiedad(){
        //Codigo
    }

    public void seleccionarDificultad(){
        //Codigo
    }

    public void repartirDinero(){
        //Codigo
    }
}
