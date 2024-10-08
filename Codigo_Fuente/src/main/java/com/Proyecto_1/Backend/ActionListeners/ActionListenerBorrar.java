package com.Proyecto_1.Backend.ActionListeners;

import java.awt.event.*;

import com.Proyecto_1.Frontend.FramePrincipal;

public class ActionListenerBorrar implements ActionListener {

    private FramePrincipal frame;
    private String texto;

    public ActionListenerBorrar(FramePrincipal frame, String texto) {
        this.frame = frame;
        this.texto = texto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.borrarTextArea(texto, e.getActionCommand());
    }

}
