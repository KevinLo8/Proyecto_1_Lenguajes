package com.Proyecto_1.Backend.Token;

public class TokenHTML {
    private final String[] MAIN = { "principal", "main" };
    private final String[] HEADER = { "encabezado", "header" };
    private final String[] NAV = { "navegacion", "nav" };
    private final String[] ASIDE = { "apartado", "aside" };
    private final String[] UL = { "listaordenada", "ul" };
    private final String[] OL = { "listadesordenada", "ol" };
    private final String[] LI = { "itemlista", "li" };
    private final String[] A = { "anclaje", "a" };
    private final String[] DIV = { "contenedor", "div" };
    private final String[] SECTION = { "seccion", "section" };
    private final String[] ARTICLE = { "articulo", "article" };
    private final String[] H = { "titulo", "h" };
    private final String[] P = { "parrafo", "p" };
    private final String[] SPAN = { "span", "span" };
    private final String[] PALABRA_RESERVADA = { "class", "=", "href", "onClick", "id", "style", "type", "placeholder",
            "required", "name" };

    public String[] retornarTraduccion(String palabra) {
        if (palabra.contains(MAIN[0])) {
            return MAIN;
        } else if (palabra.contains(HEADER[0])) {
            return HEADER;
        } else if (palabra.contains(NAV[0])) {
            return NAV;
        } else if (palabra.contains(ASIDE[0])) {
            return ASIDE;
        } else if (palabra.contains(UL[0])) {
            return UL;
        } else if (palabra.contains(OL[0])) {
            return OL;
        } else if (palabra.contains(LI[0])) {
            return LI;
        } else if (palabra.contains(A[0])) {
            return A;
        } else if (palabra.contains(DIV[0])) {
            return DIV;
        } else if (palabra.contains(SECTION[0])) {
            return SECTION;
        } else if (palabra.contains(ARTICLE[0])) {
            return ARTICLE;
        } else if (palabra.contains(H[0])) {
            return H;
        } else if (palabra.contains(P[0])) {
            return P;
        } else if (palabra.contains(SPAN[0])) {
            return SPAN;
        } else if (esPalabraReservada(palabra) || esCadena(palabra)) {
            String[] retorno = { palabra, palabra };
            return retorno;
        } else {
            return null;
        }
    }

    public boolean pertenece(String palabra) {
        return esEtiqueta(palabra) || esPalabraReservada(palabra) || esCadena(palabra);
    }

    public boolean esEtiqueta(String palabra) {
        if (palabra.contains(MAIN[0]) || palabra.contains(HEADER[0]) || palabra.contains(NAV[0])) {
            return true;
        } else if (palabra.contains(ASIDE[0]) || palabra.contains(UL[0]) || palabra.contains(OL[0])) {
            return true;
        } else if (palabra.contains(LI[0]) || palabra.contains(A[0]) || palabra.contains(DIV[0])) {
            return true;
        } else if (palabra.contains(SECTION[0]) || palabra.contains(ARTICLE[0]) || palabra.contains(H[0])) {
            return true;
        } else if (palabra.contains(P[0]) || palabra.contains(SPAN[0])) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean esPalabraReservada(String palabra) {
        for (int i = 0; i < PALABRA_RESERVADA.length; i++) {
            if (palabra.equals(PALABRA_RESERVADA[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esCadena(String palabra) {
        int tamaño = palabra.length();
        return palabra.charAt(0) == '"' && palabra.charAt(tamaño - 1) == '"';
    }
}
