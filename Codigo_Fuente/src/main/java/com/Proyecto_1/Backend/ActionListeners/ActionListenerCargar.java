package com.Proyecto_1.Backend.ActionListeners;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import com.Proyecto_1.Backend.ConexionArchivos.ConexionArchivos;
import com.Proyecto_1.Backend.Exception.ArchivoInexistenteException;
import com.Proyecto_1.Frontend.FramePrincipal;

public class ActionListenerCargar implements ActionListener {

    private FramePrincipal frame;
    private JLabel lbl1;
    private JTextField txf1;

    public ActionListenerCargar(FramePrincipal frame, JLabel lbl1, JTextField txf1) {
        this.frame = frame;
        this.lbl1 = lbl1;
        this.txf1 = txf1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ConexionArchivos conexion = new ConexionArchivos();
            String texto = conexion.leerArchivo(txf1.getText());

            frame.preguntarBorrar(texto);
        } catch (ArchivoInexistenteException ex) {
            lbl1.setText("Archivo seleccionado no existe.");
        } catch (IOException e1) {
            lbl1.setText("Hubo un error al leer el archivo.");
        }
    }

}
