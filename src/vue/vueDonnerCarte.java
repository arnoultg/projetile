/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Aventurier;
import modele.CarteTresor;
import util.Utils;

/**
 *
 * @author sabybe
 */
public class vueDonnerCarte {

    private JFrame frame;
    private Observateur observateur;
    private ArrayList<JButton> lesboutonscartes;
    private ArrayList<JButton> lesboutonsaventuriers;
    private Aventurier aventurier;
    private CarteTresor carte;
    private boolean carteSelec;
    private boolean avSelec;
    private JButton valider;

    public vueDonnerCarte(ArrayList<Aventurier> liste, Aventurier AvCourant) {
        lesboutonscartes = new ArrayList<>();
        lesboutonsaventuriers = new ArrayList<>();
        carteSelec = false;
        avSelec = false;

        frame = new JFrame("Donner Carte");
        JPanel mainPanel = new JPanel(new GridLayout(0, 2));
        frame.add(mainPanel);
        JPanel panelDroite = new JPanel(new GridLayout(2, 0));
        JPanel panelGauche = new JPanel(new GridLayout(2, 0));
        mainPanel.add(panelGauche);
        mainPanel.add(panelDroite);

        JPanel panelDTop = new JPanel(new GridLayout(3, 0));
        JPanel panelDHaut = new JPanel(new GridLayout(0, 3));
        panelDTop.add(panelDHaut);
        panelDroite.add(panelDTop);

        JPanel panelDBas = new JPanel(new GridLayout(2, 0));
        JLabel labelChoixC = new JLabel("Choisir une carte à donner");
        JPanel panelDChoix = new JPanel(new GridLayout(0, 3));
        panelDChoix.add(new JPanel());
        panelDChoix.add(labelChoixC);
        panelDChoix.add(new JPanel());
        panelDBas.add(panelDChoix);
        JButton annuler = new JButton("Annuler");
        panelDBas.add(annuler);
        panelDroite.add(panelDBas);

        JLabel voscartes = new JLabel("Vos cartes");
        panelDHaut.add(new JPanel());
        panelDHaut.add(voscartes);
        panelDHaut.add(new JPanel());
        JPanel boutonsCartes = new JPanel(new GridLayout(3, 3));
        panelDTop.add(boutonsCartes);

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

        JLabel cartesjoueurs = new JLabel("Les cartes des autres joueurs");
        JPanel panelGHaut = new JPanel(new GridLayout(4, 0));
        JPanel panelGTop = new JPanel(new GridLayout(0, 3));
        panelGTop.add(new JPanel());
        panelGTop.add(cartesjoueurs);
        panelGHaut.add(panelGTop);
        for (Aventurier av : liste) {
            ArrayList<JButton> lesboutonscartesJ = new ArrayList<>();

            JPanel panelJoueur = new JPanel(new GridLayout(2, 0));
            JPanel panelInfoJoueur = new JPanel(new GridLayout(2, 3));
            JLabel noms = new JLabel("Nom :");
            panelInfoJoueur.add(noms);
            JLabel nomsJoueurs = new JLabel(av.getNomjoueur());
            panelInfoJoueur.add(nomsJoueurs);
            panelInfoJoueur.add(new JPanel());
            JLabel roles = new JLabel("Rôle :");
            panelInfoJoueur.add(roles);
            JLabel roleJoueurs;
            if (av.getNomRole() == Utils.Pion.JAUNE) {
                roleJoueurs = new JLabel("navigateur");
            } else if (av.getNomRole() == Utils.Pion.ROUGE) {
                roleJoueurs = new JLabel("ingenieur");
            } else if (av.getNomRole() == Utils.Pion.VERT) {
                roleJoueurs = new JLabel("explorateur");
            } else if (av.getNomRole() == Utils.Pion.VIOLET) {
                roleJoueurs = new JLabel("plongeur");
            } else if (av.getNomRole() == Utils.Pion.ORANGE) {
                roleJoueurs = new JLabel("messager");
            } else {
                roleJoueurs = new JLabel("pilote");
            }
            panelInfoJoueur.add(roleJoueurs);
            JButton boutonAv = new JButton();
            lesboutonsaventuriers.add(boutonAv);
            boutonAv.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e
                ) {
                    for (JButton bouton : lesboutonsaventuriers) {
                        if (e.getSource().equals(bouton)) {
                            bouton.setEnabled(false);
                            avSelec = true;
                            aventurier = av;
                            if (carteSelec) {
                                valider.setEnabled(true);
                            }
                        } else {
                            bouton.setEnabled(true);
                        }
                    }

                }
            }
            );
            panelInfoJoueur.add(boutonAv);
            panelJoueur.add(panelInfoJoueur);

            JPanel boutonsCartesJ = new JPanel(new GridLayout(2, 5));
            panelJoueur.add(boutonsCartesJ);
            JButton carte1j = new JButton("");
            carte1j.setEnabled(false);
            JButton carte2j = new JButton("");
            carte2j.setEnabled(false);
            JButton carte3j = new JButton("");
            carte3j.setEnabled(false);
            JButton carte4j = new JButton("");
            carte4j.setEnabled(false);
            JButton carte5j = new JButton("");
            carte5j.setEnabled(false);
            JButton carte6j = new JButton("");
            carte6j.setEnabled(false);
            JButton carte7j = new JButton("");
            carte7j.setEnabled(false);
            JButton carte8j = new JButton("");
            carte8j.setEnabled(false);
            JButton carte9j = new JButton("");
            carte9j.setEnabled(false);
            JButton carte10j = new JButton("");
            carte10j.setEnabled(false);

            boutonsCartesJ.add(carte1j);
            boutonsCartesJ.add(carte2j);
            boutonsCartesJ.add(carte3j);
            boutonsCartesJ.add(carte4j);
            boutonsCartesJ.add(carte5j);
            boutonsCartesJ.add(carte6j);
            boutonsCartesJ.add(carte7j);
            boutonsCartesJ.add(carte8j);
            boutonsCartesJ.add(carte9j);
            boutonsCartesJ.add(carte10j);
            lesboutonscartesJ.add(carte1j);
            lesboutonscartesJ.add(carte2j);
            lesboutonscartesJ.add(carte3j);
            lesboutonscartesJ.add(carte4j);
            lesboutonscartesJ.add(carte5j);
            lesboutonscartesJ.add(carte6j);
            lesboutonscartesJ.add(carte7j);
            lesboutonscartesJ.add(carte8j);
            lesboutonscartesJ.add(carte9j);
            lesboutonscartesJ.add(carte10j);

            panelGHaut.add(panelJoueur);
            afficherCartesJoueurs(av, lesboutonscartesJ);

        }
        panelGauche.add(panelGHaut);

        JPanel panelGBas = new JPanel(new GridLayout(2, 0));
        JLabel labelChoixJ = new JLabel("Choisir un joueur");
        JPanel panelGChoix = new JPanel(new GridLayout(0, 3));
        panelGChoix.add(new JPanel());
        panelGChoix.add(labelChoixJ);
        panelGChoix.add(new JPanel());
        panelGBas.add(panelGChoix);
        valider = new JButton("Valider");
        panelGBas.add(valider);
        valider.setEnabled(false);
        panelGauche.add(panelGBas);

        annuler.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                Message m = new Message(TypesMessage.ANNULER);
                notifierObservateur(m);
                frame.setVisible(false);
            }
        }
        );
        valider.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                Message m = new Message(TypesMessage.VALIDER);
                m.destinataire = aventurier;
                m.carteTr = carte;
                notifierObservateur(m);
                frame.setVisible(false);
            }
        }
        );
    }

    public void afficher() {
        GraphicsEnvironment environnement = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle fenetre = environnement.getMaximumWindowBounds();
        int xfenetre = (int) fenetre.getWidth();
        int yfenetre = (int) fenetre.getHeight() /2;
        frame.setSize(xfenetre, yfenetre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void afficherCartes(Aventurier av) {
        int nbCartes = av.getNbCartes();
        for (int i = 0; i < 7; i++) {
            if (i < nbCartes) {
                lesboutonscartes.get(i).setText(av.getCartes().get(i).getNom());
                lesboutonscartes.get(i).setEnabled(true);
                lesboutonscartes.get(i).addActionListener(
                        new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        int compteur = 0;
                        for (JButton bouton : lesboutonscartes) {
                            if (e.getSource().equals(bouton) && compteur < nbCartes) {
                                bouton.setEnabled(false);
                                carte = av.getCartes().get(compteur);
                                carteSelec = true;
                                if (avSelec) {
                                    valider.setEnabled(true);
                                }
                            } else if (compteur >= nbCartes) {
                                bouton.setEnabled(false);
                            } else {
                                bouton.setEnabled(true);
                            }
                            compteur++;
                        }
                    }
                }
                );
            } else {
                lesboutonscartes.get(i).setText("");
                lesboutonscartes.get(i).setEnabled(false);
            }
        }
    }

    public void afficherCartesJoueurs(Aventurier av, ArrayList<JButton> lesboutonscartesJ) {
        int nbCartes = av.getNbCartes();
        for (int i = 0; i < lesboutonscartesJ.size(); i++) {
            if (i < nbCartes) {
                lesboutonscartesJ.get(i).setText(av.getCartes().get(i).getNom());
            } else {
                lesboutonscartesJ.get(i).setText("");
            }
            lesboutonscartesJ.get(i).setEnabled(false);

        }
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
