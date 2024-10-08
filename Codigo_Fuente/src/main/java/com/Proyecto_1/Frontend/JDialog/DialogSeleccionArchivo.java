package com.Proyecto_1.Frontend.JDialog;

import java.awt.*;

import javax.swing.*;

import com.Proyecto_1.Backend.ActionListeners.*;
import com.Proyecto_1.Frontend.FramePrincipal;

public class DialogSeleccionArchivo extends JDialog {

    FramePrincipal frame;

    public DialogSeleccionArchivo(FramePrincipal frame) {
        super(frame);
        this.frame = frame;

        initComponets();

    }

    private void initComponets() {

        JTextField txf1 = new JTextField();
        txf1.setPreferredSize(new Dimension(300, 25));

        JButton btn1 = new JButton("Seleccionar");
        JButton btn2 = new JButton("Cargar");

        JLabel lbl1 = new JLabel(" ");
        lbl1.setForeground(new Color(255, 0, 0));

        String[] numeros = new String[19];

        for (int i = 2; i < 21; i++) {
            numeros[i - 2] = String.valueOf(i);
        }

        btn1.addActionListener(new ActionListenerSeleccionar(txf1));
        btn2.addActionListener(new ActionListenerCargar(frame, lbl1, txf1));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txf1)
                        .addComponent(btn1)
                    )
                    .addComponent(lbl1)
                    .addComponent(btn2)
                )
                .addContainerGap(20, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(txf1)
                    .addComponent(btn1)
                )
                .addComponent(lbl1)
                .addGap(10)
                .addComponent(btn2)
                .addContainerGap(20, Short.MAX_VALUE)
        );

        pack();

        int pos_X = frame.getLocationOnScreen().x + (frame.getWidth() - getWidth()) / 2;
        int pos_Y = frame.getLocationOnScreen().y + (frame.getHeight() - getHeight()) / 2;
        setLocation(pos_X, pos_Y);

    }
}
