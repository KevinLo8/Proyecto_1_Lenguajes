package com.Proyecto_1.Backend.Token;

public class TokenJS {
    private final String[] ARITMETICOS = { "+", "-", "*", "/" };
    private final String[] RELACIONALES = { "==", "<", ">", "<=", ">=", "!=" };
    private final String[] LOGICOS = { "||", "&&", "!" };
    private final String[] INCREMENTALES = { "++", "--" };
    private final String[] TIPOS_DE_DATOS = { "true", "false" };
    private final String[] PALABRA_RESERVADA = { "function", "const", "let", "document", "event", "alert", "for",
            "while", "if", "else", "return", "console.log", "null" };
    private final String[] OTROS = { "(", ")", "[", "]", "{", "}", "=", ";", ",", ".", ":", "=>" };

    public Boolean esIdentificador(String palabra) {

        if (!esLetra(palabra.charAt(0))) {
            return false;
        }

        for (int i = 1; i < palabra.length(); i++) {
            if (!esLetra(palabra.charAt(i)) && !esNumero(palabra.charAt(i)) && palabra.charAt(i) != '_') {
                return false;
            }
        }

        return true;
    }

    public Boolean esAritmerico(String palabra) {
        for (int i = 0; i < ARITMETICOS.length; i++) {
            if (palabra.equals(ARITMETICOS[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esRelacional(String palabra) {

        for (int i = 0; i < RELACIONALES.length; i++) {
            if (palabra.equals(RELACIONALES[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esLogico(String palabra) {
        for (int i = 0; i < LOGICOS.length; i++) {
            if (palabra.equals(LOGICOS[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esIncremental(String palabra) {
        for (int i = 0; i < INCREMENTALES.length; i++) {
            if (palabra.equals(INCREMENTALES[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esTipoDeDato(String palabra) {

        if (esEnteroODecimal(palabra)) {
            return true;
        }

        if (esCadena(palabra)) {
            return true;
        }

        for (int i = 0; i < TIPOS_DE_DATOS.length; i++) {
            if (palabra.equals(TIPOS_DE_DATOS[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean esPalabraReservada(String palabra) {
        for (int i = 0; i < PALABRA_RESERVADA.length; i++) {
            if (palabra.equals(PALABRA_RESERVADA[i])) {
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

    private Boolean esEnteroODecimal(String palabra) {
        boolean tienePunto = false;

        if (!esNumero(palabra.charAt(0))) {
            return false;
        }

        for (int i = 1; i < palabra.length(); i++) {
            if (palabra.charAt(i) == '.') {
                if (!tienePunto) {
                    tienePunto = true;
                } else {
                    return false;
                }
            } else if (!esNumero(palabra.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private Boolean esCadena(String palabra) {
        int size = palabra.length();
        switch (palabra.charAt(0)) {
            case '"':
                return palabra.charAt(0) == '"' && palabra.charAt(size - 1) == '"';
            case '`':
                return palabra.charAt(0) == '`' && palabra.charAt(size - 1) == '`';
            case 39:
                return palabra.charAt(0) == 39 && palabra.charAt(size - 1) == 39;
        }
        return false;
    }

    private Boolean esLetra(char car) {
        if (car > '@' && car < '[') {
            return true;
        } else if (car > '`' && car < '{') {
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

    public Boolean esPrincipoCadena(char car) {
        return car == 39 || car == '"' || car == '`';
    }

}
