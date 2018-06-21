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
import vue.vueDonnerCarte;
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
    private ArrayList<Tresor> tresorRecupere;
    private CarteTresor carteSpeciale;
    private boolean findujeu = false;

    public Controleur() {
        joueurs = new ArrayList<>();
        tresorRecupere = new ArrayList<>();
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
        int nbCartes = AvCourant.getNbCartes();
        for (int i = nbCartes - 1; i >= 0; i--) {
            if (nbTresDefause < 5 && AvCourant.getCartes().get(i).getNom() == tresor.toString()) {
                defausserCarte(AvCourant.getCartes().get(i));
                nbTresDefause++;
            }
        }
    }

    @Override
    public void traiterMessage(Message m) {

        if (m.getType() == TypesMessage.CLIC_ACTION) {
            if (action != null) {
                jeu.selecTuile(tuilesNonCoulee(), Color.white);
            }
            if (action == "Action Speciale") {
                carteSpeciale = null;
                jeu.miseAJourCartesSpeciales(AvCourant, false);
            }
            action = (action == m.getAction() ? null : m.getAction());

            if (nbActions > 0) {
                if (action == "deplacer") {
                    jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.red);
                } else if (action == "assecher") {
                    jeu.selecTuile(AvCourant.dispoAssecher(G), Color.red);
                } else if (action == "Prendre tresor") {
                    Tresor tresor = AvCourant.getPos().getTresor();
                    if (tresor != null && quatreTresors(tresor) && !tresorRecupere.contains(tresor)) {
                        defausserQuatreTresor(tresor);
                        jeu.tresorGagne(tresor);
                        tresorRecupere.add(tresor);
                        jeu.MiseaJourCartes(AvCourant);
                    } else {
                        System.out.println("Pas de trésor à récuperrer");
                    }
                } else if (action == "Donner carte") {
                    ArrayList<Aventurier> liste = (AvCourant.getClass().getName() == "modele.Messager" ? joueurs : AvCourant.getPos().getAventurierssur());
                    liste.remove(AvCourant);
                    if (liste.size() == 0) {
                        System.out.println("pas d'autres aventuriers sur la tuile");
                    } else {
                        vueDonnerCarte donnercarte = new vueDonnerCarte(liste, AvCourant);
                        donnercarte.afficherCartes(AvCourant);
                        donnercarte.afficher();
                        donnercarte.addObservateur(this);
                    }
                }
            } else {
                if (action != "Action Speciale") {
                    action = null;
                }
            }
            if ((m.getAction() == "Fin de tour") && (AvCourant.getNbCartes() <= 5)) {
                this.finTour();
            }
            
           
        } else if (m.getType() == TypesMessage.CLIC_TUILE) {
            if (action == "deplacer") {
                if (AvCourant.tuilesDispoAv(G).contains(m.getTuile())) {
                    jeu.selecTuile(AvCourant.tuilesDispoAv(G), Color.white);
                    this.deplacerJoueur(m.getTuile());
                    action = null;
                    nbActions -= 1;
                }
            } else if (action == "assecher") {
                if (AvCourant.dispoAssecher(G).contains(m.getTuile())) {
                    jeu.selecTuile(AvCourant.dispoAssecher(G), Color.white);
                    action = null;
                    this.assechercase(m.getTuile());
                    nbActions -= 1;
                }
            }

        } else if (m.getType() == TypesMessage.CLIC_CARTE) { // && (action != "Donner Tresor")) {
            if (AvCourant.getNbCartes() > 5) {
                defausserCarte(AvCourant.getCartes().get(m.getCarte()));
                jeu.MiseaJourCartes(AvCourant);
            }
        } else if (m.getType() == TypesMessage.DEMARRER) {
            initialiserjeu(m.getNbjoueurs(), m.getNomsJoueurs());

        } else if (m.getType() == TypesMessage.VALIDER) {
            donnerCTresor(m.getDestinataire(), m.getCarteTr());
            jeu.MiseaJourCartes(AvCourant);
            nbActions -= 1;
            action = null;
        } else if (action == "Action Speciale") {
            System.out.println("passer par la");
            jeu.miseAJourCartesSpeciales(AvCourant, true);
            if (m.getType() == TypesMessage.CLIC_CARTE) {
                carteSpeciale = AvCourant.getCartes().get(m.getCarte());
            }
            if (carteSpeciale.getClass().getName() == "modele.Helicoptere") {
                if (conditionVictoire()) {

                } else {
                    jeu.selecTuile(tuilesNonCoulee(), Color.red);
                }
                if (m.getType() == TypesMessage.CLIC_TUILE) {
                    AvCourant.setPos(m.getTuile());        //déplace le joueur sur la tuile séléctionnée
                    jeu.afficherPion();
                    jeu.miseAJourCartesSpeciales(AvCourant, false);
                    defausserCarte(carteSpeciale);
                    jeu.MiseaJourCartes(AvCourant);
                    
                }
            } else if (carteSpeciale.getClass().getName() == "modele.SacDeSable") {
                ArrayList<Tuile> liste = new ArrayList<>();
                for (Tuile[] i : G.getGrilleTuile()) {
                    for (Tuile j : i) {
                        if (j.getEtat() == Utils.EtatTuile.INONDEE) {
                            liste.add(j);
                        }
                    }
                }
                jeu.selecTuile(liste, Color.red);
                if (m.getType() == TypesMessage.CLIC_TUILE) {
                    if (m.getTuile().getEtat() == Utils.EtatTuile.INONDEE)
                    m.getTuile().asseche();    //asseche la tuile séléctionnée
                    jeu.MiseaJourTuile(m.getTuile());
                    jeu.miseAJourCartesSpeciales(AvCourant, false);
                    defausserCarte(carteSpeciale);
                    jeu.MiseaJourCartes(AvCourant);
                }
            }

        }
        System.out.println(action);

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
        //System.out.println(AvCourant);
        //-System.out.println(joueurs);
        AvCourant = (ind == joueurs.size() - 1 ? joueurs.get(0) : joueurs.get(ind + 1));
        jeu.afficherNomJoueur(AvCourant);
        nbActions += (AvCourant.getClass().getName() == "modele.Navigateur" ? 1 : 0);
        jeu.MiseaJourCartes(AvCourant);
        if (findujeu == true) {
            System.out.println("fin");
            jeu.maj();
            //jeu.findujeu();
        }
    }

    private void deplacerJoueur(Tuile tuile) {
        AvCourant.setPos(tuile);        //déplace le joueur sur la tuile séléctionnée
        jeu.afficherPion();
        if (AvCourant.getClass().getName() == "modele.Pilote") {
            ((Pilote) AvCourant).setPouvoir(true);
        }
        if (AvCourant.getClass().getName() == "modele.Ingenieur") {  //désactive le pouvoir de l'ingenieur
            ((Ingenieur) AvCourant).setPouvoirEnCours(false);
        }
    }

    private void assechercase(Tuile tuile) {
        tuile.asseche();    //asseche la tuile séléctionnée
        jeu.MiseaJourTuile(tuile);

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

    private void donnerCTresor(Aventurier av, CarteTresor c) {
        av.addCarte(c);
        AvCourant.removeCarte(c);
    }

    private ArrayList<Tuile> tuilesNonCoulee() {
        ArrayList<Tuile> liste = new ArrayList<>();
        for (Tuile[] i : G.getGrilleTuile()) {
            for (Tuile j : i) {
                if (j.getEtat() != Utils.EtatTuile.COULEE) {
                    liste.add(j);
                }
            }
        }
        return liste;
    }

    private void Helicoptere(Tuile tuile) {
        
    }

    private void sacDeSable(Tuile tuile) {

    }

    private boolean conditionVictoire() {
        for (Aventurier i : joueurs) {
            if (i.getPos().getNom() != Iles.Heliport) {
                return false;
            }
        }
        if (tresorRecupere.size() < 4) {
            return false;
        }
        return true;
    }

    //----------------------------------------Actions sur les cartes-----------------------------------------------
    public void tirerCartesTresors() {  //donne deux cartes du paquet de cartes trésor au joueur, et les retire du paquet

        for (int i = 0; i < 2; i++) {
            int nbCartesPaquet = G.getPaquetCTresor().size();
            if (nbCartesPaquet == 0) {
                G.reinitPaquetTresor();
            }
            CarteTresor c = G.getPaquetCTresor().get(0);
            G.tirerCTresor(c);
            if (c.getNom() == "monté des eaux") {
                System.out.println("carte monté des eaux tiré");
                G.addDefausseCTresor(c);
                ((MonteDesEaux) c).MonteEau(G);
                jeu.miseajourniveau();
            } else {
                AvCourant.addCarte(c);
            }
        }
    }

    public void tirerCarteInnondation() {   //pioche des cartes du paquet de cartes innondation, et les retire du paquet
        for (int i = 0; i < G.getNiveauEau(); i++) {
            if (G.getPaquetCInnond().size() > 0) {
                G.getPaquetCInnond().get(0).innondeTuile(G);
                findujeu = conditionsdefaite();
                if (G.getTuile(G.getPaquetCInnond().get(0).getNomIle()).getEtat() == Utils.EtatTuile.COULEE) {
                    if (G.getTuile(G.getPaquetCInnond().get(0).getNomIle()).getAventurierssur() != null) {
                        this.sauvetageAventurier(G.getTuile(G.getPaquetCInnond().get(0).getNomIle()).getAventurierssur());
                    }
                }
                G.tirerCInnonde(G.getPaquetCInnond().get(0));

            } else {
                G.reinitPaquetInnond();
            }
        }
    }

    public void sauvetageAventurier(ArrayList<Aventurier> aventuriers) {
        for (Aventurier av : aventuriers) {
            ArrayList<Tuile> tuiles = av.tuilesDispoAv(G);
            Collections.shuffle(tuiles);
            if (tuiles.size() > 0) {
                av.setPos(tuiles.get(0));
                jeu.maj();
                jeu.afficherPion();
            } else {
                findujeu = true;
                //System.out.println("fin du jeu");
            }

        }

    }

    public void defausserCarte(CarteTresor c) {
        AvCourant.removeCarte(c);
        G.addDefausseCTresor(c);
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
                //System.out.println("Vous etes le navigateur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.VIOLET) {
                t = G.getTuile(Iles.LA_PORTE_DE_FER);
                Joueur = new Plongeur(Utils.Pion.VIOLET, nomsJoueurs.get(x), t);
                //System.out.println("Vous etes le plongeur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.BLEU) {
                t = G.getTuile(Iles.Heliport);
                Joueur = new Pilote(Utils.Pion.BLEU, nomsJoueurs.get(x), t);
                // System.out.println("Vous etes le pilote \n");

            } else if (lescouleurs.get(x) == Utils.Pion.ROUGE) {
                t = G.getTuile(Iles.LA_PORTE_DE_BRONZE);
                Joueur = new Ingenieur(Utils.Pion.ROUGE, nomsJoueurs.get(x), t);
                //System.out.println("Vous etes l'ingenieur \n");

            } else if (lescouleurs.get(x) == Utils.Pion.VERT) {
                t = G.getTuile(Iles.LA_PORTE_DE_CUIVRE);
                Joueur = new Explorateur(Utils.Pion.VERT, nomsJoueurs.get(x), t);
                // System.out.println("Vous etes l'explorateur \n");

            } else /* if (lescouleurs.get(x) == Utils.Pion.ORANGE)*/ {
                t = G.getTuile(Iles.LA_PORTE_D_ARGENT);
                Joueur = new Messager(Utils.Pion.ORANGE, nomsJoueurs.get(x), t);
                // System.out.println("Vous etes le messager \n");

            }
            addAventurier(Joueur);
            AvCourant = Joueur;
            tirerCartesTresors();
        }
    }

    private void premiereInondations() {    //innondation de début de partie
        for (int i = 1; i <= 6; i++) {
            G.getPaquetCInnond().get(0).innondeTuile(G);
            G.tirerCInnonde(G.getPaquetCInnond().get(0));
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

    public boolean conditionsdefaite() {
        if (G.getTuile(Iles.Heliport)
                .getEtat() == Utils.EtatTuile.COULEE) {
            System.out.println("heliport");
            return true;

        } else if (G.getTuile(Iles.LE_TEMPLE_DE_LA_LUNE)
                .getEtat() == Utils.EtatTuile.COULEE
                && G.getTuile(Iles.LE_TEMPLE_DU_SOLEIL).getEtat() == Utils.EtatTuile.COULEE
                && tresorRecupere.contains(Tresor.PIERRE) == false) {
            System.out.println("pierre");
            return true;

        } else if (G.getTuile(Iles.LE_JARDIN_DES_MURMURES)
                .getEtat() == Utils.EtatTuile.COULEE
                && G.getTuile(Iles.LE_JARDIN_DES_HURLEMENTS).getEtat() == Utils.EtatTuile.COULEE
                && tresorRecupere.contains(Tresor.STATUE) == false) {
            System.out.println("statue");
            return true;
        } else if (G.getTuile(Iles.LA_CAVERNE_DU_BRASIER)
                .getEtat() == Utils.EtatTuile.COULEE
                && G.getTuile(Iles.LA_CAVERNE_DES_OMBRES).getEtat() == Utils.EtatTuile.COULEE
                && tresorRecupere.contains(Tresor.CRYSTAL) == false) {
            System.out.println("crystal");
            return true;
        } else if (G.getTuile(Iles.LE_PALAIS_DES_MAREES)
                .getEtat() == Utils.EtatTuile.COULEE
                && G.getTuile(Iles.LE_PALAIS_DE_CORAIL).getEtat() == Utils.EtatTuile.COULEE
                && tresorRecupere.contains(Tresor.CALYCE) == false) {
            System.out.println("calyce");
            return true;
        } else {
            return false;
        }

    }

    private void initialiserjeu(int nbJoueur, ArrayList<String> nomsJoueurs) {

        G = new Grille(1);
        initialiserGrille();
        jeu = new vuejeu(G);
        creationJoueur(nbJoueur, nomsJoueurs);
        premiereInondations();
        AvCourant = joueurs.get(0);
        jeu.maj();
        jeu.addObservateur(this);
        jeu.creationPion(joueurs);
        jeu.creationTresor();
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
