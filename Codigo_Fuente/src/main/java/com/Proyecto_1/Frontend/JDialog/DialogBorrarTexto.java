package com.Proyecto_1.Frontend.JDialog;

import javax.swing.*;

import com.Proyecto_1.Backend.ActionListeners.ActionListenerBorrar;
import com.Proyecto_1.Frontend.FramePrincipal;

public class DialogBorrarTexto extends JDialog {

    FramePrincipal frame;

    public DialogBorrarTexto(FramePrincipal frame, String texto) {
        super(frame);
        this.frame = frame;

        initComponets(texto);

    }

    private void initComponets(String texto) {

        JLabel lbl1 = new JLabel("Quiere borrar el texto escrito:");

        JButton btn1 = new JButton("Si");
        JButton btn2 = new JButton("No");

        btn1.addActionListener(new ActionListenerBorrar(frame, texto));
        btn2.addActionListener(new ActionListenerBorrar(frame, texto));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(lbl1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn1)
                        .addGap(40)
                        .addComponent(btn2)
                    )
                )
                .addContainerGap(20, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(lbl1)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(btn1)
                    .addComponent(btn2)
                )
                .addContainerGap(20, Short.MAX_VALUE)
        );

        pack();

        int pos_X = frame.getLocationOnScreen().x + (frame.getWidth() - getWidth()) / 2;
        int pos_Y = frame.getLocationOnScreen().y + (frame.getHeight() - getHeight()) / 2;
        setLocation(pos_X, pos_Y);

    }
}
