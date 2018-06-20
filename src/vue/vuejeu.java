/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import Enums.Iles;
import Enums.Tresor;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
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
    private ArrayList<TresorD> TresorsDessin;
    private ArrayList<JPanel> lescasesblanches;
    private ArrayList<JPanel> lescasesniveau;
    JLabel nom = new JLabel();
    JLabel Joueur2 = new JLabel();
    private Grille g;

    public vuejeu(Grille g) {
        GraphicsEnvironment environnement = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle fenetre = environnement.getMaximumWindowBounds();
        int xfenetre = (int) fenetre.getWidth();
        int yfenetre = (int) fenetre.getHeight();
        this.g = g;

// ouverture fenetre
        frame = new JFrame();
        frame.setTitle("Carte");
        frame.setSize(xfenetre, yfenetre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDoubleBuffered(true);

//initialisation 
        lesboutonstuiles = new ArrayList<>();
        lesboutonsactions = new ArrayList<>();
        lesboutonscartes = new ArrayList<>();
        pionsjoueur = new ArrayList<>();
        cartes = new ArrayList<>();
        TresorsDessin = new ArrayList<>();
        lescasesblanches = new ArrayList<>();
        lescasesniveau = new ArrayList<>();

//initialistation panel 
        JPanel mainPanel = new JPanel(new GridLayout(0, 2));
        JPanel panelgrille = new JPanel(new GridLayout(6, 6));
        JPanel paneldroite = new JPanel(new GridLayout(3, 0));

        JPanel PHaut = new JPanel(new GridLayout(0, 2));

        JPanel PMillieu = new JPanel(new BorderLayout());
        JPanel PBas = new JPanel(new GridLayout(3, 0));

//panelgrille.set;
        frame.add(mainPanel);
        mainPanel.add(panelgrille);
        mainPanel.add(paneldroite);

//premiere ligne       
        paneldroite.add(PHaut);
        //Phaut gauche
        JPanel Phautgauche = new JPanel(new GridLayout(0, 2));
        JPanel Pniveau = new JPanel(new GridLayout(20, 2));
        for (int i = 0; i < 40; i++) {
            if (i % 2 != 0) {
                JPanel blanc = new JPanel();
                Pniveau.add(blanc);
                lescasesniveau.add(blanc);

            } else {
                JPanel blanc = new JPanel();
                Pniveau.add(blanc);
            }

        }
        lescasesniveau.get(19 - (g.getNiveauEau() - 1) * 2).setBackground(Color.orange);
        Phautgauche.add(Pniveau, BorderLayout.WEST);

        ImagePanel niveau = new ImagePanel("/users/info/etu-s2/arnoultg/projet/projetile/Niveau.png", 0, 0, 200, 387);
        Phautgauche.add(niveau, BorderLayout.CENTER);
        PHaut.add(Phautgauche);

        //Phaut droite
        JPanel Phautdroite = new JPanel(new GridLayout(2, 0));
        PHaut.add(Phautdroite);
        //1.1 ligne
        Phautdroite.add(nom);
        //1.2 ligne       
        Phautdroite.add(Joueur2);

//2eme ligne
        paneldroite.add(PMillieu);
        JLabel mescartes = new JLabel("mes cartes");
        JPanel boutonsCartes = new JPanel(new GridLayout(3, 3));
        PMillieu.add(mescartes, BorderLayout.NORTH);
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
        JButton carte9 = new JButton("");
        carte9.setEnabled(false);
        boutonsCartes.add(carte1);
        boutonsCartes.add(carte2);
        boutonsCartes.add(carte3);
        boutonsCartes.add(carte4);
        boutonsCartes.add(carte5);
        boutonsCartes.add(carte6);
        boutonsCartes.add(carte7);
        boutonsCartes.add(carte8);
        boutonsCartes.add(carte9);
        lesboutonscartes.add(carte1);
        lesboutonscartes.add(carte2);
        lesboutonscartes.add(carte3);
        lesboutonscartes.add(carte4);
        lesboutonscartes.add(carte5);
        lesboutonscartes.add(carte6);
        lesboutonscartes.add(carte7);

//3eme ligne
        paneldroite.add(PBas);
        //3.1 

        JLabel Action = new JLabel("Action utilisateur");
        PBas.add(Action);
        //3.2

        JPanel toucheaction = new JPanel(new GridLayout(0, 4));

        JButton deplacer = new JButton("deplacer");
        JButton assecher = new JButton("assecher");
        JButton prendretres = new JButton("Prendre tresor");
        JButton donnerCarte = new JButton("Donner carte");

        toucheaction.add(deplacer);
        toucheaction.add(assecher);
        toucheaction.add(prendretres);
        toucheaction.add(donnerCarte);

        lesboutonsactions.add(deplacer);
        lesboutonsactions.add(assecher);
        lesboutonsactions.add(prendretres);
        lesboutonsactions.add(donnerCarte);

        PBas.add(toucheaction);
        //3.3

        JButton findetour = new JButton("Fin de tour");
        JPanel touchefinir = new JPanel(new GridLayout(0, 3));
        touchefinir.add(new JPanel());
        touchefinir.add(findetour);
        touchefinir.add(new JPanel());
        lesboutonsactions.add(findetour);
        PBas.add(touchefinir);

        int compteur = 0;

        for (int i = 0; i < 36; i++) {

            if (g.getGrilleTuile()[i / 6][i % 6].getNom() == null) {
                JPanel blanc = new JPanel();

                panelgrille.add(blanc);
                lescasesblanches.add(blanc);
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

        }
        this.afficherPion(); // affiche le Pion
    }

    public void afficherPion() {
        for (pionD pion : pionsjoueur) {
            lesboutonstuiles.get(pion.getAventurier().getPos().getX() * 6 + pion.getAventurier().getPos().getY()).add(pion); //recupere le bouton sur le quel le pion doit etre afficher
            pion.repaint(); //appelle PaintComponent

        }
    }

    public void creationTresor() {
        for (Tuile[] tuiles : g.getGrilleTuile()) {
            for (Tuile tuile : tuiles) {
                if (tuile.getTresor() == Tresor.CALYCE) {
                    TresorD Calyce = new TresorD(5, 100, Color.GREEN, tuile);
                    TresorsDessin.add(Calyce);
                } else if (tuile.getTresor() == Tresor.CRYSTAL) {
                    TresorD crystal = new TresorD(5, 100, Color.red, tuile);
                    TresorsDessin.add(crystal);
                } else if (tuile.getTresor() == Tresor.PIERRE) {
                    TresorD pierre = new TresorD(5, 100, Color.PINK, tuile);
                    TresorsDessin.add(pierre);

                } else if (tuile.getTresor() == Tresor.STATUE) {
                    TresorD statue = new TresorD(5, 100, Color.orange, tuile);
                    TresorsDessin.add(statue);
                }
            }
        }
        this.afficherTresor();
    }

    public void afficherTresor() {
        for (TresorD tresor : TresorsDessin) {
            lesboutonstuiles.get(tresor.getTuile().getX() * 6 + tresor.getTuile().getY()).add(tresor); //recupere le bouton sur le quel le pion doit etre afficher
            tresor.repaint(); //appelle PaintComponent

        }
    }

    public void tresorGagne(Tresor tresor) {

        for (int i = 0; i < lescasesblanches.size(); i++) {
            if (tresor == Tresor.CALYCE && i == 0) {
                lescasesblanches.get(i).setBackground(Color.green);
            } else if (tresor == Tresor.CRYSTAL && i == 3) {
                lescasesblanches.get(i).setBackground(Color.red);
            } else if (tresor == Tresor.PIERRE && i == 8) {
                lescasesblanches.get(i).setBackground(Color.pink);
            } else if (tresor == Tresor.STATUE && i == 11) {
                lescasesblanches.get(i).setBackground(Color.orange);
            }
        }
        repaint();
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

    public void miseajourniveau() {
        lescasesniveau.get(19 - (g.getNiveauEau() - 2) * 2).setBackground(null);
        lescasesniveau.get(19 - (g.getNiveauEau() - 1) * 2).setBackground(Color.orange);
    }
}
