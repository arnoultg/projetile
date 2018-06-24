/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author arnoultg
 */
public class vueFin {

    private boolean gagner;
    private JFrame fenetreFin;

    public vueFin(boolean gagner) {
        this.gagner = gagner;
        fenetreFin = new JFrame("Fin du jeu");
        fenetreFin.setLayout(new BorderLayout());
        if (gagner) {
            JLabel lab = new JLabel("bravo vous avez gagner");
            fenetreFin.add(lab,BorderLayout.CENTER);
        } else {
            JLabel lab = new JLabel("dommage vous avez perdu");
            fenetreFin.add(lab,BorderLayout.CENTER);
        }
        JPanel bas = new JPanel(new GridLayout(0,3));
        JButton rejouer = new JButton();
        bas.add(rejouer);
        bas.add(new JPanel());
        bas.add(new JButton());
        
        fenetreFin.add(bas,BorderLayout.NORTH);
    }

    public void afficher() {
        fenetreFin.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        fenetreFin.setSize(300, 200);
        fenetreFin.setVisible(true);

    }

}
