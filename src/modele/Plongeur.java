/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import java.util.Iterator;
import util.Utils;

/**
 *
 * @author geitnert
 */
public class Plongeur extends Aventurier {
    
    ArrayList<Tuile> dejapasse = new ArrayList();

    public Plongeur(Utils.Pion nomRole, String nomjoueur, Tuile pos) {
        super(nomRole, nomjoueur, pos);
    }

    public ArrayList<Tuile> dispoAssecher(Grille g) {
        ArrayList<Tuile> liste = new ArrayList();
        int x = super.getPos().getX();
        int y = super.getPos().getY();
        Tuile[][] grille = g.getGrilleTuile();

        if ((y != 0) && (grille[x][y - 1].getEtat() == Utils.EtatTuile.INONDEE) && !liste.contains(grille[x][y - 1])) {
            liste.add(grille[x][y - 1]);
        }
        if ((x != 0) && (grille[x - 1][y].getEtat() == Utils.EtatTuile.INONDEE) && !liste.contains(grille[x - 1][y])) {
            liste.add(grille[x - 1][y]);
        }
        if ((x != 5) && (grille[x + 1][y].getEtat() == Utils.EtatTuile.INONDEE) && !liste.contains(grille[x + 1][y])) {
            liste.add(grille[x + 1][y]);
        }
        if ((y != 5) && (grille[x][y + 1].getEtat() == Utils.EtatTuile.INONDEE) && !liste.contains(grille[x][y + 1])) {
            liste.add(grille[x][y + 1]);
        }
        return liste;
    }

    @Override
    public ArrayList<Tuile> tuilesDispoAv(Grille g) {
        ArrayList<Tuile> liste = super.tuilesDispoAv(g);
        if (liste.contains(super.getPos())) {
            liste.remove(super.getPos());
        }
        dejapasse.clear();
        return liste;
    }

    @Override
    public ArrayList<Tuile> tuilesAutour(ArrayList<Tuile> liste, int x, int y, Tuile[][] grille) {

        Tuile t1 = (y !=0 ? grille[x][y - 1] : null);       //défini les tuiles adjacentes a celle de coordonnées x,y
        Tuile t2 = (x !=0 ? grille[x - 1][y] : null);
        Tuile t3 = (x !=5 ? grille[x + 1][y] : null);
        Tuile t4 = (y !=5 ? grille[x][y + 1] : null);
        
        
        if ((y != 0) && (!liste.contains(t1)) && (t1.getNom() != null)) {           //si la tuile existe et si elle n'a pas deja été ajoutée aux tuiles accessibles
            if (t1.getEtat() != Utils.EtatTuile.COULEE) {                           //si elle est assechée ou innondée elle est une destination accessible
                liste.add(t1);
            }
            if ((t1.getEtat() != Utils.EtatTuile.ASSECHEE) && !dejapasse.contains(t1)) {    //si la tuile est innondée ou coulée réexecute la fonction en rapport a cette tuile
                dejapasse.add(t1);                                                          
                liste = tuilesAutour(liste, t1.getX(), t1.getY(), grille);
            }
        }

        if ((x != 0) && (!liste.contains(t2)) && (t2.getNom() != null)) {
            if (t2.getEtat() != Utils.EtatTuile.COULEE) {
                liste.add(t2);
            }
            if ((t2.getEtat() != Utils.EtatTuile.ASSECHEE) && !dejapasse.contains(t2)){
                dejapasse.add(t2);
                liste = tuilesAutour(liste, t2.getX(), t2.getY(), grille);
            }
        }

        if ((x != 5) && (!liste.contains(t3)) && (t3.getNom() != null)) {
            if (t3.getEtat() != Utils.EtatTuile.COULEE) {
                liste.add(t3);
            }
            if ((t3.getEtat() != Utils.EtatTuile.ASSECHEE) && !dejapasse.contains(t3)){
                dejapasse.add(t3);
                liste = tuilesAutour(liste, t3.getX(), t3.getY(), grille);
            }
        }

        if ((y != 5) && (!liste.contains(t4)) && (t4.getNom() != null)) {
            if (t4.getEtat() != Utils.EtatTuile.COULEE) {
                liste.add(t4);
            }
            if ((t4.getEtat() != Utils.EtatTuile.ASSECHEE) && !dejapasse.contains(t4)){
                dejapasse.add(t4);
                liste = tuilesAutour(liste, t4.getX(), t4.getY(), grille);
            }
        }
        

        return liste;
    }

}
