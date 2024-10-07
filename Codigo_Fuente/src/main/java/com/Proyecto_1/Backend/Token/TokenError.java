package com.Proyecto_1.Backend.Token;

public class TokenError {

    private String token;
    private String lenguajeEncontrado;
    private String lenguajeSugerido;
    private int fila;
    private int columna;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLenguajeEncontrado() {
        return lenguajeEncontrado;
    }

    public void setLenguajeEncontrado(String lenguajeEncontrado) {
        this.lenguajeEncontrado = lenguajeEncontrado;
    }

    public String getLenguajeSugerido() {
        return lenguajeSugerido;
    }

    public void setLenguajeSugerido(String lenguajeSugerido) {
        this.lenguajeSugerido = lenguajeSugerido;
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
