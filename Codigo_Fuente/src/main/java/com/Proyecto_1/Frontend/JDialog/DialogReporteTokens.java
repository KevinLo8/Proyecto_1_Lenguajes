package com.Proyecto_1.Frontend.JDialog;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;

import com.Proyecto_1.Backend.ActionListeners.*;
import com.Proyecto_1.Backend.Token.Token;
import com.Proyecto_1.Frontend.FramePrincipal;

public class DialogReporteTokens extends JDialog {

    FramePrincipal frame;
    ArrayList<Token> tokens;

    public DialogReporteTokens(FramePrincipal frame) {
        super(frame);
        this.frame = frame;

        tokens = frame.pedirTokens();
        
        initComponets();

    }

    private void initComponets() {

        JScrollPane scp1 = new JScrollPane();
        scp1.setPreferredSize(new Dimension(800, 400));

        String[] titulos = { "Token", "Expresión Regular", "Lenguaje", "Tipo", "Linea", "Columna" };

        String[][] data = new String[0][6];
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            data = agregarData(token, data);
        }

        JTable tbl1 = new JTable(data, titulos);

        scp1.setViewportView(tbl1);

        JButton btn1 = new JButton("Cerrar");

        btn1.addActionListener(new ActionListenerCerrar(frame));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(scp1)
                                        .addComponent(btn1))
                        .addContainerGap(20, Short.MAX_VALUE));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addComponent(scp1)
                        .addGap(20)
                        .addComponent(btn1)
                        .addContainerGap(20, Short.MAX_VALUE));

        pack();

        int pos_X = frame.getLocationOnScreen().x + (frame.getWidth() - getWidth()) / 2;
        int pos_Y = frame.getLocationOnScreen().y + (frame.getHeight() - getHeight()) / 2;
        setLocation(pos_X, pos_Y);

    }

    private String[][] agregarData(Token token, String[][] data) {
        String[][] retorno = new String[data.length + 1][6];

        for (int i = 0; i < data.length; i++) {
            retorno[i][0] = data[i][0];
            retorno[i][1] = data[i][1];
            retorno[i][2] = data[i][2];
            retorno[i][3] = data[i][3];
            retorno[i][4] = data[i][4];
            retorno[i][5] = data[i][5];
        }

        retorno[data.length][0] = token.getToken();
        retorno[data.length][1] = token.getExpresionRegular();
        retorno[data.length][2] = token.getLenguaje();
        retorno[data.length][3] = token.getTipo();
        retorno[data.length][4] = String.valueOf(token.getFila());
        retorno[data.length][5] = String.valueOf(token.getColumna());

        return retorno;
    }
}
