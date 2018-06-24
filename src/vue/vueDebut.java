/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import modele.Grille;

/**
 *
 * @author arnoultg
 *
 */
public class vueDebut implements Observe {

    private JFrame fenetreInitialisation;
    private Observateur observateur;
    private JRadioButton[] boutons;
    private JPanel haut;
    private JButton demarrer;
    private JPanel grilleCentre;
    private ArrayList<String> nomjoueurs = new ArrayList<>();
    private JTextField nomJoueur1 = new JTextField();
    private JTextField nomJoueur2 = new JTextField();
    private JTextField nomJoueur3 = new JTextField();
    private JTextField nomJoueur4 = new JTextField();
    private ButtonGroup choixJoueur;
    private int nbjoueurs;
    
    private JComboBox listeDeroulante;
    private final String [] difficulté = { "Novice", "Normal","Elite","Légendaire"};

    public vueDebut() {

        fenetreInitialisation = new JFrame("Demarrage");
        fenetreInitialisation.setLayout(new BorderLayout());

        haut = new JPanel();
        demarrer = new JButton("demarrer");
        demarrer.setEnabled(false);

        fenetreInitialisation.add(haut, BorderLayout.NORTH);
        fenetreInitialisation.add(demarrer, BorderLayout.SOUTH);
        
        listeDeroulante = new JComboBox(difficulté);

        JRadioButton bouton;

        JPanel grillehaut = new JPanel(new GridLayout(1, 2));
        JPanel hautgauche = new JPanel();
        JPanel hautdroit = new JPanel();
        
        grillehaut.add(hautgauche);
        grillehaut.add(hautdroit);
        
        
        JLabel nbJoueur = new JLabel("nombre de joueurs : ");
        boutons = new JRadioButton[3];

        choixJoueur = new ButtonGroup();

        bouton = new JRadioButton("2");
        boutons[0] = bouton;
        choixJoueur.add(bouton);

        bouton = new JRadioButton("3");
        boutons[1] = bouton;
        choixJoueur.add(bouton);

        bouton = new JRadioButton("4");
        boutons[2] = bouton;
        choixJoueur.add(bouton);

        hautgauche.add(nbJoueur);
        hautgauche.add(boutons[0]);
        hautgauche.add(boutons[1]);
        hautgauche.add(boutons[2]);
        hautdroit.add(new JLabel("Espèce :"));
        hautdroit.add(listeDeroulante);

        haut.add(grillehaut);
        grilleCentre = new JPanel();
        boutons[0].addItemListener(
                new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e
            ) {
                fenetreInitialisation.remove(grilleCentre);
                grilleCentre = new JPanel(new GridLayout(2, 1));
                demarrer.setEnabled(true);
                nbjoueurs = 2;
                for (int i = 0; i < 4; i++) {
                    if ((i % 2) == 0) {
                        JLabel joueur = new JLabel("nom du joueur n°" + (i / 2 + 1) + " :");
                        grilleCentre.add(joueur);
                    } else if (i == 1) {
                        grilleCentre.add(nomJoueur1);
                    } else if (i == 3) {
                        grilleCentre.add(nomJoueur2);
                    }
                }
                fenetreInitialisation.add(grilleCentre, BorderLayout.CENTER);
                fenetreInitialisation.setVisible(true);
            }
        }
        );
        boutons[1].addItemListener(
                new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e
            ) {
                fenetreInitialisation.remove(grilleCentre);
                grilleCentre = new JPanel(new GridLayout(3, 1));
                demarrer.setEnabled(true);
                nbjoueurs = 3;
                for (int i = 0; i < 6; i++) {
                    if ((i % 2) == 0) {
                        JLabel joueur = new JLabel("nom du joueur n°" + (i / 2 + 1));
                        grilleCentre.add(joueur);
                    } else if (i == 1) {
                        grilleCentre.add(nomJoueur1);
                    } else if (i == 3) {
                        grilleCentre.add(nomJoueur2);
                    } else if (i == 5) {
                        grilleCentre.add(nomJoueur3);
                    }
                }
                fenetreInitialisation.add(grilleCentre, BorderLayout.CENTER);
                fenetreInitialisation.setVisible(true);
            }

        }
        );
        boutons[2].addItemListener(
                new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e
            ) {
                fenetreInitialisation.remove(grilleCentre);
                grilleCentre = new JPanel(new GridLayout(2, 2));
                demarrer.setEnabled(true);
                nbjoueurs = 4;
                for (int i = 0; i < 8; i++) {
                    if ((i % 2) == 0) {
                        JLabel joueur = new JLabel("nom du joueur n°" + (i / 2 + 1));
                        grilleCentre.add(joueur);
                    } else if (i == 1) {
                        grilleCentre.add(nomJoueur1);
                    } else if (i == 3) {
                        grilleCentre.add(nomJoueur2);
                    } else if (i == 5) {
                        grilleCentre.add(nomJoueur3);
                    } else if (i == 7) {
                        grilleCentre.add(nomJoueur4);
                    }
                }
                fenetreInitialisation.add(grilleCentre, BorderLayout.CENTER);
                fenetreInitialisation.setVisible(true);
            }
        }
        );

        demarrer.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                fenetreInitialisation.setVisible(false);
                Message m = new Message(TypesMessage.DEMARRER);
                nomjoueurs.add(nomJoueur1.getText());
                nomjoueurs.add(nomJoueur2.getText());
                nomjoueurs.add(nomJoueur3.getText());
                nomjoueurs.add(nomJoueur4.getText());
                m.nomsJoueurs = nomjoueurs;
                m.nbjoueurs = nbjoueurs;
                m.niveauEau = listeDeroulante.getSelectedIndex()+1;
                notifierObservateur(m);
            }
        }
        );

    }

    public void afficher() {
        fenetreInitialisation.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        fenetreInitialisation.setSize(650, 200);
        fenetreInitialisation.setVisible(true);

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
