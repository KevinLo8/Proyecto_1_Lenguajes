package com.Proyecto_1.Backend.Analizador;

import java.util.ArrayList;

import com.Proyecto_1.Backend.Token.*;

public class AnalizadorHTML extends Analizador {

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
                    analizarLineaCierre(parte);
                } else {
                    analizarLineaApertura(parte);
                }
            } else if (parte.charAt(0) == '/' && parte.charAt(1) == '/') {
                String[] comentario = { parte, parte };
                agregarToken(comentario, "Comentario", "HTML");
            } else {
                String[] texto = { parte, parte };
                agregarToken(texto, "Texto", "HTML");
            }
        }

        return tokens;
    }

    private ArrayList<String> separarPartes(String linea) {
        ArrayList<String> partes = new ArrayList<>();

        String parte = null;
        index = -1;

        do {
            if (linea.charAt(index + 1) == ' ') {
                index++;
            } else if (linea.charAt(index + 1) == '<') {
                parte = extraerComando(linea);
                partes.add(parte);
            } else if (linea.charAt(index + 1) == '/' && linea.charAt(index + 2) == '/') {
                parte = extraerComentario(linea);
                partes.add(parte);
            } else {
                parte = extraerTexto(linea);
                partes.add(parte);
            }
        } while (index < linea.length() - 1);

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
            } while (linea.charAt(index) != '>');
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

    private void analizarLineaApertura(String parte) {
        TokenHTML tokenHTML = new TokenHTML();
        String palabra = "";

        index = 0;

        palabra = palabra + parte.charAt(index);
        String[] apertura = { palabra, palabra };

        agregarToken(apertura, "Etiqueta de Apertura", "HTML");

        do {

            while (parte.charAt(index + 1) == ' ') {
                index++;
            }

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
                agregarToken(traduccion, tipo, "HTML");
            } else {
                agregarError(palabra, "HTML");
            }
        } while (index < parte.length() - 2);

        index++;
        palabra = "" + parte.charAt(index);
        String[] cierre = { palabra, palabra };

        agregarToken(cierre, "Etiqueta de Cierre", "HTML");
    }

    private void analizarLineaCierre(String parte) {
        TokenHTML tokenHTML = new TokenHTML();
        String palabra = "";
        boolean tieneEtiqueta = false;

        index = 0;

        palabra = palabra + parte.charAt(index);
        String[] apertura = { palabra, palabra };

        agregarToken(apertura, "Etiqueta de Apertura", "HTML");

        do {

            while (parte.charAt(index + 1) == ' ') {
                index++;
            }

            palabra = extraerPalabra(parte);

            if (tokenHTML.esEtiqueta(palabra)) {
                String[] traduccion = tokenHTML.retornarTraduccion(palabra);
                agregarToken(traduccion, "Etiqueta", "HTML");
                tieneEtiqueta = true;
            } else {
                agregarError(palabra, "HTML");
            }
        } while (!tieneEtiqueta && index < parte.length() - 2);

        while (index < parte.length() - 2 && parte.charAt(index + 1) != '/') {
            palabra = extraerPalabra(parte);
            agregarError(palabra, "HTML");
        }

        palabra = "";
        for (int i = 0; i < 2; i++) {
            palabra = palabra + parte.charAt(index);
            index++;
        }
        String[] cierre = { palabra, palabra };

        agregarToken(cierre, "Etiqueta de Cierre", "HTML");
    }

    @Override
    protected boolean esFinalPalabra(char car, char carSig) {
        return carSig != ' ' && carSig != '=' && carSig != '/' && carSig != '>' && car != '=';
    }
}