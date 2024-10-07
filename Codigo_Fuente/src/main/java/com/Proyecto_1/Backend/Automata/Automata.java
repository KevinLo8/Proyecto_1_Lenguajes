package com.Proyecto_1.Backend.Automata;

import java.util.ArrayList;

import javax.swing.*;

import com.Proyecto_1.Backend.Analizador.Analizador;

public class Automata {

    private ArrayList<String> parrafos;
    private ArrayList<String> tipo;
    private Analizador analizador;

    public Automata() {
        parrafos = new ArrayList<>();
        tipo = new ArrayList<>();
        analizador = new Analizador();
    }

    public void analizarTexto(JTextArea txa) {

        String[] lineas = txa.getText().split("\\n");
        separarParrafos(lineas);

        analizarParrafos();
    }

    private void separarParrafos(String[] lineas) {

        String parrafo = "";
        int index = 0;

        while (index < lineas.length) {
            String primeraPalabra = analizador.extraerPalabraEstado(lineas[index]);

            if (analizador.esTokenEstado(primeraPalabra)) {
                if (parrafo.length() > 0) {
                    parrafos.add(parrafo);
                    agregarTipo(parrafo);
                    parrafo = "";
                }

                parrafo = parrafo + lineas[index];
            } else {
                parrafo = parrafo + lineas[index];
            }
            index++;
        }
    }

    private void agregarTipo(String parrafo) {
        String primeraPalabra = analizador.extraerPalabraEstado(parrafo);

        switch (primeraPalabra) {
            case ">>[html]":
                tipo.add("HTML");
                break;
            case ">>[css]":
                tipo.add("CSS");
                break;
            case ">>[js]":
                tipo.add("JS");
                break;
            default:
                tipo.add("ERROR");
                break;
        }
    }

    private void analizarParrafos() {
        for (int i = 0; i < parrafos.size(); i++) {
            switch (tipo.get(i)) {
                case "HTML":
                    analizador.analizarHTML(parrafos.get(i));
                    break;
                case "CSS":
                    analizador.analizarCSS(parrafos.get(i));
                    break;
                case "JS":
                    analizador.analizarJS(parrafos.get(i));
                    break;
                case "ERROR":
                    analizador.analizarJS(parrafos.get(i));
                    break;
            }
        }
    }

}
