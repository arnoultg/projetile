/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author lavierp
 */
public class TestVue {

    private JFrame frame;
    private JButton bouton;

    /**
     * @param args the command line arguments
     */
    public TestVue() {
        frame = new JFrame();
        frame.setTitle("Ma carte");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);
        JPanel panelCentre = new JPanel(new GridLayout(6, 6));

        bouton = new JButton();
        bouton.setBackground(Color.red);
        mainPanel.add(panelCentre, BorderLayout.CENTER);
        frame.setVisible(true);

        for (int i = 1; i <= 36; i++) {
            panelCentre.add(getCellule(i));

        }

    }

    private JPanel getCellule(int i) {
        int numLigne = (int) (i + 4) / 4;
        int numCouleur = (i - numLigne + 1) % 4 + 1;
        JPanel panelCellule = new JPanel();
        JButton tesb = new JButton();
        tesb.setBackground(Color.red);
        panelCellule.add(tesb);
        //panelCellule.setBackground((numCouleur == 1 ? new Color(255, 106, 0) : (numCouleur == 2 || numCouleur == 4 ? new Color(173, 234, 80) : new Color(92, 147, 200))));
        return panelCellule;
    }

    public void afficher() {

    }

    public static void main(String[] args) {
        // TODO code application logic here
        TestVue jeu = new TestVue();
        jeu.afficher();

    }

}
