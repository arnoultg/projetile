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
        frame = new JFrame();
        frame.setTitle("Carte");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);
        JPanel panelCentre = new JPanel(new GridLayout(6, 6));

        lesbouttons = new ArrayList<>();
        JButton bouton = new JButton();
        mainPanel.add(panelCentre, BorderLayout.CENTER);

        int compteur = 0;

        for (int i = 1; i <= 36; i++) {
            if ((i < 3) || (i > 4 && i < 8) || (i == 12) || (i == 25) || (i > 29 && i < 33) || (i > 34)) {
                JPanel blanc = new JPanel();
                panelCentre.add(blanc);
            } else {
                panelCentre.add(getCellule(compteur, g));
                compteur++;
                System.out.println(compteur);

            }

        }

    }

    private JButton getCellule(int compteur, Grille g) {

        //JButton bouton = new JButton(Iles.values()[compteur].toString());
        JButton bouton = new JButton(g.getTuile(Iles.values()[compteur]).toString());
        if (g.getTuile(Iles.values()[compteur]).getEtat() == Utils.EtatTuile.ASSECHEE){
            bouton.setBackground(Color.red);
        }else if (g.getTuile(Iles.values()[compteur]).getEtat() == Utils.EtatTuile.COULEE){
            bouton.setBackground(Color.blue);
        }else {
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
