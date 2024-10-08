package com.Proyecto_1.Backend.Token;

public class TokenCSS {
    private final String UNIVERSAL = "*";
    private final String[] ETIQUETAS = { "body", "header", "main", "nav", "aside", "div", "ul", "ol", "li", "a", "h1",
            "h2", "h3", "h4", "h5", "h6", "p", "span", "label", "textarea", "button", "section", "article", "footer" };
    private final String[] REGLAS = { "color", "background-color", "background", "font-size", "font-weight",
            "font-family", "font-align", "width", "height", "min-width", "min-height", "max-width", "max-height",
            "display", "inline", "block", "inline-block", "flex", "grid", "none", "margin", "border", "padding",
            "content", "border-color", "border-style", "border-width", "border-top", "border-bottom", "border-left",
            "border-right", "box-sizing", "border-box", "position", "static", "relative", "absolute", "sticky", "fixed",
            "top", "bottom", "left", "right", "z-index", "justify-content", "align-items", "border-radius", "auto",
            "float", "list-style", "text-align", "box-shadow" };
    private final String[] OTROS = { "px", "%", "rem", "em", "vw", "vh", ":hover", ":active", ":not", ":nth-child",
            "odd", "even", "::before", "::after", ":", ";", ",", "'[A-Za-z]'", "(", ")", "{", "}" };
    private final String[] COMBINADORES = { ">", "+", "~" };

    public boolean pertenece(String palabra) {
        return esUniversal(palabra) || esEtiqueta(palabra) || esRegla(palabra) || esOtro(palabra)
                || esCadena(palabra);
    }

    public Boolean esUniversal(String palabra) {
        return palabra.equals(UNIVERSAL);
    }

    public Boolean esEtiqueta(String palabra) {
        for (int i = 0; i < ETIQUETAS.length; i++) {
            if (palabra.equals(ETIQUETAS[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esRegla(String palabra) {
        for (int i = 0; i < REGLAS.length; i++) {
            if (palabra.equals(REGLAS[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esOtro(String palabra) {
        for (int i = 0; i < OTROS.length; i++) {
            if (palabra.equals(OTROS[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esOtroCorchete(String palabra) {
        int tamaño = palabra.length();
        return palabra.charAt(0) == '[' && palabra.charAt(tamaño - 1) == ']';
    }

    public Boolean esCombinador(String palabra) {
        for (int i = 0; i < COMBINADORES.length; i++) {
            if (palabra.equals(COMBINADORES[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esNombreDeClase(String palabra) {
        if (palabra.charAt(0) != '.') {
            return false;
        }
        if (!esLetraMinuscula(palabra.charAt(1))) {
            return false;
        }
        for (int i = 2; i < palabra.length(); i++) {
            if (!esCaracterNombre(palabra.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public Boolean esNombreDeId(String palabra) {
        if (palabra.charAt(0) != '#') {
            return false;
        }
        if (!esLetraMinuscula(palabra.charAt(1))) {
            return false;
        }
        for (int i = 2; i < palabra.length(); i++) {
            if (!esCaracterNombre(palabra.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public Boolean esIdentificador(String palabra) {
        if (!esLetraMinuscula(palabra.charAt(0))) {
            return false;
        }
        for (int i = 1; i < palabra.length(); i++) {
            if (!esCaracterNombre(palabra.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public Boolean esColor(String palabra) {
        if (palabra.charAt(0) == '#') {
            if (palabra.length() == 4 || palabra.length() == 7) {
                for (int i = 1; i < palabra.length(); i++) {
                    if (!esNumeroHex(palabra.charAt(i))) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else if (palabra.charAt(0) == 'r') {
            if (palabra.substring(0, 5).equals("rgba(")) {
                int index = 5;

                for (int i = 0; i < 3; i++) {
                    while (palabra.charAt(index) == ' ') {
                        index++;
                    }

                    for (int j = 0; j < 3; j++) {
                        if (palabra.charAt(index) == ',' && j != 0) {
                            j = 3;
                        } else if (!esNumero(palabra.charAt(index))) {
                            return false;
                        }
                        index++;
                    }

                    if (palabra.charAt(index) != ',' && i > 2) {
                        return false;
                    }
                    index++;
                }

                if (palabra.charAt(index) == ',') {
                    index++;
                    while (palabra.charAt(index) == ' ') {
                        index++;
                    }

                    boolean tienePunto = false;
                    do {
                        if (palabra.charAt(index) == '.' && !tienePunto) {
                            tienePunto = true;
                        } else if (!esNumero(palabra.charAt(index))) {
                            return false;
                        }
                        index++;
                    } while (palabra.charAt(index + 1) != ')');
                } else if (palabra.charAt(index) != ')') {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public Boolean esEntero(String palabra) {
        for (int i = 0; i < palabra.length(); i++) {
            if (!esNumero(palabra.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private Boolean esCaracterNombre(char car) {
        return esLetraMinuscula(car) || esNumero(car) || Character.compare(car, '-') == 0;
    }

    private Boolean esLetraMinuscula(char car) {
        if (car > '`' && car < '{') {
            return true;
        } else {
            return false;
        }
    }

    public Boolean esNumero(char car) {
        if (car > '/' && car < ':') {
            return true;
        } else {
            return false;
        }
    }

    private Boolean esNumeroHex(char car) {
        return esNumero(car) || car == 'a' || car == 'b' || car == 'c' || car == 'd' || car == 'e' || car == 'f';
    }

    public Boolean esCadena(String palabra) {
        int tamaño = palabra.length();
        return palabra.charAt(0) == 39 && palabra.charAt(tamaño - 1) == 39;
    }
}
