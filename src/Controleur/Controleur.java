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
    private Aventurier destinataire;
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

    public boolean quatreTresors(Tresor tresor) {
        int nbTres = 0;
        for (int i = 0; i < AvCourant.getNbCartes(); i++) {
            if (AvCourant.getCartes().get(i).getNom() == tresor.toString()) {
                nbTres++;
            }
        }
        return nbTres >= 4;
    }

    public void defausserQuatreTresor(Tresor tresor) {
        int nbTresDefause = 0;
        for (int i = 0; i < AvCourant.getNbCartes(); i++) {
            if (nbTresDefause < 4 && AvCourant.getCartes().get(i).getNom() == tresor.toString()) {
                defausserCarte(AvCourant.getCartes().get(i));
            }
        }
    }

    @Override
    public void traiterMessage(Message m) {

        if (m.getType() == TypesMessage.CLIC_ACTION) {
            if (action != null) {
                jeu.selecTuile(AvCourant.dispoAssecher(G), Color.white);
                jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.white);
            }

            if (action == m.getAction()){
                destinataire = null;
            }
            action = (((action == m.getAction()) || (nbActions == 0)) ? null : m.getAction());
            
            if (nbActions > 0) {
                if (action == "deplacer") {
                    jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.red);
                } else if (action == "assecher"){
                    jeu.selecTuile(AvCourant.dispoAssecher(G), Color.red);
                } else if (action == "Prendre_tresor") {
                    if (quatreTresors(Tresor.PIERRE) && (AvCourant.getPos().getNom() == Iles.LE_TEMPLE_DE_LA_LUNE || AvCourant.getPos().getNom() == Iles.LE_TEMPLE_DU_SOLEIL)) {
                        defausserQuatreTresor(Tresor.PIERRE);
                    } else if (quatreTresors(Tresor.CALYCE) && (AvCourant.getPos().getNom() == Iles.LE_PALAIS_DE_CORAIL || AvCourant.getPos().getNom() == Iles.LE_PALAIS_DES_MAREES)) {
                        defausserQuatreTresor(Tresor.CALYCE);
                    } else if (quatreTresors(Tresor.CRYSTAL) && (AvCourant.getPos().getNom() == Iles.LA_CAVERNE_DU_BRASIER || AvCourant.getPos().getNom() == Iles.LA_CAVERNE_DES_OMBRES)) {
                        defausserQuatreTresor(Tresor.CRYSTAL);
                    } else if (quatreTresors(Tresor.STATUE) && (AvCourant.getPos().getNom() == Iles.LE_JARDIN_DES_HURLEMENTS || AvCourant.getPos().getNom() == Iles.LE_JARDIN_DES_MURMURES)) {
                        defausserQuatreTresor(Tresor.STATUE);
                    } else {
                        System.out.println("Pas de trésor à récuperrer");
                    }
                } else if (action == "Donner_Tresor") {
                    ArrayList<Aventurier> liste =  AvCourant.getPos().getAventurierssur();
                    liste.remove(AvCourant);
                    if (liste.size() == 0) {
                        System.out.println("pas d'autres aventuriers sur la tuile");
                    }else {
                        
                    }
                }
            }
            if ((m.getAction() == "Fin_de_tour") && (AvCourant.getNbCartes() <= 5)) {
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

            
        } else if ((m.getType() == TypesMessage.CLIC_CARTE) && (action != "Donner_Tresor")) {
            if (AvCourant.getNbCartes() > 5) {
                defausserCarte(AvCourant.getCartes().get(m.getCarte()));
                jeu.MiseaJourCartes(AvCourant);
            }
        } else if (m.getType() == TypesMessage.DEMARRER) {
            initialiserjeu(m.getNbjoueurs(), m.getNomsJoueurs());
            
        }else if ((m.getType() == TypesMessage.CLIC_JOUEUR) && (action == "Donner_Tresor")){
            destinataire = AvCourant.getPos().getAventurierssur().get(0);
            
        }else if ((m.getType() == TypesMessage.CLIC_CARTE) && (action == "Donner_Tresor") && (destinataire != null)){
            donnerCTresor(destinataire, AvCourant.getCartes().get(m.getCarte()));
            destinataire = null;
        }
    }

//----------------------------------------Actions d'un tour de jeux-----------------------------------------------    
    public void finTour() {
        action = null;
        nbActions = 3;

        if (AvCourant.getClass().getName() == "modele.Ingenieur") { //réinitialisation des pouvoirs du pilote et de l'ingenieur
            ((Ingenieur) AvCourant).setPouvoirEnCours(false);
        }
        if (AvCourant.getClass().getName() == "modele.Pilote") {
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
        nbActions += (AvCourant.getClass().getName() == "modele.Navigateur" ? 1 : 0);
        jeu.MiseaJourCartes(AvCourant);
    }

    private void deplacerJoueur(Tuile tuile) {
        enleverAvTuile();   //déplace le joueur sur la tuile séléctionnée
        AvCourant.setPos(tuile);
        tuile.addAventurier(AvCourant);
        jeu.afficherPion();
        nbActions -= 1;

        if (AvCourant.getClass().getName() == "modele.Pilote") {  //active le pouvoir du pilote
            ((Pilote) AvCourant).setPouvoir(true);
        }
        if (AvCourant.getClass().getName() == "modele.Ingenieur") {  //désactive le pouvoir de l'ingenieur
            ((Ingenieur) AvCourant).setPouvoirEnCours(false);
        }
    }

    private void assechercase(Tuile tuile) {
        tuile.asseche();    //asseche la tuile séléctionnée
        jeu.MiseaJourTuile(tuile);
        nbActions -= 1;
        if (AvCourant.getClass().getName() == "modele.Ingenieur") {
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
                System.out.println("reinit");
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

    public void defausserCarte(CarteTresor c) {
        AvCourant.removeCarte(c);
        G.addDefausseCTresor(c);
    }

    private void enleverAvTuile() {
        AvCourant.getPos().getAventurierssur().remove(AvCourant);

    }

    private void donnerCTresor(Aventurier av, CarteTresor c) {
        boolean conditions = (AvCourant.getClass().getName() == "modele.Messager" ? true : (AvCourant.getPos().equals(av.getPos())&& (c.getClass().getName() == "modele.C_tresor")));
        if (conditions) {
            av.addCarte(c);
            AvCourant.removeCarte(c);
        }
    }

//----------------------------------------Initialisation du jeu-----------------------------------------------
    private void creationJoueur(int nbJoueur, ArrayList<String> nomsJoueurs) {

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
            System.out.println(AvCourant.getClass().getName());
            System.out.println(AvCourant.getClass().getName() == "modele.Explorateur");
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
        initialiserTresor();
    }

    private void initialiserTresor() {
        G.getTuile(Iles.LE_TEMPLE_DE_LA_LUNE).setTresor(Tresor.PIERRE);
        G.getTuile(Iles.LE_TEMPLE_DU_SOLEIL).setTresor(Tresor.PIERRE);

        G.getTuile(Iles.LE_JARDIN_DES_MURMURES).setTresor(Tresor.STATUE);
        G.getTuile(Iles.LE_JARDIN_DES_HURLEMENTS).setTresor(Tresor.STATUE);

        G.getTuile(Iles.LA_CAVERNE_DU_BRASIER).setTresor(Tresor.CRYSTAL);
        G.getTuile(Iles.LA_CAVERNE_DES_OMBRES).setTresor(Tresor.CRYSTAL);

        G.getTuile(Iles.LE_PALAIS_DES_MAREES).setTresor(Tresor.CALYCE);
        G.getTuile(Iles.LE_PALAIS_DE_CORAIL).setTresor(Tresor.CALYCE);

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
