/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import Enums.Iles;
import java.util.ArrayList;
import util.Utils;

/**
 *
 * @author lavierp
 */
public class Grille {
    private int niveauEau;
    private Tuile[][] grilleTuile;
    private ArrayList<Carte_Innondation> paquetCInnond;
    private ArrayList<CarteTresor> paquetCTresor;

    public Grille(int niveauEau) {
        this.niveauEau = niveauEau;
        grilleTuile = new Tuile[6][6];
    }
    
    
    public void deplacerAv(Aventurier a,Tuile depart,Tuile arrivee){
        
    };
    public int getNiv(){
        return niveauEau;
    }
    
    public void setTuile(int x, int y, Tuile T){
        grilleTuile[x][y] = T;
    }
    
    public Tuile getTuile (Iles nom) {
        for (Tuile[] i : grilleTuile) {
            for (Tuile j : i) {
                if (j.getNom() == nom) {

                    return j;
                }
            }
        }
        return null;
    }
}
