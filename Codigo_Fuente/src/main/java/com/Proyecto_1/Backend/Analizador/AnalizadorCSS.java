package com.Proyecto_1.Backend.Analizador;

import java.util.ArrayList;

import com.Proyecto_1.Backend.Token.*;

public class AnalizadorCSS extends Analizador {
    private int indexPalabra;

    public ArrayList<Token> separarTokensLinea(String linea, int numeroLinea) {
        this.numeroLinea = numeroLinea;
        tokens = new ArrayList<>();
        columna = 1;

        if (linea.contains("{")) {
            analizarLineaApertura(linea);
        } else if (linea.contains("}")) {
            analizarLineaCierre(linea);
        } else {
            analizarLineaReglas(linea);
        }

        return tokens;
    }

    private void analizarLineaApertura(String parte) {
        String palabra = "";
        boolean tieneEtiqueta = false;

        index = 0;

        separarTokensEtiqueta(parte);

        do {
            palabra = extraerPalabra(parte);

            if (palabra.equals("{") && !tieneEtiqueta) {
                String[] traduccion = { palabra, palabra };
                agregarToken(traduccion, "Etiqueta", "CSS");
                tieneEtiqueta = true;
            } else {
                agregarError(palabra, "CSS");
            }
        } while (!tieneEtiqueta && index < parte.length() - 2);
    }

    private void analizarLineaReglas(String parte) {
        TokenCSS tokenCSS = new TokenCSS();
        String palabra = "";
        boolean tieneEtiqueta = false;

        index = 0;

        do {
            if (tokenCSS.esNumero(palabra.charAt(index))) {
                palabra = extraerNumero(parte, tokenCSS);
            } else {
                palabra = extraerPalabra(parte);
            }

            if (palabra.equals("rgba")) {
                palabra = extraerParantesis(parte, palabra);
            }

            extraerTokenRegla(palabra, tokenCSS);

        } while (!tieneEtiqueta && index < parte.length() - 2);
    }

    private void analizarLineaCierre(String parte) {
        String palabra = "";
        boolean tieneEtiqueta = false;

        index = 0;

        do {
            palabra = extraerPalabra(parte);

            if (palabra.equals("}") && !tieneEtiqueta) {
                String[] traduccion = { palabra, palabra };
                agregarToken(traduccion, "Etiqueta", "CSS");
                tieneEtiqueta = true;
            } else {
                agregarError(palabra, "CSS");
            }
        } while (!tieneEtiqueta && index < parte.length() - 2);
    }

    private void separarTokensEtiqueta(String parte) {
        String etiqueta = "";
        char caracter;

        do {
            index++;
            caracter = parte.charAt(index);
            etiqueta = etiqueta + caracter;
        } while (parte.charAt(index + 1) == '{');

        while (etiqueta.charAt(etiqueta.length() - 1) == ' ') {
            etiqueta = etiqueta.substring(0, etiqueta.length() - 1);
        }

        indexPalabra = -1;
        do {
            if (etiqueta.charAt(indexPalabra + 1) != ' ') {
                extraerTokenEtiqueta(etiqueta);
            } else {
                extraerTokenCombinador(etiqueta);
            }
        } while (indexPalabra < etiqueta.length() - 1);
    }

    private void extraerTokenEtiqueta(String parte) {
        TokenCSS tokenCSS = new TokenCSS();
        String palabra = "";
        char caracter;

        do {
            indexPalabra++;
            caracter = parte.charAt(indexPalabra);
            palabra = palabra + caracter;
        } while (esFinalPalabra(parte.charAt(indexPalabra), parte.charAt(indexPalabra + 1)));

        String[] traduccion = { palabra, palabra };
        if (tokenCSS.esNombreDeClase(palabra)) {
            agregarToken(traduccion, "Nombre de Clase", "CSS");
        } else if (tokenCSS.esNombreDeId(palabra)) {
            agregarToken(traduccion, "Nombre de Id", "CSS");
        } else if (tokenCSS.esUniversal(palabra)) {
            agregarToken(traduccion, "Universal", "CSS");
        } else if (tokenCSS.esIdentificador(palabra)) {
            agregarToken(traduccion, "Identificador", "CSS");
        } else if (tokenCSS.esEtiqueta(palabra)) {
            agregarToken(traduccion, "Etiqueta", "CSS");
        } else if (tokenCSS.esOtro(palabra)) {
            agregarToken(traduccion, "Otro", "CSS");
        } else if (tokenCSS.esOtroCorchete(palabra)) {
            agregarToken(traduccion, "Otro", "CSS");
        } else {
            agregarError(palabra, "CSS");
        }
    }

    private void extraerTokenCombinador(String parte) {
        TokenCSS tokenCSS = new TokenCSS();
        String palabra = "" + parte.charAt(indexPalabra + 2);

        if (tokenCSS.esCombinador(palabra)) {
            String[] traduccion = { palabra, palabra };
            agregarToken(traduccion, "Combinador", "CSS");
            indexPalabra += 3;
        } else {
            palabra = " ";
            String[] traduccion = { palabra, palabra };
            agregarToken(traduccion, "Combinador", "CSS");
            indexPalabra++;
        }
    }

    private String extraerNumero(String parte, TokenCSS tokenCSS) {
        String palabra = "";
        char caracter;

        do {
            index++;
            caracter = parte.charAt(index);
            palabra = palabra + caracter;
        } while (tokenCSS.esNumero(palabra.charAt(index + 1)));

        return palabra;
    }

    private String extraerParantesis(String parte, String palabra) {
        char caracter;

        do {
            index++;
            caracter = parte.charAt(index);
            palabra = palabra + caracter;
        } while (palabra.charAt(index + 1) != ')');

        return palabra;
    }

    private void extraerTokenRegla(String palabra, TokenCSS tokenCSS) {
        String[] traduccion = { palabra, palabra };
        if (tokenCSS.esRegla(palabra)) {
            agregarToken(traduccion, "Regla", "CSS");
        } else if (tokenCSS.esOtro(palabra)) {
            agregarToken(traduccion, "Otro", "CSS");
        } else if (tokenCSS.esCadena(palabra)) {
            agregarToken(traduccion, "Cadena", "CSS");
        } else if (tokenCSS.esColor(palabra)) {
            agregarToken(traduccion, "Color", "CSS");
        } else if (tokenCSS.esIdentificador(palabra)) {
            agregarToken(traduccion, "Identificador", "CSS");
        } else if (tokenCSS.esEntero(palabra)) {
            agregarToken(traduccion, "Entero", "CSS");
        } else {
            agregarError(palabra, "CSS");
        }
    }

    @Override
    protected boolean esFinalPalabra(char car, char carSig) {
        return carSig == ' ' || (carSig == ':' && car != ':') || carSig == '.' || carSig == '#' || carSig == ','
                || carSig == ';' || car == '{' || car == '}' || car == '(' || car == ')' || carSig == '('
                || carSig == ')';
    }
}
