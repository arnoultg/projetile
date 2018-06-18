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
import vue.vueDebut;
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
        return G;
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
            } else if ((m.getAction() == "Fin_de_tour") && (AvCourant.getNbCartes() <= 5)) {
                this.finTour();
            }

        } else if (m.getType() == TypesMessage.CLIC_TUILE) {
            if (action == "deplacer") {
                if (AvCourant.tuilesDispoAv(G).contains(m.getTuile())) {
                    jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.white);
                    this.deplacerJoueur(m.getTuile());
                    action = null;

                }

            } else if (action == "assecher") {
                if (AvCourant.dispoAssecher(G).contains(m.getTuile())) {
                    jeu.selecTuile(AvCourant.dispoAssecher(G), Color.white);
                    action = null;
                    this.assechercase(m.getTuile());
                }
            }

        } else if (m.getType() == TypesMessage.CLIC_CARTE) {
            if (AvCourant.getNbCartes() > 5) {
                defausserCarte(m.getCarte());
                jeu.MiseaJourCartes(AvCourant);
            }
        } else if (m.getType() == TypesMessage.DEMARRER) {
            initialiserjeu(m.getNbjoueurs(), m.getNomsJoueurs());

        }
    }

//----------------------------------------Actions d'un tour de jeux-----------------------------------------------    
    public void finTour() {
        action = null;
        nbActions = 3;

        if ((AvCourant.getNomRole() == Utils.Pion.ROUGE)) { //réinitialisation des pouvoirs du pilote et de l'ingenieur
            ((Ingenieur) AvCourant).setPouvoirEnCours(false);
        }
        if ((AvCourant.getNomRole() == Utils.Pion.BLEU)) {
            ((Pilote) AvCourant).setPouvoir(false);
        }

        tirerCartesTresors();    //pioche des catres trésors et innondations
        tirerCarteInnondation();
        jeu.maj();

        //jeu.afficherCartes(AvCourant);  //affiche les cartes du joueur et lui propose de défausser si il a trop de cartes
        jeu.choisirCarteDefausse(AvCourant);

        int ind = joueurs.indexOf(AvCourant);   //passe au joueur suivant
        AvCourant = (ind == joueurs.size() - 1 ? joueurs.get(0) : joueurs.get(ind + 1));
        jeu.afficherNomJoueur(AvCourant);
        nbActions += (AvCourant.getNomRole() == Utils.Pion.JAUNE ? 1 : 0);
        nbActions += (AvCourant.getNomRole() == Utils.Pion.JAUNE ? 1 : 0);
        jeu.MiseaJourCartes(AvCourant);
    }

    private void deplacerJoueur(Tuile tuile) {
        enleverAvTuile();   //déplace le joueur sur la tuile séléctionnée
        AvCourant.setPos(tuile);
        tuile.addAventurier(AvCourant);
        jeu.afficherPion();
        nbActions -= 1;

        if ((AvCourant.getNomRole() == Utils.Pion.BLEU)) {  //active le pouvoir du pilote
            ((Pilote) AvCourant).setPouvoir(true);
        }
        if (AvCourant.getNomRole() == Utils.Pion.ROUGE) {  //désactive le pouvoir de l'ingenieur
            ((Ingenieur) AvCourant).setPouvoirEnCours(false);
        }
    }

    private void assechercase(Tuile tuile) {
        tuile.asseche();    //asseche la tuile séléctionnée
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

    //----------------------------------------Actions sur les cartes-----------------------------------------------
    public void tirerCartesTresors() {  //donne deux cartes du paquet de cartes trésor au joueur, et les retire du paquet

        for (int i = 0; i < 2; i++) {
            int nbCartesPaquet = G.getPaquetCTresor().size();
            if (nbCartesPaquet == 0) {
                G.reinitPaquetTresor();
            }
            CarteTresor c = G.getPaquetCTresor().get(0);
            if (c.getNom() == "monté des eaux") {
                ((MonteDesEaux) c).MonteEau(G);
                System.out.println("carte monté des eaux tiré");
            } else {
                AvCourant.addCarte(c);
            }
            G.tirerCTresor(c);

        }
    }

    public void tirerCarteInnondation() {   //pioche des cartes du paquet de cartes innondation, et les retire du paquet
        for (int i = 0; i < G.getNiveauEau(); i++) {
            int nbCartesPaquet = G.getPaquetCInnond().size();
            if (nbCartesPaquet > 0) {
                G.getPaquetCInnond().get(0).innondeTuile(G);
                G.tirerCInnonde(G.getPaquetCInnond().get(0));

            } else {
                G.reinitPaquetInnond();
            }
        }
    }

    public void defausserCarte(int c) {
        CarteTresor carte = AvCourant.getCartes().get(c);
        AvCourant.removeCarte(carte);
        G.addDefausseCTresor(carte);
    }

    private void enleverAvTuile() {
        AvCourant.getPos().getAventurierssur().remove(AvCourant);

    }

//----------------------------------------Initialisation du jeu-----------------------------------------------
    private void creationJoueur(int nbJoueur, ArrayList<String> nomsJoueurs) {
        /*
        Scanner entree = new Scanner(System.in);
        for (int i = 10; i >= 0; i--) { //demande le nombre de joueur, recommence si le nombre chosi n'est pas compris entre 2 et 4
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
         */

        ArrayList<Utils.Pion> lescouleurs = new ArrayList<>();

        for (int x = 0; x < Utils.Pion.values().length; x++) {
            lescouleurs.add(Utils.Pion.values()[x]);
        }
        Collections.shuffle(lescouleurs);

        for (int x = 0; x < nbJoueur; x++) {    //demande le nom de chaques joueurs
            //System.out.println("joueur n°" + (x + 1));
            //System.out.println("quel est votre nom ?");
            //String nomjoueur = entree.next();
            Tuile t;
            Aventurier Joueur;
            if (lescouleurs.get(x) == Utils.Pion.JAUNE) {
                t = G.getTuile(Iles.LA_PORTE_D_OR);
                Joueur = new Navigateur(Utils.Pion.JAUNE, nomsJoueurs.get(x), t);
                System.out.println("Vous etes le navigateur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.VIOLET) {
                t = G.getTuile(Iles.LA_PORTE_DE_FER);
                Joueur = new Plongeur(Utils.Pion.VIOLET, nomsJoueurs.get(x), t);
                System.out.println("Vous etes le plongeur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.BLEU) {
                t = G.getTuile(Iles.HELIPORT);
                Joueur = new Pilote(Utils.Pion.BLEU, nomsJoueurs.get(x), t);
                System.out.println("Vous etes le pilote \n");

            } else if (lescouleurs.get(x) == Utils.Pion.ROUGE) {
                t = G.getTuile(Iles.LA_PORTE_DE_BRONZE);
                Joueur = new Ingenieur(Utils.Pion.ROUGE, nomsJoueurs.get(x), t);
                System.out.println("Vous etes l'ingenieur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.VERT) {
                t = G.getTuile(Iles.LA_PORTE_DE_CUIVRE);
                Joueur = new Explorateur(Utils.Pion.VERT, nomsJoueurs.get(x), t);
                System.out.println("Vous etes l'explorateur \n");

            } else /* if (lescouleurs.get(x) == Utils.Pion.ORANGE)*/ {
                t = G.getTuile(Iles.LA_PORTE_D_ARGENT);
                Joueur = new Messager(Utils.Pion.ORANGE, nomsJoueurs.get(x), t);
                System.out.println("Vous etes le messager \n");

            }
            t.addAventurier(Joueur);
            addAventurier(Joueur);
            AvCourant = Joueur;
            tirerCartesTresors();
        }
    }

    private void premiereInondations() {    //innondation de début de partie

        /*
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
        //innondation aléatoire
         */
        for (int i = 1; i <= 6; i++) {
            int nbCartesPaquet = G.getPaquetCInnond().size();
            if (nbCartesPaquet > 0) {
                G.getPaquetCInnond().get(0).innondeTuile(G);
                G.getPaquetCInnond().remove(0);
            } else {
                G.initialiserPaquetInnond();
            }
        }

    }

    private void initialiserGrille() {  //place les tuiles sur la grille

        int ind = 0;
        Iles[] liste = Iles.values();
        
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                if (((y != 2 && y != 3) && (x == 0 || x == 5)) || ((x == 1 || x == 4) && (y == 0 || y == 5))) {
                    G.setTuile(x, y, new Tuile(null, Utils.EtatTuile.COULEE, x, y));
                } else {
                    G.setTuile(x, y, new Tuile(liste[ind], Utils.EtatTuile.ASSECHEE, x, y));

                    ind++;

                }
            }
        }
    }

    private void initialiserjeu(int nbJoueur, ArrayList<String> nomsJoueurs) {

        G = new Grille(1);
        initialiserGrille();
        creationJoueur(nbJoueur, nomsJoueurs);
        premiereInondations();
        AvCourant = joueurs.get(0);
        jeu = new vuejeu(G);
        jeu.addObservateur(this);
        jeu.creationPion(joueurs);
        jeu.afficherNomJoueur(AvCourant);
        jeu.MiseaJourCartes(AvCourant);
        jeu.afficher();

    }

    public static void main(String[] args) {
        Controleur C = new Controleur();
        vueDebut debut = new vueDebut();
        debut.afficher();
        debut.addObservateur(C);

    }

}
