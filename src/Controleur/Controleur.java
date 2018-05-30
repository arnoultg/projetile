/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;
import Enums.*;
import java.util.ArrayList;
import modele.*;
import util.Utils;
import vue.vuejeu;


/**
 *
 * @author geitnert
 */
public class Controleur {
    
    private ArrayList<Aventurier> joueurs;

    public Controleur() {
    }

    private Grille getGrille(){
        return null;
    }
    
    private int getNbJoueur(){
        return joueurs.size();
    }
    
    private void assecherTuile(Tuile t){
        t.asseche();
    }
    
    private Aventurier getAvCourant(){
        return null;
    }
    
    private Tuile TuileSelectionnee(){
        return null;
    }
    
    private void initialiserJeu (Grille g) {
        
        int ind = 0;
        Iles[] liste = Iles.values();
        
        for (int x = 0; x<6; x++){
            for (int y = 0; y<6; y++) {
                if (( (y != 2 && y != 3) && (x == 0 || x == 5) ) || ((x == 1 || x == 4) && (y == 0 || y == 5))) {
                    g.setTuile(x, y, new Tuile(null, Utils.EtatTuile.COULEE, x, y));
                    System.out.println(x + " " + y);
                } else {
                    g.setTuile(x, y, new Tuile(liste[ind], Utils.EtatTuile.ASSECHEE, x, y));
                    //System.out.println(ind);
                    
                    ind ++;
                    
                }
            }
        }
    }

    
    
    
    public static void main(String[] args) {
        Controleur C = new Controleur();
        Grille G = new Grille(1);
        C.initialiserJeu(G);
        vuejeu jeu = new vuejeu(G);
        jeu.afficher();
        
        
        
        
    }
    
}
