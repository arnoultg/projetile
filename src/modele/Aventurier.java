/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import Enums.Iles;
import java.util.ArrayList;
import util.Utils;
import modele.MonteDesEaux;

/**
 *
 * @author geitnert
 */
public abstract class Aventurier {

    private final Utils.Pion nomRole;
    private final String nomjoueur;
    private Tuile pos;
    private ArrayList<CarteTresor> cartes;

    public Aventurier(Utils.Pion nomRole, String nomjoueur, Tuile pos) {
        this.nomRole = nomRole;
        this.nomjoueur = nomjoueur;
        this.pos = pos;
        cartes = new ArrayList<>();
    }

    public Utils.Pion getNomRole() {
        return nomRole;
    }

    public String getNomjoueur() {
        return nomjoueur;
    }

    public Tuile getPos() {
        return pos;
    }

    public void setPos(Tuile pos) {
        this.pos = pos;
    }

    public ArrayList<CarteTresor> getCartes() {
        return cartes;
    }

    public ArrayList<Tuile> dispoAssecher(Grille g) {
        ArrayList<Tuile> liste = new ArrayList();
        ArrayList<Tuile> liste2 = new ArrayList();
        int x = pos.getX();
        int y = pos.getY();
        Tuile[][] grille = g.getGrilleTuile();

        for (Tuile tuile : tuilesAutour(liste, x, y, grille)) {
            if (tuile.getEtat() != Utils.EtatTuile.ASSECHEE) {
                liste2.add(tuile);
            }
        }
        return liste2;
    }

    public ArrayList<Tuile> tuilesDispoAv(Grille g) {
        ArrayList<Tuile> liste = new ArrayList();
        int x = pos.getX();
        int y = pos.getY();
        Tuile[][] grille = g.getGrilleTuile();

        return tuilesAutour(liste, x, y, grille);
    }

    protected ArrayList<Tuile> tuilesAutour(ArrayList<Tuile> liste, int x, int y, Tuile[][] grille) {

        if ((y != 0) && (grille[x][y - 1].getEtat() != Utils.EtatTuile.COULEE) && !liste.contains(grille[x][y - 1])) {
            liste.add(grille[x][y - 1]);
        }
        if ((x != 0) && (grille[x - 1][y].getEtat() != Utils.EtatTuile.COULEE) && !liste.contains(grille[x - 1][y])) {
            liste.add(grille[x - 1][y]);
        }
        if ((x != 5) && (grille[x + 1][y].getEtat() != Utils.EtatTuile.COULEE) && !liste.contains(grille[x + 1][y])) {
            liste.add(grille[x + 1][y]);
        }
        if ((y != 5) && (grille[x][y + 1].getEtat() != Utils.EtatTuile.COULEE) && !liste.contains(grille[x][y + 1])) {
            liste.add(grille[x][y + 1]);
        }
        return liste;
    }

    public int getNbCartes() {
        return cartes.size();
    }

    public void tirerCartesTresors(Grille g) {
        for (int i = 0; i < 2; i++) {
            int nbCartesPaquet = g.getPaquetCTresor().size();
            MonteDesEaux carteMonté = new MonteDesEaux();
            if (nbCartesPaquet > 0) {
                if (g.getPaquetCTresor().get(0).getNom() == "monté des eaux") {
                    carteMonté.MonteEau(g);
                    g.getPaquetCTresor().remove(0);

                } else {
                    cartes.add(g.getPaquetCTresor().get(0));
                    g.getPaquetCTresor().remove(0);
                }

            } else {
                g.initialiserPaquetTresor(g.getPaquetCTresor());
                i--;
            }
        }
    }

    public void tirerCarteInnondation(Grille g) {
        for (int i = 0; i < g.getNiveauEau(); i++) {
            int nbCartesPaquet = g.getPaquetCInnond().size();
            if (nbCartesPaquet > 0) {
                g.getPaquetCInnond().get(0).innondeTuile(g);
                g.getPaquetCInnond().remove(0);
            } else {
                g.initialiserPaquetInnond(g.getPaquetCInnond());
            }
        }
    }

    public void defausserCarte(int c) {
        cartes.remove(c);
    }
}
