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
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import modele.Aventurier;
import modele.CarteTresor;
import modele.Grille;
import modele.Tuile;
import util.Utils;
import util.Utils.Pion;

/**
 *
 * @author lavierp
 */
public class vuejeu extends JPanel implements Observe {

    private JFrame frame;
    private ArrayList<JButton> lesbouttonstuilles;
    private ArrayList<JButton> lesboutonsactions;
    private ArrayList<CarteTresor> cartes;
    private Observateur observateur;
    private Color myBrown = new Color(167, 103, 38);
    private pionD pion;

    public vuejeu(Grille g) {
        GraphicsEnvironment environnement = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle fenetre = environnement.getMaximumWindowBounds();
        int xfentre = (int) fenetre.getWidth();
        int yfenetre = (int) fenetre.getHeight();

// ouverture fenetre
        frame = new JFrame();
        frame.setTitle("Carte");
        frame.setSize(xfentre, yfenetre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDoubleBuffered(true);

//initialisation 
        lesbouttonstuilles = new ArrayList<>();
        lesboutonsactions = new ArrayList<>();
        cartes = new ArrayList<>();
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
        JLabel Joueur = new JLabel("Joueur 2");

        PHaut.add("North", nomJoueur);
        PHaut.add("Center", Joueur);

        JPanel toucheaction = new JPanel(new GridLayout(0, 4));
        PBas.add(toucheaction);
        JButton findetour = new JButton("Fin de tour");
        JButton deplacer = new JButton("deplacer");
        JButton assecher = new JButton("assecher");
        JButton rienfaire = new JButton("rien faire");

        toucheaction.add(findetour);
        toucheaction.add(deplacer);
        toucheaction.add(assecher);
        toucheaction.add(rienfaire);
        lesboutonsactions.add(findetour);
        lesboutonsactions.add(deplacer);
        lesboutonsactions.add(assecher);
        lesboutonsactions.add(findetour);

        int compteur = 0;

        for (int i = 1; i <= 36; i++) {
            if ((i < 3) || (i > 4 && i < 8) || (i == 12) || (i == 25) || (i > 29 && i < 33) || (i > 34)) {
                JPanel blanc = new JPanel();
                panelgrille.add(blanc);
                lesbouttonstuilles.add(null);
            } else {

                panelgrille.add(getCellule(compteur, g));

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
        for (Aventurier av : t.getAventurierssur()) {
            pion = new pionD(20, 20, 10, av.getNomRole().getCouleur());
            bouton.add(pion);
        }

        lesbouttonstuilles.add(bouton);
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

    public void afficherCartes(Aventurier a) {
        cartes = a.getCartes();
        for (int i = 0; i < cartes.size(); i++) {
            System.out.println(cartes.get(i).getNom());
        }
    }

    public void deplacer(Grille g, Aventurier av) {
        HashMap<JButton, Tuile> Casesaccessible = new HashMap<>();
        ArrayList<Tuile> tdispo = av.tuilesDispoAv(g);
        lesboutonsactions.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(av.getPos().getNom());
                //System.out.println("");
                for (Tuile t : tdispo) {
                    //System.out.println(t.getNom());
                    //System.out.println(t.getX() + "" + t.getY());
                    int placetuilleihm = t.getX() * 6 + t.getY();
                    lesbouttonstuilles.get(placetuilleihm).setBackground(Color.red);
                    Casesaccessible.put(lesbouttonstuilles.get(placetuilleihm), t);
                    System.out.println(Casesaccessible.size());

                }
                for (HashMap.Entry<JButton, Tuile> entry : Casesaccessible.entrySet()) {

                    entry.getKey().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            av.setPos(entry.getValue());
                            tdispo.clear();
                        }
                    });
                }

            }
        });

    }
}
