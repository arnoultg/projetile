/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Enums.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import modele.*;
import util.Utils;
import vue.vuejeu;

/**
 *
 * @author geitnert
 */
public class Controleur {

    private ArrayList<Aventurier> joueurs;
    

    public Controleur() {
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
    private Aventurier addAventurier(Aventurier av){
        joueurs.add(av);
    }
    
   

    private void creationJoueur(Grille g) {
        System.out.println("combien de joueur voulais vous ?");
        Scanner entree = new Scanner(System.in);
        int nbJoueur = entree.nextInt();
        ArrayList<Utils.Pion> lescouleurs = new ArrayList<>();

        for (int x = 0; x < Utils.Pion.values().length; x++) {
            lescouleurs.add(Utils.Pion.values()[x]);
        }
        Collections.shuffle(lescouleurs);

        for (int x = 0; x < nbJoueur; x++) {
            System.out.println("joueur n°"+x+1);
            System.out.println("quelle est votre nom ?");
            String nomjoueur = entree.next();
            
            
            if (lescouleurs.get(x) == Utils.Pion.JAUNE) {
                Tuile t = g.getTuile(Iles.LA_PORTE_D_OR);
                Navigateur Joueur = new Navigateur(Utils.Pion.JAUNE, nomjoueur, t);
                t.addAventurier(Joueur);
                joueurs.add(Joueur);

            } else if (lescouleurs.get(x) == Utils.Pion.VIOLET) {
                Tuile t = g.getTuile(Iles.LA_PORTE_DE_FER);
                Plongeur Joueur = new Plongeur(Utils.Pion.VIOLET, nomjoueur, t);
                t.addAventurier(Joueur);
                joueurs.add(Joueur);

            } else if (lescouleurs.get(x) == Utils.Pion.BLEU) {
                Tuile t = g.getTuile(Iles.HELIPORT);
                Pilote Joueur = new Pilote(Utils.Pion.BLEU, nomjoueur, t);
                t.addAventurier(Joueur);
                joueurs.add(Joueur);

            } else if (lescouleurs.get(x) == Utils.Pion.ROUGE) {
                Tuile t = g.getTuile(Iles.LA_PORTE_DE_BRONZE);
                Ingenieur Joueur = new Ingenieur(Utils.Pion.ROUGE, nomjoueur, t);
                t.addAventurier(Joueur);
                joueurs.add(Joueur);

            } else if (lescouleurs.get(x) == Utils.Pion.VERT) {
                Tuile t = g.getTuile(Iles.LA_PORTE_DE_CUIVRE);
                Explorateur Joueur = new Explorateur(Utils.Pion.VERT, nomjoueur, t);
                t.addAventurier(Joueur);
                joueurs.add(Joueur);

            } else if (lescouleurs.get(x) == Utils.Pion.ORANGE) {
                Tuile t = g.getTuile(Iles.LA_PORTE_D_ARGENT);
                Messager Joueur = new Messager(Utils.Pion.BLEU, nomjoueur, t);
                t.addAventurier(Joueur);
                joueurs.add(Joueur);

            }

        }
    }

    private void initialiserJeu(Grille g) {

        int ind = 0;
        Iles[] liste = Iles.values();

        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                if (((y != 2 && y != 3) && (x == 0 || x == 5)) || ((x == 1 || x == 4) && (y == 0 || y == 5))) {
                    g.setTuile(x, y, new Tuile(null, Utils.EtatTuile.COULEE, x, y));
                    //System.out.println(x + " " + y);
                } else {
                    g.setTuile(x, y, new Tuile(liste[ind], Utils.EtatTuile.ASSECHEE, x, y));
                    //System.out.println(ind);

                    ind++;

                }
            }
        }
    }

    public static void main(String[] args) {
        Controleur C = new Controleur();
        Grille G = new Grille(1);
        C.initialiserJeu(G);
        C.creationJoueur(G);
        vuejeu jeu = new vuejeu(G);
        jeu.afficher();

    }

}
