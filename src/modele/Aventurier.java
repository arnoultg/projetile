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
        this.setPos(pos);
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
        //if (this.pos.getAventurierssur().contains(this)) {
            pos.getAventurierssur().remove(this);
        //}
        this.pos = pos;
        pos.addAventurier(this);
        System.out.println(pos.getAventurierssur());
    }

    public ArrayList<CarteTresor> getCartes() {
        return cartes;
    }

    public void addCarte(CarteTresor c) {
        cartes.add(c);
    }
    public void removeCarte(CarteTresor c) {
        cartes.remove(c);
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

}
