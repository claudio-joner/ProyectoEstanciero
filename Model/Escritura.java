package Model;

public class Escritura {

    //atributos
    private String  provincia; //tambien podriamos agregar q zona es con un Enum (centro / norte / sur)
                               // para despues facilitarnos verificar q un solo jugador tiene las 2 o 3
                               // zonas de una provincia
    private Integer valorCampo;
    private Integer valorAlquiler;
    private Integer valorHipoteca;
    private boolean estadoHipoteca;
    private Integer campos;
    private Integer chacras;
    private Integer estancias;
    private Jugadores jugador;


    //getters y setters
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public Integer getValorCampo() { return valorCampo; }
    public void setValorCampo(Integer valorCampo) { this.valorCampo = valorCampo; }

    public Integer getValorAlquiler() { return valorAlquiler; }
    public void setValorAlquiler(Integer valorAlquiler) { this.valorAlquiler = valorAlquiler; }

    public Integer getValorHipoteca() { return valorHipoteca; }
    public void setValorHipoteca(Integer valorHipoteca) { this.valorHipoteca = valorHipoteca; }

    public boolean isEstadoHipoteca() { return estadoHipoteca; }
    public void setEstadoHipoteca(boolean estadoHipoteca) { this.estadoHipoteca = estadoHipoteca; }

    public Integer getCampos() { return campos; }
    public void setCampos(Integer campos) { this.campos = campos; }

    public Integer getChacras() { return chacras; }
    public void setChacras(Integer chacras) { this.chacras = chacras; }

    public Integer getEstancias() { return estancias; }
    public void setEstancias(Integer estancias) { this.estancias = estancias; }

    public Jugadores getJugador() { return jugador; }
    public void setJugador(Jugadores jugador) { this.jugador = jugador; }


    //constructores
    public Escritura(String provincia, Integer valorCampo, Integer valorAlquiler, Integer valorHipoteca, boolean estadoHipoteca, Integer campos, Integer chacras, Integer estancias, Jugadores jugador){
        this.provincia = provincia;
        this.valorCampo = valorCampo;
        this.valorAlquiler = valorAlquiler;
        this.valorHipoteca = valorHipoteca;
        this.estadoHipoteca = estadoHipoteca;
        this.campos = campos;
        this.chacras = chacras;
        this.estancias = estancias;
        this.jugador = jugador;
    }

    public Escritura(String provincia, Integer valorCampo, Integer valorAlquiler, Integer valorHipoteca){
        this.provincia = provincia;
        this.valorCampo = valorCampo;
        this.valorAlquiler = valorAlquiler;
        this.valorHipoteca = valorHipoteca;
        this.estadoHipoteca = false;
        this.campos = 0;
        this.chacras = 0;
        this.estancias = 0;
        this.jugador = null;
    }
    public Escritura(){

    }

    //metodos
    public void Comprar(){
        //Método que permite que el jugador compre dicha escritura.
    }

    public void Vender(){
        //Método que permite que el jugador venda dicha escritura.
    }

    public void pagarAlquiler(){
        //Método que obliga al jugador a pagarle al dueño el costo de alquiler por caer en dicho casillero.
    }

    public void cantidadCampos(){
        //Método que indica la cantidad de campos que hay.
    }

    public void cantidadChacras(){
        //Método que indica la cantidad de chacras que hay.
    }

    public void cantidadEstancias(){
        //Método que indica la cantidad de estancias que hay.
    }

}
