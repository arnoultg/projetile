/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;
import Enums.*;
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
                if ((x == 0 || x == 5) && (y != 2 || y != 3)) {
                    
                } 
            }
        }
    }

    
    
    
    public static void main(String[] args) {
        Controleur C = new Controleur();
        Grille G = new Grille(1);
        
        
        
        
    }
    
}
