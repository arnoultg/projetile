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
import java.util.Scanner;
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
    private pionD pion;
    private Grille g;

    public vuejeu(Grille g) {
        GraphicsEnvironment environnement = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle fenetre = environnement.getMaximumWindowBounds();
        int xfentre = (int) fenetre.getWidth();
        int yfenetre = (int) fenetre.getHeight();
        this.g = g;

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
        JButton findetour = new JButton("Fin_de_tour");
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

        for (int i = 0; i < 36; i++) {
            //if ((i < 3) || (i > 4 && i < 8) || (i == 12) || (i == 25) || (i > 29 && i < 33) || (i > 34)) {
            if (g.getGrilleTuile()[i / 6][i % 6].getNom() == null) {
                JPanel blanc = new JPanel();
                panelgrille.add(blanc);
                lesbouttonstuilles.add(null);
            } else {

                panelgrille.add(getCellule(compteur));

                compteur++;
            }

        }

        initAcPerform();
    }

    private JButton getCellule(int compteur) {

        Tuile t = g.getTuile(Iles.values()[compteur]);
        JButton bouton = new JButton(t.getNom().toString());
        bouton.setBackground(t.getCouleur());   
        lesbouttonstuilles.add(bouton);
        this.afficherPion(t);
        return bouton;
    }

    public void afficherPion(Tuile t) {
        for (Aventurier av : t.getAventurierssur()) {
            pion = new pionD(20, 20, 10, av.getNomRole().getCouleur());
            //System.out.println(t.getX()*6+t.getY());
            lesbouttonstuilles.get(t.getX()*6+t.getY()).add(pion);
            repaint();
        }     
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

    public void initAcPerform() {
        for (JButton i : lesbouttonstuilles) {
            if (i != null) {
                int ind = lesbouttonstuilles.indexOf(i);
                i.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Message m = new Message(TypesMessage.CLIC_TUILE);
                        m.setTuile(g.getGrilleTuile()[(ind / 6)][(ind % 6)]);
                        notifierObservateur(m);
                    }
                });
            }
        }
        for (JButton i : lesboutonsactions) {
            i.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Message m = new Message(TypesMessage.CLIC_ACTION);
                    m.setAction(i.getText());
                    notifierObservateur(m);
                }
            });
        }
    }

    public void afficherCartes(Aventurier a) {
        cartes = a.getCartes();
        for (int i = 0; i < cartes.size(); i++) {
            System.out.println(cartes.get(i).getNom());
        }
    }

    public void choisirCarteDefausse(Aventurier a) {
        cartes = a.getCartes();
        for (int i = a.getNbCartes(); i > 5; i--) {
            System.out.println("Carte a déf (1/2/3/4/5/6/7) : ");
            Scanner entree = new Scanner(System.in);
            int carteSelectionnee = entree.nextInt();
            a.defausserCarte(carteSelectionnee - 1);
            afficherCartes(a);
        }
    }

    public void selecTuile(ArrayList<Tuile> liste, Color coul) {
        for (Tuile t : liste) {
            if (coul != Color.RED) {
                coul = t.getCouleur();
            }
            int placetuilleihm = t.getX() * 6 + t.getY();
            lesbouttonstuilles.get(placetuilleihm).setBackground(coul);
            //Casesaccessible.put(lesbouttonstuilles.get(placetuilleihm), t);
            //System.out.println(Casesaccessible.size());

        }

    }
}
