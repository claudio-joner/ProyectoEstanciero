package com.example.demo.Model;

import lombok.Data;

@Data
public class Banco {

    private static class BancoSingleton
    {
        private static Banco INSTANCE = new Banco();
        private static void resetInstance(){
            INSTANCE = new Banco();
        };
    }
    public static Banco getBancoInstance()
    {
        return BancoSingleton.INSTANCE;
    }
    private Long id;
    private Integer cantidadChacras;
    private Integer cantidadEstancias;
    private Integer cantidadPropiedades;

    private Banco()
    {
        id=0L;
        cantidadChacras = 32; //Cantidad Establecida
        cantidadEstancias = 12; //Cantidad Establecida
        cantidadPropiedades=29;
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

    /**
     * Reinicia la instancia del banco.
     * Utilizar para reiniciar una partida
     */
    public void reiniciarBanco() {
        BancoSingleton.resetInstance();
    }

}
