package Model;

public class Servicio {
    // Atributos
    private Integer valor;
    private Jugadores jugador;
    public enum TipoServicio {
        FERROCARRIL,
        COMPANIA
    }
    private TipoServicio tipoServicio;


    //getters y setters
    public Integer getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }

    public Jugadores getJugador() { return jugador; }
    public void setJugador(Jugadores jugador) { this.jugador = jugador; }

    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }


    // Constructores
    public Servicio(){

    }

    public Servicio(Integer valor, Jugadores jugador, TipoServicio tipoServicio) {
        this.valor = valor;
        this.jugador = jugador;
        this.tipoServicio = tipoServicio;
    }


    // Métodos
    public void comprar() {
        // Método que permite que el jugador compre dicho servicio.
    }

    public void vender() {
        // Método que permite que el jugador venda dicho servicio.
    }

    public void pagarAlquiler() {
        // Método que obliga al jugador a pagarle al dueño el costo de alquiler por caer en dicho casillero.
    }

    public void cantidadCompanias() {
        // Método que indica la cantidad de compañías que hay.
    }

    public void cantidadFerrocarriles() {
        // Método que indica la cantidad de ferrocarriles que hay.
    }
}
