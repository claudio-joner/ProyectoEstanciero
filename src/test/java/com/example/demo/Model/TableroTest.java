package com.example.demo.Model;

import com.example.demo.Model.Enums.tipoEspecial;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    @Test
    public void testMatchRegex_ForInputIncorrect() {
        Tablero tablero = Tablero.getTableroInstance();
        String input = "Yes";

        assertFalse(tablero.matchRegex(input));
    }

    @Test
    public void testMatchRegex_ForInputYorN() {
        Tablero tablero = Tablero.getTableroInstance();
        String input = "Y";

        assertTrue(tablero.matchRegex(input));
    }

}