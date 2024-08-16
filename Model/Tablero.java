package Model;

import java.util.Date;

public class Tablero {
    private Jugadores jugador;
    private Perfil perfil; //Sumar al UML, ya que tendra jugador y perfiles
    private Dados dados;
    private Casilleros casilleros;

    public Jugadores getJugador() {
        return jugador;
    }

    public void setJugador(Jugadores jugador) {
        this.jugador = jugador;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Dados getDados() {
        return dados;
    }

    public void setDados(Dados dados) {
        this.dados = dados;
    }

    public Casilleros getCasilleros() {
        return casilleros;
    }

    public void setCasilleros(Casilleros casilleros) {
        this.casilleros = casilleros;
    }

    public Tablero(Jugadores jugador, Perfil perfil, Dados dados, Casilleros casilleros) {
        this.jugador = jugador;
        this.perfil = perfil;
        this.dados = dados;
        this.casilleros = casilleros;
    }

    public Tablero(){

    }

    public void avanza(Dados d)
    {
        int avance =  d.tirarDados();
        System.out.println("El jugador debe avanzar " + avance + " casilleros");
        //Modificar su ubicacion de casillero
    }
}
