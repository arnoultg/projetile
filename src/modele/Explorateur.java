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
public class Explorateur extends Aventurier {

    public Explorateur(Utils.Pion nomRole, String nomjoueur, Tuile pos) {
        super(nomRole, nomjoueur, pos);
    }
    
    
    @Override
    public ArrayList<Tuile> tuilesDispoAv(Grille g) {
        ArrayList<Tuile> liste = super.tuilesDispoAv(g);
        int x = super.getPos().getX();
        int y = super.getPos().getY();
        Tuile[][] grille = g.getGrilleTuile();
        
        if ((y != 0) && (x != 0) && (grille[x][y - 1].getEtat() != Utils.EtatTuile.COULEE)) {
            liste.add(grille[x-1][y - 1]);
        }
        if ((y != 5) && (x != 0) && (grille[x][y - 1].getEtat() != Utils.EtatTuile.COULEE)) {
            liste.add(grille[x-1][y+1]);
        }
        if ((y != 0) && (x != 5) && (grille[x][y - 1].getEtat() != Utils.EtatTuile.COULEE)) {
            liste.add(grille[x+1][y-1]);
        }
        if ((y != 5) && (x != 5) && (grille[x][y - 1].getEtat() != Utils.EtatTuile.COULEE)) {
            liste.add(grille[x+1][y + 1]);
        }
        return liste;
    }
    
}
