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
public class Plongeur extends Aventurier {

    public Plongeur(Utils.Pion nomRole, String nomjoueur, Tuile pos) {
        super(nomRole, nomjoueur, pos);
    }
    
    @Override
    public ArrayList<Tuile> tuilesDispoAv(Grille g) {
        ArrayList<Tuile> liste = super.tuilesDispoAv(g);
        if (liste.contains(super.getPos())) {
            liste.remove(super.getPos());
        }
        for (Tuile i : liste) {
            if (i.getEtat() == Utils.EtatTuile.COULEE) {
                liste.remove(i);
            }
        }
        return liste;
    }
    
    @Override
    public ArrayList<Tuile> tuilesAutour(ArrayList<Tuile> liste, int x, int y, Tuile[][] grille) {

        if ((y != 0) && (grille[x][y - 1].getNom() != null) && !liste.contains(grille[x][y - 1])) {
            liste.add(grille[x][y - 1]);
            if ((grille[x][y - 1].getEtat() == Utils.EtatTuile.COULEE) || (grille[x][y - 1].getEtat() == Utils.EtatTuile.INONDEE)){
                liste = tuilesAutour(liste,x,y,grille);
            }
        }
        if ((x != 0) && (grille[x-1][y].getNom() != null) && !liste.contains(grille[x-1][y])) {
            liste.add(grille[x-1][y]);
            if ((grille[x-1][y].getEtat() == Utils.EtatTuile.COULEE) || (grille[x-1][y].getEtat() == Utils.EtatTuile.INONDEE)){
                liste = tuilesAutour(liste,x,y,grille);
            }
        }
        if ((x != 5) && (grille[x+1][y].getNom() != null) && !liste.contains(grille[x+1][y])) {
            liste.add(grille[x+1][y]);
            if ((grille[x+1][y].getEtat() == Utils.EtatTuile.COULEE) || (grille[x+1][y].getEtat() == Utils.EtatTuile.INONDEE)){
                liste = tuilesAutour(liste,x,y,grille);
            }
        }
        if ((y != 5) && (grille[x][y + 1].getNom() != null) && !liste.contains(grille[x][y + 1])) {
            liste.add(grille[x][y + 1]);
            if ((grille[x][y + 1].getEtat() == Utils.EtatTuile.COULEE) || (grille[x][y + 1].getEtat() == Utils.EtatTuile.INONDEE)){
                liste = tuilesAutour(liste,x,y,grille);
            }
        }
            return liste;
    }
    
}
