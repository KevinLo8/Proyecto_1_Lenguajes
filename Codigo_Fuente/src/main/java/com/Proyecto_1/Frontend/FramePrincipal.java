package com.Proyecto_1.Frontend;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.Proyecto_1.Backend.ActionListeners.*;
import com.Proyecto_1.Backend.Automata.*;
import com.Proyecto_1.Backend.Token.*;
import com.Proyecto_1.Frontend.JDialog.*;

public class FramePrincipal extends JFrame {

    private Automata automata;

    private JScrollPane scp1;
    private JTextArea txa;
    private JButton btn1, btn2;
    private JMenuItem jMI3, jMI4, jMI5;

    private final Dimension DIM = Toolkit.getDefaultToolkit().getScreenSize();
    private final int SIZE = 800;
    private final int GAP = 50;
    private final int SIZE_PANEL = SIZE - GAP * 2;

    private DialogSeleccionArchivo dialogSeleccionArchivo;
    private DialogReporteTokens dialogReporteTokens;
    private DialogReporteErrores dialogReporteErrores;
    private DialogReporteOptimizacion dialogReporteOptimizacion;

    public FramePrincipal() {

        initComponentes();

    }

    private void initComponentes() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds((int) ((DIM.getWidth() - SIZE) / 2), (int) ((DIM.getHeight() - SIZE * 1.3) / 2), SIZE,
                (int) (SIZE * 1.3));
        setTitle("Analizador Léxico");

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jM1 = new JMenu("Archivo");
        JMenu jM2 = new JMenu("Acciones");

        jMenuBar.add(jM1);
        jMenuBar.add(jM2);

        JMenuItem jMI1 = new JMenuItem("Cargar Archivo");
        JMenuItem jMI2 = new JMenuItem("Salir");
        jMI3 = new JMenuItem("Reporte De Tokens");
        jMI4 = new JMenuItem("Reporte De Errores");
        jMI5 = new JMenuItem("Reporte De Optimización");

        jMI3.setEnabled(false);
        jMI4.setEnabled(false);
        jMI5.setEnabled(false);

        jM1.add(jMI1);
        jM1.add(jMI2);
        jM2.add(jMI3);
        jM2.add(jMI4);
        jM2.add(jMI5);

        jMI1.addActionListener(new ActionListenerArchivo(this));
        jMI2.addActionListener(new ActionListenerSalir());
        jMI3.addActionListener(new ActionListenerReporteTokens(this));
        jMI4.addActionListener(new ActionListenerReporteErrores(this));
        jMI5.addActionListener(new ActionListenerReporteOptimizacion(this));

        setJMenuBar(jMenuBar);

        txa = new JTextArea();

        scp1 = new JScrollPane(txa);

        scp1.setPreferredSize(new Dimension(SIZE_PANEL, (int) (SIZE_PANEL * 1.3)));
        scp1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        btn1 = new JButton("Analizar Texto");
        btn2 = new JButton("Generar HTML");

        btn1.addActionListener(new ActionListenerAnalizar(this));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(GAP, Short.MAX_VALUE)
                        .addComponent(scp1)
                        .addGap(GAP / 3)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(btn1)
                                .addComponent(btn2))
                        .addContainerGap(GAP, Short.MAX_VALUE));

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(GAP, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(scp1)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn1)
                                        .addGap(GAP)
                                        .addComponent(btn2)))

                        .addContainerGap(GAP, Short.MAX_VALUE));

    }

    public int getSIZE_PANEL() {
        return SIZE_PANEL;
    }

    public void preguntarArchivo() {
        cerrarDialogs();
        dialogSeleccionArchivo = new DialogSeleccionArchivo(this);
        dialogSeleccionArchivo.setVisible(true);
    }

    public void cerrarDialogs() {
        if (dialogSeleccionArchivo != null) {
            dialogSeleccionArchivo.dispose();
        }
        if (dialogReporteTokens != null) {
            dialogReporteTokens.dispose();
        }
        if (dialogReporteErrores != null) {
            dialogReporteErrores.dispose();
        }
        if (dialogReporteOptimizacion != null) {
            dialogReporteOptimizacion.dispose();
        }
    }

    public void borrarTextArea(String texto) {
        String textoTotal = null;

        textoTotal = texto;

        txa.setText(textoTotal);
        cerrarDialogs();
    }

    public void analizarTexto() {
        automata = new Automata();
        automata.analizarTexto(txa);

        jMI3.setEnabled(true);
        jMI4.setEnabled(true);
        jMI5.setEnabled(true);
    }

    public ArrayList<Token> pedirTokens() {
        return automata.getTokens();
    }

    public ArrayList<TokenError> pedirErrores() {
        return automata.getErrores();
    }

    public ArrayList<Token> pedirOptimizacion() {
        return automata.getOptimizacion();
    }

    public void mostrarReporteTokens() {
        cerrarDialogs();
        dialogReporteTokens = new DialogReporteTokens(this);
        dialogReporteTokens.setVisible(true);
    }

    public void mostrarReporteErrores() {
        cerrarDialogs();
        dialogReporteErrores = new DialogReporteErrores(this);
        dialogReporteErrores.setVisible(true);
    }

    public void mostrarReporteOptimizacion() {
        cerrarDialogs();
        dialogReporteOptimizacion = new DialogReporteOptimizacion(this);
        dialogReporteOptimizacion.setVisible(true);
    }
}
