/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import util.Utils;

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

    public void Avancer() {

    }

    public void Assecher() {

    }
    
    public ArrayList<Tuile> tuilesDispo(Grille g) {
        ArrayList<Tuile> liste = new ArrayList();
        int x = pos.getX();
        int y = pos.getY();
        Tuile[][] grille = g.getGrilleTuile();
        
        return tuilesAutour(liste, x, y, grille);
    }

    public ArrayList<Tuile> tuilesAutour(ArrayList<Tuile> liste, int x, int y, Tuile[][] grille) {

        if ((y != 0) && (grille[x][y - 1].getEtat() != Utils.EtatTuile.COULEE)) {
            liste.add(grille[x][y - 1]);
        }
        if ((x != 0) && (grille[x][y - 1].getEtat() != Utils.EtatTuile.COULEE)) {
            liste.add(grille[x-1][y]);
        }
        if ((x != 5) && (grille[x][y - 1].getEtat() != Utils.EtatTuile.COULEE)) {
            liste.add(grille[x+1][y]);
        }
        if ((y != 5) && (grille[x][y - 1].getEtat() != Utils.EtatTuile.COULEE)) {
            liste.add(grille[x][y + 1]);
        }
            return liste;
    }

    
    public int getNbCartes() {
        return cartes.size();
    }

    public void tirerCarteTresor(Grille g, int nbPioche) {
        for (int i = 1; i <= nbPioche; i++) {
            int nbCartesPaquet = g.getPaquetCTresor().size();
            if (nbCartesPaquet > 0) {
                cartes.add(g.getPaquetCTresor().get(i));
                g.getPaquetCTresor().remove(i);
            } else {
                System.out.println("Plus de carte dans le paquet");
            }
        }
    }

}
