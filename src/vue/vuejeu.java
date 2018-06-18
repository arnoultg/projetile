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
    private ArrayList<JButton> lesboutonstuiles;
    private ArrayList<JButton> lesboutonsactions;
    private ArrayList<JButton> lesboutonscartes;
    private ArrayList<CarteTresor> cartes;
    private Observateur observateur;
    private ArrayList<pionD> pionsjoueur;
    JLabel nom = new JLabel();
    JLabel Joueur2 = new JLabel();

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
        lesboutonstuiles = new ArrayList<>();
        lesboutonsactions = new ArrayList<>();
        lesboutonscartes = new ArrayList<>();
        pionsjoueur = new ArrayList<>();
        cartes = new ArrayList<>();

//initialistation panel 
        JPanel mainPanel = new JPanel(new GridLayout(0, 2));
        JPanel panelgrille = new JPanel(new GridLayout(6, 6));
        JPanel paneldroite = new JPanel(new BorderLayout());

        JPanel PHaut = new JPanel(new BorderLayout());
        JPanel PMillieu = new JPanel(new BorderLayout());
        JPanel PBas = new JPanel(new BorderLayout());

//panelgrille.set;
        frame.add(mainPanel);
        mainPanel.add(panelgrille);
        mainPanel.add(paneldroite);
        paneldroite.add(PHaut, BorderLayout.NORTH);
        paneldroite.add(PMillieu, BorderLayout.CENTER);
        paneldroite.add(PBas, BorderLayout.SOUTH);

        PHaut.add(nom, BorderLayout.WEST);
        PHaut.add(Joueur2, BorderLayout.EAST);

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
        lesboutonsactions.add(rienfaire);

        JPanel boutonsCartes = new JPanel(new GridLayout(2, 4));
        PMillieu.add(boutonsCartes, BorderLayout.CENTER);
        JButton carte1 = new JButton("");
        carte1.setEnabled(false);
        JButton carte2 = new JButton("");
        carte2.setEnabled(false);
        JButton carte3 = new JButton("");
        carte3.setEnabled(false);
        JButton carte4 = new JButton("");
        carte4.setEnabled(false);
        JButton carte5 = new JButton("");
        carte5.setEnabled(false);
        JButton carte6 = new JButton("");
        carte6.setEnabled(false);
        JButton carte7 = new JButton("");
        carte7.setEnabled(false);
        JButton carte8 = new JButton("");
        carte8.setEnabled(false);
        boutonsCartes.add(carte1);
        boutonsCartes.add(carte2);
        boutonsCartes.add(carte3);
        boutonsCartes.add(carte4);
        boutonsCartes.add(carte5);
        boutonsCartes.add(carte6);
        boutonsCartes.add(carte7);
        boutonsCartes.add(carte8);
        lesboutonscartes.add(carte1);
        lesboutonscartes.add(carte2);
        lesboutonscartes.add(carte3);
        lesboutonscartes.add(carte4);
        lesboutonscartes.add(carte5);
        lesboutonscartes.add(carte6);
        lesboutonscartes.add(carte7);

        int compteur = 0;

        for (int i = 0; i < 36; i++) {

            if (g.getGrilleTuile()[i / 6][i % 6].getNom() == null) {
                JPanel blanc = new JPanel();
                panelgrille.add(blanc);
                lesboutonstuiles.add(null);
            } else {

                panelgrille.add(getCellule(compteur));

                compteur++;
            }

        }

        initAcPerform();
    }

    private JButton getCellule(int compteur) {

        Tuile t = g.getTuile(Iles.values()[compteur]);
        JButton bouton = new JButton(t.getNom().toString()); // créé un bouton pour la tuille
        bouton.setBackground(t.getCouleur());

        lesboutonstuiles.add(bouton);
        return bouton;
    }

    public void creationPion(ArrayList<Aventurier> aventuriers) {
        for (Aventurier av : aventuriers) {
            pionD pion = new pionD(20, 20, 10, av.getNomRole().getCouleur(), av); // crée un pionD pour chaque aventurer du jeu
            pionsjoueur.add(pion);
            this.afficherPion(); // affiche le Pion
        }
    }

    public void afficherPion() {
        for (pionD pion : pionsjoueur) {
            lesboutonstuiles.get(pion.getAventurier().getPos().getX() * 6 + pion.getAventurier().getPos().getY()).add(pion); //recupere le bouton sur le quel le pion doit etre afficher
            repaint(); //appelle PaintComponent

        }
    }

    public void afficher() {  // affiche la fenetre
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

    public void initAcPerform() {  //créé toute les action pour les boutons
        for (JButton i : lesboutonstuiles) { //les boutons des tuiles
            if (i != null) {
                int ind = lesboutonstuiles.indexOf(i);
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
            i.addActionListener(new ActionListener() { //les boutons des actions
                @Override
                public void actionPerformed(ActionEvent e) {
                    Message m = new Message(TypesMessage.CLIC_ACTION);
                    m.setAction(i.getText());
                    notifierObservateur(m);
                }
            });
        }
        for (JButton i : lesboutonscartes) {
            i.addActionListener(new ActionListener() { //les boutons des cartes
                @Override
                public void actionPerformed(ActionEvent e) {
                    Message m = new Message(TypesMessage.CLIC_CARTE);
                    m.setCarte(lesboutonscartes.indexOf(i));
                    notifierObservateur(m);
                }
            });
        }
    }

    /*public void afficherCartes(Aventurier a) { //affiche les cartes de l'aventurier dans la console
        cartes = a.getCartes();
        for (int i = 0; i < cartes.size(); i++) {
            System.out.print(i + 1 + " : ");
            System.out.println(cartes.get(i).getNom());
        }
    }*/
    public void choisirCarteDefausse(Aventurier a) { //l'utilisateur choisie les cartes qu'il doit defausser
        //cartes = a.getCartes();
        MiseaJourCartes(a);
        //int carteSelectionnee;
        int nbCartes = a.getNbCartes();
        for (int i = nbCartes; i > 5; i--) { //demande à l'utilisateur de recommencer si il se trompe de numero de carte
            //if (a.getNbCartes() == 6) {
            for (int j = 0; j < a.getNbCartes(); j++) {
                lesboutonscartes.get(j).setEnabled(true);
            }
            //System.out.println("Carte a défausser (1/2/3/4/5/6) : ");
            //} else {
            //System.out.println("Carte a défausser (1/2/3/4/5/6/7) : ");
            //}
            /*Scanner entree = new Scanner(System.in);
            carteSelectionnee = entree.nextInt();
            if (carteSelectionnee >= 1 && ((a.getNbCartes() == 6 && carteSelectionnee <= 6) || (a.getNbCartes() == 7 && carteSelectionnee <= 7))) {
                a.defausserCarte(carteSelectionnee - 1);
            } else {
                System.out.println("Selection érronée, réessayer");
                i++;
            }*/
            //afficherCartes(a); //réactualise la main 
            //MiseaJourCartes(a);
        }
    }

    public void selecTuile(ArrayList<Tuile> liste, Color coul) { //permet de mettre en rouge les tuiles disponible a assecher ou pour se deplacer
        for (Tuile t : liste) {
            if (coul != Color.RED) {
                coul = t.getCouleur();
            }
            int placetuilleihm = t.getX() * 6 + t.getY();
            lesboutonstuiles.get(placetuilleihm).setBackground(coul);
            //Casesaccessible.put(lesbouttonstuiles.get(placetuilleihm), t);
            //System.out.println(Casesaccessible.size());
        }
    }

    public void afficherNomJoueur(Aventurier av) { //affiche a l'ecran le nom du joueur et sa fonction
        if (av.getNomRole() == Utils.Pion.BLEU) {
            miseAJourNom(Joueur2, "pilote");
            miseAJourNom(nom, av.getNomjoueur());
        } else if (av.getNomRole() == Utils.Pion.JAUNE) {
            miseAJourNom(Joueur2, "navigateur");
            miseAJourNom(nom, "nom : " + av.getNomjoueur());
        } else if (av.getNomRole() == Utils.Pion.ORANGE) {
            miseAJourNom(Joueur2, "messager");
            miseAJourNom(nom, "nom : " + av.getNomjoueur());
        } else if (av.getNomRole() == Utils.Pion.ROUGE) {
            miseAJourNom(Joueur2, "ingenieur");
            miseAJourNom(nom, "nom : " + av.getNomjoueur());
        } else if (av.getNomRole() == Utils.Pion.VERT) {
            miseAJourNom(Joueur2, "explorateur");
            miseAJourNom(nom, "nom : " + av.getNomjoueur());
        } else if (av.getNomRole() == Utils.Pion.VIOLET) {
            miseAJourNom(Joueur2, "plongeur");
            miseAJourNom(nom, "nom : " + av.getNomjoueur());
        }
    }

    private void miseAJourNom(JLabel label, String str) { //met a jour le nom sur le label
        label.setText(str);
    }

    public void MiseaJourTuile(Tuile t) { //permet de remettre la case en marron apres l'action assecher
        int placetuilleihm = t.getX() * 6 + t.getY();
        lesboutonstuiles.get(placetuilleihm).setBackground(t.getCouleur());
    }

    public void MiseaJourCartes(Aventurier av) {
        int nbCartes = av.getNbCartes();
        for (int i = 0; i < 7; i++) {
            if (i < nbCartes) {
                lesboutonscartes.get(i).setText(av.getCartes().get(i).getNom());
                if (nbCartes > 5) {
                    lesboutonscartes.get(i).setEnabled(true);
                } else {
                    lesboutonscartes.get(i).setEnabled(false);
                }
            } else {
                lesboutonscartes.get(i).setText("");
                lesboutonscartes.get(i).setEnabled(false);
            }
        }

    }

    public void maj() { //permet de remettre toute les cases de leurs couleurs
        for (JButton b : lesboutonstuiles) {
            if (b != null) {
                b.setBackground(g.getGrilleTuile()[lesboutonstuiles.indexOf(b) / 6][lesboutonstuiles.indexOf(b) % 6].getCouleur());
            }
        }
    }
}
