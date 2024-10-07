package com.Proyecto_1.Backend.Token;

public class Token {

    private String token;
    private String expresionRegular;
    private String lenguaje;
    private String tipo;
    private int fila;
    private int columna;
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getExpresionRegular() {
        return expresionRegular;
    }
    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }
    public String getLenguaje() {
        return lenguaje;
    }
    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getFila() {
        return fila;
    }
    public void setFila(int fila) {
        this.fila = fila;
    }
    public int getColumna() {
        return columna;
    }
    public void setColumna(int columna) {
        this.columna = columna;
    }
    
}
