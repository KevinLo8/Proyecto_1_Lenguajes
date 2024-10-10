package com.Proyecto_1.Backend.ActionListeners;

import java.awt.event.*;

import com.Proyecto_1.Frontend.FramePrincipal;

public class ActionListenerReporteTokens implements ActionListener {

    private FramePrincipal framePrincipal;

    public ActionListenerReporteTokens(FramePrincipal framePrincipal) {
        this.framePrincipal = framePrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        framePrincipal.mostrarReporteTokens();
    }

}
