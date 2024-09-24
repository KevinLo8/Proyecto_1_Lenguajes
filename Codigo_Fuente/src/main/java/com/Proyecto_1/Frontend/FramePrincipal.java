package com.Proyecto_1.Frontend;

import java.awt.*;

import javax.swing.*;

import com.Proyecto_1.Backend.ActionListeners.ActionListenerSalir;

public class FramePrincipal extends JFrame {

    private JScrollPane scp1;
    private JTextArea txa;

    private final Dimension DIM = Toolkit.getDefaultToolkit().getScreenSize();
    private final int SIZE = 800;
    private final int GAP = 50;
    private final int SIZE_PANEL = SIZE - GAP * 2;

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

        JMenuItem jMI1 = new JMenuItem("Salir");

        jM1.add(jMI1);

        jMI1.addActionListener(new ActionListenerSalir());

        setJMenuBar(jMenuBar);

        txa = new JTextArea();

        scp1 = new JScrollPane(txa);

        scp1.setPreferredSize(new Dimension(SIZE_PANEL, (int) (SIZE_PANEL * 1.3)));
        scp1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(GAP, Short.MAX_VALUE)
                        .addComponent(scp1)
                        .addContainerGap(GAP, Short.MAX_VALUE));

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(GAP, Short.MAX_VALUE)
                        .addComponent(scp1)
                        .addContainerGap(GAP, Short.MAX_VALUE));

    }

    public int getSIZE_PANEL() {
        return SIZE_PANEL;
    }

}
