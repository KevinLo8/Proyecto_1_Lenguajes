package com.Proyecto_1.Backend.Automata;

import java.util.ArrayList;

import javax.swing.JTextArea;

import com.Proyecto_1.Backend.Analizador.*;
import com.Proyecto_1.Backend.Token.*;

public class AutomataAnalizador {

    private final String[] TOKEN_DE_ESTADO = { ">>[html]", ">>[css]", ">>[js]" };
    private int numeroLinea;
    private ArrayList<Token> tokens;
    private ArrayList<Token> optimizacion;
    private ArrayList<TokenError> errores;
    private String textoOptimizado;

    public AutomataAnalizador() {
        numeroLinea = 1;
        tokens = new ArrayList<>();
        optimizacion = new ArrayList<>();
        errores = new ArrayList<>();
        textoOptimizado = "";
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
                        index = index + crearTokenError(lineas[i], columna, index, "");
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
                            AnalizadorJS analizadorJS = new AnalizadorJS();
                            tokensLinea = analizadorJS.separarTokensLinea(lineas[i], numeroLinea);
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
                        textoOptimizado = textoOptimizado + lineas[i] + "\n";
                    }
                }
    
                numeroLinea++;
            }    
        }

        textoOptimizado = textoOptimizado + "\n";
        numeroLinea++;
    }

    public boolean esTokenEstado(String palabra) {
        for (int i = 0; i < 3; i++) {
            if (palabra.equals(TOKEN_DE_ESTADO[i])) {
                return true;
            }
        }
        return false;
    }

    private void crearTokenEstado(String linea) {
        String[] lineas = linea.split("\\n");
        String palabra = extraerPalabraEstado(linea);
        int index = palabra.length();
        int columna = 0;
        Token token = new Token();

        token.setToken(palabra);
        token.setExpresionRegular(palabra);
        token.setFila(numeroLinea);
        token.setColumna(1);
        token.setTipo("Estado");

        tokens.add(token);

        textoOptimizado = textoOptimizado + palabra + "\n";

        while (index < lineas[0].length()) {
            if (linea.charAt(index) == ' ') {
                index++;
            } else {
                index += crearTokenError(linea, columna, index, "");
            }
        }

        numeroLinea++;
    }

    private int crearTokenError(String linea, int columna, int index, String lenguaje) {
        index--;
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
        int index = -1;
        char caracter;

        try {
            char car;
            do {
                index++;
                caracter = linea.charAt(index);
                palabra = palabra + caracter;
                car = linea.charAt(index + 1);
            } while (car != ' ' && car != '\n');
        } catch (Exception e) {
        }

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

    public void ponerOptimizacion(JTextArea txa) {
        txa.setText(textoOptimizado);
    }
}