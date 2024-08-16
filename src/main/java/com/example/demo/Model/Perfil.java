package Model;

import java.util.ArrayList;
import java.util.List;

public class Perfil {
    private List<String>provinciasPreferidas;
    public List<String> getProvinciasPreferidas() {
        return provinciasPreferidas;
    }

    public void setProvinciasPreferidas(List<String> provinciasPreferidas) {
        this.provinciasPreferidas = provinciasPreferidas;
    }

    public Perfil(List<String> provinciasPreferidas) {
        this.provinciasPreferidas = new ArrayList<>(provinciasPreferidas);
    }
    public Perfil(){
    }

    public boolean comprarPorPreferencias()
    {
        return true;
    }


}
