package Model;

public class Jugadores {
    private Integer cuenta;
    private Peon peon;
    private Perfil perfil;

    public Jugadores(Integer cuenta, Peon peon, Perfil perfil) {
        this.cuenta = cuenta;
        this.peon = peon;
        this.perfil = perfil;
    }

    /*Ver cuando definan los constructores de:
     peon
     perfil
     */
    public Jugadores() {
        cuenta = 0; //deberian ser 35.000 (segun el pdf todos los jugadores empiezan con 35k)
        peon = new Peon();
        perfil = new Perfil();
    }

    public Integer getCuenta() {
        return cuenta;
    }
    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Perfil getPerfil() {
        return perfil;
    }
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Peon getPeon() {
        return peon;
    }
    public void setPeon(Peon peon) {
        this.peon = peon;
    }

    public void mejorar(){
        //Codigo
    }
    public void hipotecar(){
        //Codigo
    }
}

