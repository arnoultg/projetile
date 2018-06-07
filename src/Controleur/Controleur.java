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
    private int nbActions = 3;
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

            //if ((action == m.getAction())&& (nbActions > 0)){
            action = (((action == m.getAction()) || (nbActions == 0)) ? null : m.getAction());
            // System.out.println(action);
            if ((action == "deplacer") && (nbActions > 0)) {
                jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.red);
            } else if ((action == "assecher") && (nbActions > 0)) {
                jeu.selecTuile(AvCourant.dispoAssecher(G), Color.red);
            } else if (m.getAction() == "Fin_de_tour") {
                this.finTour();
            }

        } else if (m.getType() == TypesMessage.CLIC_TUILE) {
            if (action == "deplacer") {
                if (AvCourant.tuilesDispoAv(G).contains(m.getTuile())) {
                    //System.out.println(m.getTuile().getNom() + "est la destination");
                    jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.white);
                    this.deplacerJoueur(m.getTuile());
                    action = null;

                }

            } else if (action == "assecher") {
                //System.out.println(m.getTuile().getNom() + "a ete asseché");
                if (AvCourant.dispoAssecher(G).contains(m.getTuile())) {
                    jeu.selecTuile(AvCourant.dispoAssecher(G), Color.white);
                    action = null;
                    this.assechercase(m.getTuile());
                }
            }

        }
    }

    public void finTour() {
        action = null;
        nbActions = 3;

        if ((AvCourant.getNomRole() == Utils.Pion.ROUGE)) {
            ((Ingenieur) AvCourant).setPouvoirEnCours(false);
        }
        if ((AvCourant.getNomRole() == Utils.Pion.BLEU)) {
            ((Pilote) AvCourant).setPouvoir(false);
        }

        AvCourant.tirerCartesTresors(G);
        AvCourant.tirerCarteInnondation(G);

        jeu.maj();
        jeu.afficherCartes(AvCourant);
        //jeu.choisirCarteDefausse(AvCourant);

        //System.out.println("NIVEAU EAU = " + G.getNiveauEau());
        int ind = joueurs.indexOf(AvCourant);
        //System.out.println(ind);
        //System.out.println(joueurs.size()-1);
        AvCourant = (ind == joueurs.size() - 1 ? joueurs.get(0) : joueurs.get(ind + 1));
        jeu.afficherNomJoueur(AvCourant);
        //System.out.println(AvCourant.getNomjoueur());
    }

    private void deplacerJoueur(Tuile tuile) {
        enleverAvTuile();
        AvCourant.setPos(tuile);
        tuile.addAventurier(AvCourant);
        jeu.afficherPion();
        nbActions -= 1;

        if ((AvCourant.getNomRole() == Utils.Pion.BLEU)) {
            ((Pilote) AvCourant).setPouvoir(true);
        }
        if (AvCourant.getNomRole() == Utils.Pion.ROUGE) {
            ((Ingenieur) AvCourant).setPouvoirEnCours(false);
        }
    }

    private void assechercase(Tuile tuile) {
        tuile.asseche();
        jeu.MiseaJourTuile(tuile);
        nbActions -= 1;
        if (AvCourant.getNomRole() == Utils.Pion.ROUGE) {
            if (!((Ingenieur) AvCourant).isPouvoirEnCours()) {
                jeu.selecTuile(AvCourant.dispoAssecher(G), Color.red);
                action = "assecher";
                ((Ingenieur) AvCourant).setPouvoirEnCours(true);
            } else {
                ((Ingenieur) AvCourant).setPouvoirEnCours(false);
                nbActions += 1;
            }
        }
    }

    private void creationJoueur() {
        int nbJoueur = 0;
        Scanner entree = new Scanner(System.in);
        for (int i = 10; i >= 0; i--) {
            System.out.println("Combien de joueurs voulez vous ?");
            nbJoueur = entree.nextInt();
            if (nbJoueur > 1 && nbJoueur < 5) {
                i = i - 10;
            } else {
                if (i == 0) {
                    System.out.println("C'est pas trés sympa");
                } else {
                    System.out.println(i + " essais restants");
                    System.out.println("Mettez un nombre compris entre 2 et 4 :");
                }
            }
        }
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

    private void premiereInondations() {

        G.getTuile(Iles.LA_PORTE_DE_BRONZE).innonde();
        G.getTuile(Iles.OBSERVATOIRE).innonde();
        G.getTuile(Iles.LA_CAVERNE_DU_BRASIER).innonde();
        G.getTuile(Iles.LE_LAGON_PERDU).innonde();
        G.getTuile(Iles.LE_JARDIN_DES_MURMURES).innonde();
        G.getTuile(Iles.LES_DUNES_DE_L_ILLUSION).innonde();
        G.getTuile(Iles.LES_DUNES_DE_L_ILLUSION).innonde();
        G.getTuile(Iles.LE_MARAIS_BRUMEUX).innonde();
        G.getTuile(Iles.LE_MARAIS_BRUMEUX).innonde();
        G.getTuile(Iles.LE_TEMPLE_DE_LA_LUNE).innonde();
        G.getTuile(Iles.LE_TEMPLE_DE_LA_LUNE).innonde();
        G.getTuile(Iles.LE_ROCHER_FANTOME).innonde();
        G.getTuile(Iles.LE_ROCHER_FANTOME).innonde();

        /*
        for (int i = 1; i <= 6; i++) {
            int nbCartesPaquet = G.getPaquetCInnond().size();
            if (nbCartesPaquet > 0) {
                G.getPaquetCInnond().get(0).innondeTuile(G);
                G.getPaquetCInnond().remove(0);
            } else {
                G.initialiserPaquetInnond(G.getPaquetCInnond());
            }
        }
         */
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

    private void enleverAvTuile() {
        AvCourant.getPos().getAventurierssur().remove(AvCourant);

    }

    private void initialiserjeu() {

        G = new Grille(1);
        initialiserGrille();
        creationJoueur();
        premiereInondations();
        AvCourant = joueurs.get(0);
        jeu = new vuejeu(G);
        jeu.addObservateur(this);
        jeu.creationPion(joueurs);
        jeu.afficherNomJoueur(AvCourant);
        jeu.afficher();

    }

    public static void main(String[] args) {
        Controleur C = new Controleur();
        C.initialiserjeu();

        //jeu.afficherCartes(C.getJoueurs().get(0));
        //jeu.choisirCarteDefausse(C.getJoueurs().get(0));
    }

}
