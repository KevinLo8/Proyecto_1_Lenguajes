package com.Proyecto_1.Backend.Analizador;

import java.util.ArrayList;

import com.Proyecto_1.Backend.Token.*;

public class AnalizadorJS extends Analizador {

    public ArrayList<Token> separarTokensLinea(String linea, int numeroLinea) {
        this.numeroLinea = numeroLinea;
        tokens = new ArrayList<>();

        TokenJS tokenJS = new TokenJS();
        String palabra = "";

        index = -1;

        do {

            while (linea.charAt(index + 1) == ' ') {
                index++;
            }

            if (tokenJS.esNumero(linea.charAt(index + 1))) {
                palabra = extraerNumero(linea, tokenJS);
            } else if (tokenJS.esPrincipoCadena(linea.charAt(index + 1))) {
                palabra = extraerCadena(linea, tokenJS);
            } else if (linea.charAt(index + 1) == '/' && linea.charAt(index + 2) == '/') {
                palabra = extraerComentario(linea);
            } else {
                palabra = extraerPalabra(linea);
                if (palabra.equals("console")) {
                    palabra = extraerResto(palabra, linea);
                }
            }

            extraerToken(palabra, tokenJS);

        } while (index < linea.length() - 1);

        return tokens;
    }

    private String extraerResto(String palabra, String linea) {

        String resto = extraerPalabra(linea);

        return palabra + resto;
    }

    private String extraerNumero(String parte, TokenJS tokenJS) {
        String palabra = "";
        char caracter;

        do {
            index++;
            caracter = parte.charAt(index);
            palabra = palabra + caracter;
        } while (tokenJS.esNumero(palabra.charAt(index + 1)) || palabra.charAt(index + 1) == '.');

        return palabra;
    }

    private String extraerCadena(String parte, TokenJS tokenJS) {
        String palabra = "";
        char caracter;

        index++;
        caracter = parte.charAt(index);
        palabra = palabra + caracter;

        do {
            index++;
            caracter = parte.charAt(index);
            palabra = palabra + caracter;
        } while (!tokenJS.esPrincipoCadena(parte.charAt(index)));

        return palabra;
    }

    private void extraerToken(String palabra, TokenJS tokenJS) {
        String[] traduccion = { palabra, palabra };
        if (tokenJS.esAritmerico(palabra)) {
            agregarToken(traduccion, "Aritmetico", "JS");
        } else if (tokenJS.esRelacional(palabra)) {
            agregarToken(traduccion, "Relacional", "JS");
        } else if (tokenJS.esLogico(palabra)) {
            agregarToken(traduccion, "Lógico", "JS");
        } else if (tokenJS.esIncremental(palabra)) {
            agregarToken(traduccion, "Incremental", "JS");
        } else if (tokenJS.esTipoDeDato(palabra)) {
            agregarToken(traduccion, "Tipo de Dato", "JS");
        } else if (tokenJS.esPalabraReservada(palabra)) {
            agregarToken(traduccion, "Palabra Reservada", "JS");
        } else if (tokenJS.esOtro(palabra)) {
            agregarToken(traduccion, "Otro", "JS");
        } else if (tokenJS.esIdentificador(palabra)) {
            agregarToken(traduccion, "Identificador", "JS");
        } else if (palabra.charAt(0) == '/' && palabra.charAt(1) == '/') {
            String[] comentario = { palabra, palabra };
            agregarToken(comentario, "Comentario", "JS");
    } else {
            agregarError(palabra, "JS");
        }
    }

    @Override
    protected boolean esFinalPalabra(char car, char carSig) {
        return carSig != ' ' && carSig != '(' && carSig != ')' && carSig != ';' && carSig != '.' && carSig != ','
                && car != '(' && car != ')' && car != '.' && carSig != 10;
    }
}
