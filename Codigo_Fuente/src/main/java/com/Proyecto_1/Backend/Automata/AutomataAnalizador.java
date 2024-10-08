package com.Proyecto_1.Backend.Automata;

import java.util.ArrayList;

import com.Proyecto_1.Backend.Analizador.*;
import com.Proyecto_1.Backend.Token.*;

public class AutomataAnalizador {

    private final String[] TOKEN_DE_ESTADO = { ">>[html]", ">>[css]", ">>[js]" };
    private int numeroLinea;
    private ArrayList<Token> tokens;
    private ArrayList<Token> optimizacion;
    private ArrayList<TokenError> errores;

    public AutomataAnalizador() {
        numeroLinea = 1;
        tokens = new ArrayList<>();
        optimizacion = new ArrayList<>();
        errores = new ArrayList<>();
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public ArrayList<Token> getOptimizacion() {
        return optimizacion;
    }

    public ArrayList<TokenError> getErrores() {
        return errores;
    }

    public void analizarParrafo(String parrafo, String tipo) {
        String[] lineas = parrafo.split("\\n");

        if (tipo.equals("ERROR")) {
            for (int i = 0; i < lineas.length; i++) {
                int index = 0;
                int columna = 0;
        
                while (index < lineas[i].length()) {
                    if (lineas[i].charAt(index) == ' ') {
                        index++;
                    } else {
                        index += crearTokenError(lineas[i], columna, index, "");
                    }
                }
        
                numeroLinea++;
            }
        } else {
            crearTokenEstado(parrafo);
            for (int i = 1; i < lineas.length; i++) {
                if (lineas[i].length() > 0) {
                    ArrayList<Token> tokensLinea = null;
                    switch (tipo) {
                        case "HTML":
                            AnalizadorHTML analizadorHTML = new AnalizadorHTML();
                            tokensLinea = analizadorHTML.separarTokensLinea(lineas[i], numeroLinea);
                            break;
                        case "CSS":
                            AnalizadorCSS analizadorCSS = new AnalizadorCSS();
                            tokensLinea = analizadorCSS.separarTokensLinea(lineas[i], numeroLinea);
    
                            break;
                        case "JS":
                            /*
                             * AnalizadorJS analizadorJS = new AnalizadorJS();
                             * tokensLinea = analizadorJS.separarTokensLinea(lineas[i], numeroLinea);
                             */
                            break;
                    }
    
                    if (contieneComentario(tokensLinea)) {
                        optimizacion.addAll(tokensLinea);
                    } else {
                        for (int j = 0; j < tokensLinea.size(); j++) {
                            Token token = tokensLinea.get(j);
                            if (token.getTipo() == "Error") {
                                TokenError tokenError = tokenATokenError(token);
                                errores.add(tokenError);
                            } else {
                                tokens.add(token);
                            }
                        }
                    }
                }
    
                numeroLinea++;
            }    
        }
    }

    public boolean esTokenEstado(String palabra) {
        for (int i = 0; i < 3; i++) {
            if (palabra == TOKEN_DE_ESTADO[i]) {
                return true;
            }
        }
        return false;
    }

    private void crearTokenEstado(String linea) {
        String palabra = extraerPalabraEstado(linea);
        int index = 0;
        int columna = 0;
        Token token = new Token();

        token.setToken(palabra);
        token.setExpresionRegular(palabra);
        token.setFila(numeroLinea);
        token.setColumna(1);
        token.setTipo("Estado");

        tokens.add(token);

        while (index < linea.length()) {
            if (linea.charAt(index) == ' ') {
                index++;
            } else {
                index += crearTokenError(linea, columna, index, "");
            }
        }

        numeroLinea++;
    }

    private int crearTokenError(String linea, int columna, int index, String lenguaje) {
        String palabra = extraerPalabraErronea(linea, index);

        TokenError token = new TokenError();
        token.setToken(palabra);
        token.setLenguajeEncontrado(lenguaje);
        token.setFila(numeroLinea);
        token.setColumna(columna);

        errores.add(token);

        return palabra.length();
    }

    public String extraerPalabraEstado(String linea) {

        linea = linea.trim();

        String palabra = "";
        int index = 0;
        char caracter;

        do {
            index++;
            caracter = linea.charAt(index);
            palabra = palabra + caracter;
        } while (caracter == ' ');

        return palabra;
    }

    private String extraerPalabraErronea(String linea, int index) {
        String palabra = "";
        char caracter;
        try {
            do {
                index++;
                caracter = linea.charAt(index);
                palabra = palabra + caracter;
            } while (linea.charAt(index) != '_');
        } catch (IndexOutOfBoundsException e) {
        }
        return palabra;
    }

    private boolean contieneComentario(ArrayList<Token> tokensLinea) {

        for (int i = 0; i < tokensLinea.size(); i++) {
            Token token = tokensLinea.get(i);
            if (token.getTipo().equals("Comentario")) {
                return true;
            }
        }
        return false;
    }

    private TokenError tokenATokenError(Token token) {
        TokenError tokenError = new TokenError();

        tokenError.setToken(token.getToken());
        tokenError.setLenguajeEncontrado(token.getLenguaje());
        tokenError.setFila(token.getFila());
        tokenError.setColumna(token.getColumna());

        return tokenError;
    }
}