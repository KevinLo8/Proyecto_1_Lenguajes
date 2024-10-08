package com.Proyecto_1.Backend.Analizador;

import java.util.ArrayList;

import com.Proyecto_1.Backend.Token.Token;

public abstract class Analizador {

    protected ArrayList<Token> tokens;
    protected int index;
    protected int columna;
    protected int numeroLinea;

    protected void agregarToken(String[] traduccion, String tipo, String lenguaje) {
        Token token = new Token();
        token.setToken(traduccion[0]);
        token.setExpresionRegular(traduccion[1]);
        token.setLenguaje(lenguaje);
        token.setTipo(tipo);
        token.setFila(numeroLinea);
        token.setColumna(columna);

        tokens.add(token);

        columna++;
    }

    protected String extraerPalabra(String parte) {
        String palabra = "";
        char caracter;

        do {
            index++;
            caracter = parte.charAt(index);
            palabra = palabra + caracter;
        } while (esFinalPalabra(parte.charAt(index), parte.charAt(index + 1)));

        return palabra;
    }

    protected void agregarError(String palabra, String lenguaje) {
        String[] error = { palabra, palabra };
        agregarToken(error, "Error", lenguaje);
    }

    protected abstract boolean esFinalPalabra(char car, char carSig);
}
