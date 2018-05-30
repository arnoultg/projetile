/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import Enums.Iles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import modele.Grille;
import modele.Tuile;
import util.Utils;

/**
 *
 * @author lavierp
 */
public class vuejeu extends JPanel implements Observe {

    private JFrame frame;
    private ArrayList<JButton> lesbouttons;
    private Observateur observateur;

    public vuejeu(Grille g) {
        
// ouverture fenetre
        frame = new JFrame();
        frame.setTitle("Carte");
        frame.setSize(1600, 1600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//initialisation 
        lesbouttons = new ArrayList<>();
        JButton bouton = new JButton();

//initialistation panel 
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);
        JPanel panelgrille = new JPanel(new GridLayout(6, 6));
        mainPanel.add(panelgrille, BorderLayout.WEST);
        
        

        int compteur = 0;

        for (int i = 1; i <= 36; i++) {
            if ((i < 3) || (i > 4 && i < 8) || (i == 12) || (i == 25) || (i > 29 && i < 33) || (i > 34)) {
                JPanel blanc = new JPanel();
                panelgrille.add(blanc);
            } else {
                panelgrille.add(getCellule(compteur, g));
                compteur++;

            }

        }

    }

    private JButton getCellule(int compteur, Grille g) {

        JButton bouton = new JButton(Iles.values()[compteur].toString());
        //System.out.println(Iles.values()[compteur]);
        //System.out.println(compteur);
        Tuile t = g.getTuile(Iles.values()[compteur]);
        if (t.getEtat() == Utils.EtatTuile.ASSECHEE) {
            bouton.setBackground(Color.red);
        } else if (t.getEtat() == Utils.EtatTuile.COULEE) {
            bouton.setBackground(Color.blue);
        } else {
            bouton.setBackground(Color.yellow);
        }
        lesbouttons.add(bouton);
        return bouton;
    }

    public void creationIHM(Grille g) {

    }

    public void afficher() {
        frame.setVisible(
                true);

    }

    public void addObservateur(Observateur o) {
        this.observateur = o;
    }

    public void notifierObservateur(Message m) {
        if (observateur != null) {
            observateur.traiterMessage(m);
        }
    }

}
