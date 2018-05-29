/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;
import Enums.*;
import java.util.ArrayList;
import modele.*;


/**
 *
 * @author geitnert
 */
public class Controleur {

    public Controleur() {
    }

    private void getGrille(){
        
    }
    
    private void initialiserJeu (Grille g) {
        
        for (int x = 0; x<6; x++){
            for (int y = 0; y<6; y++) {
                if (((x == 0 || x == 5) && (y != 2 || y != 3)) || ((x == 1 || x == 4) && (y == 0 || y == 5))) {
                    g.setTuile(x, y, new Tuile(null, Etat.COULEE, x, y));
                } else {
                    indice = (int) Math.random()*liste.length;
                    g.setTuile(x, y, new Tuile(liste [indice], Etat.ASSECHEE, x, y));
                }
            }
        }
    }

    
    
    
    public static void main(String[] args) {
        Controleur C = new Controleur();
        Grille G = new Grille(1);
        
        
        
        
    }
    
}
