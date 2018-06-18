/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import modele.Grille;

/**
 *
 * @author arnoultg
 *
 */
public class vueDebut implements Observe {

    private JFrame fenetreInitialisation;
    private Observateur observateur;
    private JComboBox choixJoueur;
    private JPanel haut;
    private JButton boutonOk;
    private JButton demarrer;
    private JPanel grilleCentre;
    private ArrayList<String> nomjoueurs = new ArrayList<>();
    private JTextArea nomJoueur1 = new JTextArea();
    private JTextArea nomJoueur2 = new JTextArea();
    private JTextArea nomJoueur3 = new JTextArea();
    private JTextArea nomJoueur4 = new JTextArea();

    public vueDebut() {

        fenetreInitialisation = new JFrame("Demarrage");
        fenetreInitialisation.setLayout(new BorderLayout());
        haut = new JPanel();
        demarrer = new JButton("demarrer");
        fenetreInitialisation.add(haut, BorderLayout.NORTH);
        fenetreInitialisation.add(demarrer, BorderLayout.SOUTH);
        demarrer.setEnabled(false);

        JPanel grillehaut = new JPanel(new GridLayout(1, 2));
        JLabel nbJoueur = new JLabel("nombre de joueur");
        grillehaut.add(nbJoueur);
        choixJoueur = new JComboBox(new String[]{"0", "2", "3", "4"});
        grillehaut.add(choixJoueur);

        haut.add(grillehaut);

        grilleCentre = new JPanel(new GridLayout(4, 2));
        fenetreInitialisation.add(grilleCentre, BorderLayout.CENTER);
        for (int i = 0; i < 8; i++) {
            if ((i % 2) == 0) {
                JLabel joueur = new JLabel("nom du joueur n°" + (i / 2 + 1));
                grilleCentre.add(joueur);
            } else if (i == 1) {
                nomJoueur1.setEnabled(false);
                grilleCentre.add(nomJoueur1);
            } else if (i == 3) {
                nomJoueur2.setEnabled(false);
                grilleCentre.add(nomJoueur2);
            } else if (i == 5) {
                nomJoueur3.setEnabled(false);
                grilleCentre.add(nomJoueur3);
            } else if (i == 7) {
                nomJoueur4.setEnabled(false);
                grilleCentre.add(nomJoueur4);
            }
        }

        choixJoueur.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (((String) e.getItem()) == "2") {
                    nomJoueur1.setEnabled(true);
                    nomJoueur2.setEnabled(true);
                    nomJoueur3.setEnabled(false);
                    nomJoueur4.setEnabled(false);
                    demarrer.setEnabled(true);

                } else if (((String) e.getItem()) == "3") {
                    nomJoueur1.setEnabled(true);
                    nomJoueur2.setEnabled(true);
                    nomJoueur3.setEnabled(true);
                    nomJoueur4.setEnabled(false);
                    demarrer.setEnabled(true);

                } else if (((String) e.getItem()) == "4") {
                    nomJoueur1.setEnabled(true);
                    nomJoueur2.setEnabled(true);
                    nomJoueur3.setEnabled(true);
                    nomJoueur4.setEnabled(true);
                    demarrer.setEnabled(true);

                } else {
                    nomJoueur1.setEnabled(false);
                    nomJoueur2.setEnabled(false);
                    nomJoueur3.setEnabled(false);
                    nomJoueur4.setEnabled(false);
                    demarrer.setEnabled(false);
                }
                //choixJoueur.setEnabled(false);
                
            }
        });

        demarrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetreInitialisation.setVisible(false);
                Message m = new Message(TypesMessage.DEMARRER);
                nomjoueurs.add(nomJoueur1.getText());
                nomjoueurs.add(nomJoueur2.getText());
                nomjoueurs.add(nomJoueur3.getText());
                nomjoueurs.add(nomJoueur4.getText());
                m.nomsJoueurs = nomjoueurs;
                m.nbjoueurs = Integer.parseInt((String) choixJoueur.getSelectedItem());
                notifierObservateur(m);
            }
        });

    }

    public void afficher() {
        fenetreInitialisation.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        fenetreInitialisation.setSize(400, 150);
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
