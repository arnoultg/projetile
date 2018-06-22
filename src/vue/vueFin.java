/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
            fenetreFin.add(lab);
        } else {
            JLabel lab = new JLabel("dommage vous avez perdu");
            fenetreFin.add(lab);
        }
        
    }

    public void afficher() {
        fenetreFin.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        fenetreFin.setSize(300, 200);
        fenetreFin.setVisible(true);

    }

}
