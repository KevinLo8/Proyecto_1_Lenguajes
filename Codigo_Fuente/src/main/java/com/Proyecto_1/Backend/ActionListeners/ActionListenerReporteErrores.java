package com.Proyecto_1.Backend.ActionListeners;

import java.awt.event.*;

import com.Proyecto_1.Frontend.FramePrincipal;

public class ActionListenerReporteErrores implements ActionListener {

    private FramePrincipal framePrincipal;

    public ActionListenerReporteErrores(FramePrincipal framePrincipal) {
        this.framePrincipal = framePrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        framePrincipal.mostrarReporteErrores();
    }

}
