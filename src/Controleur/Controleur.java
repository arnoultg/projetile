/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Enums.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import modele.*;
import util.Utils;
import vue.Message;
import vue.Observateur;
import vue.TypesMessage;
import vue.vuejeu;

/**
 *
 * @author geitnert
 */
public class Controleur implements Observateur {

    private static ArrayList<Aventurier> joueurs;
    private String action = null;
    private static vuejeu jeu;
    private static Aventurier AvCourant;
    private int nbActions;
    private static Grille G;

    public Controleur() {
        joueurs = new ArrayList<>();
    }

    public Grille getGrille() {
        return null;
    }

    public int getNbJoueur() {
        return joueurs.size();
    }

    private Aventurier getAvCourant() {
        return null;
    }

    private Tuile TuileSelectionnee() {
        return null;
    }

    private void addAventurier(Aventurier av) {
        joueurs.add(av);
    }

    public ArrayList<Aventurier> getJoueurs() {
        return joueurs;
    }

    public void setAvCourant(Aventurier AvCourant) {
        this.AvCourant = AvCourant;
    }

    @Override
    public void traiterMessage(Message m) {

        if (m.getType() == TypesMessage.CLIC_ACTION) {
            if (action != null) {
                jeu.selecTuile(AvCourant.dispoAssecher(G), Color.white);
                jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.white);
            }

            action = m.getAction();
            System.out.println(action);
            System.out.println("straf");
            if (action == "deplacer") {
                jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.red);
            } else if (action == "assecher") {
                jeu.selecTuile(AvCourant.dispoAssecher(G), Color.red);
            } else if (m.getAction() == "Fin_de_tour") {
                this.finTour();
            }

        } else if (m.getType() == TypesMessage.CLIC_TUILE) {
            if (action == "deplacer") {
                if (AvCourant.tuilesDispoAv(G).contains(m.getTuile())) {
                    System.out.println(m.getTuile().getNom() + "est la destination");
                    jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.white);
    
                    this.deplacerJoueur(m.getTuile());
                    action = null;
                }
                
            } else if (action == "assecher") {
                System.out.println(m.getTuile().getNom() + "a ete asseché");
                jeu.selecTuile(AvCourant.dispoAssecher(G), Color.white);
                action = null;
            }

        }
    }

    public void finTour() {
        action = null;
        nbActions = 3;
        int ind = joueurs.indexOf(AvCourant);
        AvCourant = (ind == joueurs.size()-1 ? joueurs.get(0) : joueurs.get(ind+1));
        jeu.afficherNomJoueur(AvCourant);
        System.out.println("strafoulila");
        System.out.println(AvCourant.getNomjoueur());
    }

    private void creationJoueur() {

        System.out.println("Combien de joueurs voulez vous ?");
        Scanner entree = new Scanner(System.in);
        int nbJoueur = entree.nextInt();
        ArrayList<Utils.Pion> lescouleurs = new ArrayList<>();

        for (int x = 0; x < Utils.Pion.values().length; x++) {
            lescouleurs.add(Utils.Pion.values()[x]);
        }
        Collections.shuffle(lescouleurs);

        for (int x = 0; x < nbJoueur; x++) {
            System.out.println("joueur n°" + (x + 1));
            System.out.println("quel est votre nom ?");
            String nomjoueur = entree.next();

            if (lescouleurs.get(x) == Utils.Pion.JAUNE) {
                Tuile t = G.getTuile(Iles.LA_PORTE_D_OR);
                Navigateur Joueur = new Navigateur(Utils.Pion.JAUNE, nomjoueur, t);
                t.addAventurier(Joueur);
                Joueur.tirerCartesTresors(G);
                addAventurier(Joueur);
                System.out.println("Vous etes le navigateur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.VIOLET) {
                Tuile t = G.getTuile(Iles.LA_PORTE_DE_FER);
                Plongeur Joueur = new Plongeur(Utils.Pion.VIOLET, nomjoueur, t);
                t.addAventurier(Joueur);
                Joueur.tirerCartesTresors(G);
                addAventurier(Joueur);
                System.out.println("Vous etes le plongeur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.BLEU) {
                Tuile t = G.getTuile(Iles.HELIPORT);
                Pilote Joueur = new Pilote(Utils.Pion.BLEU, nomjoueur, t);
                t.addAventurier(Joueur);
                Joueur.tirerCartesTresors(G);
                addAventurier(Joueur);
                System.out.println("Vous etes le pilote \n");

            } else if (lescouleurs.get(x) == Utils.Pion.ROUGE) {
                Tuile t = G.getTuile(Iles.LA_PORTE_DE_BRONZE);
                Ingenieur Joueur = new Ingenieur(Utils.Pion.ROUGE, nomjoueur, t);
                t.addAventurier(Joueur);
                Joueur.tirerCartesTresors(G);
                addAventurier(Joueur);
                System.out.println("Vous etes l'ingenieur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.VERT) {
                Tuile t = G.getTuile(Iles.LA_PORTE_DE_CUIVRE);
                Explorateur Joueur = new Explorateur(Utils.Pion.VERT, nomjoueur, t);
                t.addAventurier(Joueur);
                Joueur.tirerCartesTresors(G);
                addAventurier(Joueur);
                System.out.println("Vous etes le explorateur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.ORANGE) {
                Tuile t = G.getTuile(Iles.LA_PORTE_D_ARGENT);
                Messager Joueur = new Messager(Utils.Pion.ORANGE, nomjoueur, t);
                t.addAventurier(Joueur);
                Joueur.tirerCartesTresors(G);
                addAventurier(Joueur);
                System.out.println("Vous etes le messager \n");

            }

        }
    }

    private void initialiserGrille() {

        int ind = 0;
        Iles[] liste = Iles.values();

        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                if (((y != 2 && y != 3) && (x == 0 || x == 5)) || ((x == 1 || x == 4) && (y == 0 || y == 5))) {
                    G.setTuile(x, y, new Tuile(null, Utils.EtatTuile.COULEE, x, y));
                    //System.out.println(x + " " + y);
                } else {
                    G.setTuile(x, y, new Tuile(liste[ind], Utils.EtatTuile.ASSECHEE, x, y));
                    //System.out.println(ind);

                    ind++;

                }
            }
        }
    }

    private void deplacerJoueur(Tuile tuile) {
        enleverAvTuile();
        AvCourant.setPos(tuile);
        tuile.addAventurier(AvCourant);
        jeu.afficherPion();
    }
    private void enleverAvTuile(){
        AvCourant.getPos().getAventurierssur().remove(AvCourant);
       
    }

    public static void main(String[] args) {
        Controleur C = new Controleur();
        G = new Grille(1);
        C.initialiserGrille();
        C.creationJoueur();
        AvCourant = joueurs.get(0);
        jeu = new vuejeu(G);
        jeu.addObservateur(C);
        jeu.creationPion(joueurs);
        //jeu.afficherNomJoueur(AvCourant);
        jeu.afficher();
        
        
               //jeu.deplacer(C.getJoueurs().get(0));
        //jeu.afficherCartes(C.getJoueurs().get(0));
        //jeu.choisirCarteDefausse(C.getJoueurs().get(0));

    }

}
