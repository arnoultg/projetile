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
import javax.swing.JLabel;
import javax.swing.border.Border;
import modele.Aventurier;
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
    private Color myBrown = new Color(167, 103, 38);

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
        JPanel mainPanel = new JPanel(new GridLayout(0, 2));
        JPanel panelgrille = new JPanel(new GridLayout(6, 6));
        JPanel paneldroite = new JPanel(new GridLayout(3, 0));
        
        JPanel PHaut = new JPanel(new BorderLayout());
        JPanel PMillieu = new JPanel(new BorderLayout());
        JPanel PBas = new JPanel(new BorderLayout());
         
        
        //panelgrille.set;
        frame.add(mainPanel);
        mainPanel.add(panelgrille);
        mainPanel.add(paneldroite);
        paneldroite.add(PHaut);
        paneldroite.add(PMillieu);
        paneldroite.add(PBas);
        
        
        JLabel nomJoueur = new JLabel("nom Joueur");
        JLabel Joueur = new JLabel ("Joueur 2");
        
        PHaut.add("North", nomJoueur);
        PHaut.add("Center", Joueur);
        
        
        
        //paneldroite.add(joueur, BorderLayout.);
        
        /*JPanel ligne = new JPanel(){
            public void paintComponent(Graphics g) {
       
                g.setColor(Color.BLACK);
                g.drawLine(600, 150, 700, 150);
                g.drawOval(400, 100, 100, 100);
            }
        };
        PHaut.add(ligne);*/

        int compteur = 0;

        for (int i = 1; i <= 36; i++) {
            if ((i < 3) || (i > 4 && i < 8) || (i == 12) || (i == 25) || (i > 29 && i < 33) || (i > 34)) {
                JPanel blanc = new JPanel();
                panelgrille.add(blanc);
            } else {
                Tuile t = g.getTuile(Iles.values()[compteur]);
                panelgrille.add(getCellule(compteur, g));
                
                for (Aventurier A : t.getAventurierssur() ){
                    JPanel Pion = new JPanel () {
                        public void paintComponent(Graphics g) {
                            g.setColor(Color.white);
                            g.drawOval(400, 100, 100, 100);
                        }
                    };
                }
                
                compteur++;
            }

        }

    }

    private JButton getCellule(int compteur, Grille g) {

        Tuile t = g.getTuile(Iles.values()[compteur]);
        JButton bouton = new JButton(t.getNom().toString());
        if (t.getEtat() == Utils.EtatTuile.ASSECHEE) {
            bouton.setBackground(myBrown);
        } else if (t.getEtat() == Utils.EtatTuile.COULEE) {
            bouton.setBackground(Color.blue);
        } else {
            bouton.setBackground(Color.yellow);
        }
        lesbouttons.add(bouton);
        return bouton;
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
