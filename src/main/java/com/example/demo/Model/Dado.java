package com.example.demo.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor public class Dado {
    int valorDadoA = 0;
    int valorDadoB = 0;
    public int getValorDadoA() {
        return valorDadoA;
    }
    public void setValorDadoA(int valorDadoA) {
        this.valorDadoA = valorDadoA;
    }
    public int getValorDadoB() {
        return valorDadoB;
    }
    public void setValorDadoB(int valorDadoB) {
        this.valorDadoB = valorDadoB;
    }
    public Integer tirarDados() {valorDadoA = (int)(Math.random() * 6) + 1;
        valorDadoB = (int)(Math.random() * 6) + 1;
        Integer valorAvance = valorDadoA + valorDadoB;
        return valorAvance;}
    public Boolean compararValores() {if(valorDadoA == valorDadoB) {
            return true;}
        return false;}}