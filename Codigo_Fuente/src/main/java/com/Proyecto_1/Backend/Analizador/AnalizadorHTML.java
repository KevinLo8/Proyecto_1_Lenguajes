package com.Proyecto_1.Backend.Analizador;

import java.util.ArrayList;

import com.Proyecto_1.Backend.Token.*;

public class AnalizadorHTML {
    private ArrayList<Token> tokens;
    private int index;
    private int columna;
    private int numeroLinea;

    public ArrayList<Token> separarTokensLinea(String linea, int numeroLinea) {
        this.numeroLinea = numeroLinea;
        tokens = new ArrayList<>();
        String parte;

        ArrayList<String> partes = separarPartes(linea);

        for (int i = 0; i < partes.size(); i++) {
            columna = 1;
            parte = partes.get(i);
            if (parte.charAt(0) == '<') {
                if (parte.charAt(parte.length() - 2) == '/') {
                    analizarLineaCierre(parte, numeroLinea, columna);
                } else {
                    analizarLineaApertura(parte, numeroLinea, columna);
                }
            } else if (parte.charAt(0) == '/' && parte.charAt(1) == '/') {
                String[] comentario = { parte, parte };
                agregarToken(comentario, "Comentario");
            } else {
                String[] texto = { parte, parte };
                agregarToken(texto, "Texto");
            }
        }

        return tokens;
    }

    private ArrayList<String> separarPartes(String linea) {
        ArrayList<String> partes = new ArrayList<>();

        String parte = null;
        index = 0;

        do {
            if (linea.charAt(index) == '<') {
                parte = extraerComando(linea);
                partes.add(parte);
            } else if (linea.charAt(index) == '/' && linea.charAt(index + 1) == '/') {
                parte = extraerComentario(linea);
                partes.add(parte);
            } else {
                parte = extraerTexto(linea);
                partes.add(parte);
            }
        } while (index < linea.length());

        return partes;
    }

    private String extraerComando(String linea) {
        String palabra = "";
        char caracter;
        try {
            do {
                index++;
                caracter = linea.charAt(index);
                palabra = palabra + caracter;
            } while (linea.charAt(index) == '>');
        } catch (IndexOutOfBoundsException e) {
        }
        return palabra;
    }

    private String extraerComentario(String linea) {
        String palabra = "";
        char caracter;
        try {
            do {
                index++;
                caracter = linea.charAt(index);
                palabra = palabra + caracter;
            } while (true);
        } catch (IndexOutOfBoundsException e) {
        }
        return palabra;
    }

    private String extraerTexto(String linea) {
        String palabra = "";
        char caracter;

        try {
            do {
                index++;
                caracter = linea.charAt(index);
                palabra = palabra + caracter;
            } while (linea.charAt(index + 1) != '<');
        } catch (IndexOutOfBoundsException e) {
        }
        return palabra;
    }

    private void analizarLineaApertura(String parte, int numeroLinea, int columna) {
        TokenHTML tokenHTML = new TokenHTML();
        String palabra = "";

        index = 0;

        palabra = palabra + palabra.charAt(index);
        String[] apertura = { palabra, palabra };

        agregarToken(apertura, "Etiqueta de Apertura");

        do {
            palabra = extraerPalabra(parte);

            if (tokenHTML.pertenece(palabra)) {
                String tipo = null;
                if (tokenHTML.esPalabraReservada(palabra)) {
                    tipo = "Palabra Reservada";
                } else if (tokenHTML.esCadena(palabra)) {
                    tipo = "Cadena";
                } else {
                    tipo = "Etiqueta";
                }
                String[] traduccion = tokenHTML.retornarTraduccion(palabra);
                agregarToken(traduccion, tipo);
            } else {
                String[] error = { palabra, palabra };
                agregarToken(error, "Error");
            }
        } while (index > parte.length() - 1);

        palabra = palabra + palabra.charAt(index);
        String[] cierre = { palabra, palabra };

        agregarToken(cierre, "Etiqueta de Cierre");
    }

    private void analizarLineaCierre(String parte, int numeroLinea, int columna) {
        TokenHTML tokenHTML = new TokenHTML();
        String palabra = "";
        boolean tieneEtiqueta = false;

        index = 0;

        palabra = palabra + palabra.charAt(index);
        String[] apertura = { palabra, palabra };

        agregarToken(apertura, "Etiqueta de Apertura");

        do {
            palabra = extraerPalabra(parte);

            if (tokenHTML.esEtiqueta(palabra)) {
                String[] traduccion = tokenHTML.retornarTraduccion(palabra);
                agregarToken(traduccion, "Etiqueta");
                tieneEtiqueta = true;
            } else {
                String[] error = { palabra, palabra };
                agregarToken(error, "Error");
            }
        } while (!tieneEtiqueta && index < parte.length() - 2);

        do {
            palabra = extraerPalabra(parte);
            String[] error = { palabra, palabra };
            agregarToken(error, "Error");
        } while (index < parte.length() - 2);

        for (int i = 0; i < 2; i++) {
            palabra = palabra + palabra.charAt(index);
            index++;
        }
        String[] cierre = { palabra, palabra };

        agregarToken(cierre, "Etiqueta de Cierre");
    }

    private void agregarToken(String[] traduccion, String tipo) {
        Token token = new Token();
        token.setToken(traduccion[0]);
        token.setExpresionRegular(traduccion[1]);
        token.setLenguaje("HTML");
        token.setTipo(tipo);
        token.setFila(numeroLinea);
        token.setColumna(columna);

        tokens.add(token);

        columna++;
    }

    private String extraerPalabra(String parte) {
        String palabra = "";
        char caracter;

        do {
            index++;
            caracter = parte.charAt(index);
            palabra = palabra + caracter;
        } while (esFinalPalabra(parte.charAt(index + 1)));

        return palabra;
    }

    private boolean esFinalPalabra(char car) {
        return car == ' ' || car == '=' || car == '/' || car == '>';
    }
}
