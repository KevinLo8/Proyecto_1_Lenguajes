package com.Proyecto_1.Backend.ActionListeners;

import java.awt.event.*;

import javax.swing.*;

public class ActionListenerSeleccionar implements ActionListener {

    private JTextField txf1;

    public ActionListenerSeleccionar(JTextField txf1) {
        this.txf1 = txf1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(fileChooser);
            txf1.setText(fileChooser.getSelectedFile().getPath());
        } catch (NullPointerException ex) {
        }
    }

}
