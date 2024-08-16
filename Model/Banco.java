package Model;

public class Banco {
    private Integer cantidadCampos;
    private Integer cantidadChacras;
    private Integer cantidadEstancias;
    private Integer cuenta;

    public Integer getCantidadCampos() {
        return cantidadCampos;
    }

    public void setCantidadCampos(Integer cantidadCampos) {
        this.cantidadCampos = cantidadCampos;
    }

    public Integer getCantidadChacras() {
        return cantidadChacras;
    }

    public void setCantidadChacras(Integer cantidadChacras) {
        this.cantidadChacras = cantidadChacras;
    }

    public Integer getCantidadEstancias() {
        return cantidadEstancias;
    }

    public void setCantidadEstancias(Integer cantidadEstancias) {
        this.cantidadEstancias = cantidadEstancias;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Banco() {
    }

    public Banco(Integer cantidadCampos, Integer cantidadChacras, Integer cantidadEstancias) {
        this.cantidadCampos = cantidadCampos;
        this.cantidadChacras = cantidadChacras;
        this.cantidadEstancias = cantidadEstancias;
    }

    public Integer venderCampos()
    {
        cantidadCampos--;
        return cantidadCampos;
    }
    public Integer venderChacras()
    {
        cantidadChacras--;
        return cantidadChacras;
    }
    public Integer venderEstancias()
    {
        cantidadEstancias--;
        return cantidadEstancias;
    }

}
