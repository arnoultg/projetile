/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import java.util.ArrayList;
import modele.Grille;
import vue.vuejeu;

/**
 *
 * @author geitnert
 */
public class Demo_Defaite extends Controleur{

    /**
     * @param args the command line arguments
     */
    
    @Override
    protected void premiereInondations(){
        super.getClass().
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Demo_Defaite C = new Demo_Defaite();
        
        ArrayList<String> noms = new ArrayList<>();
        noms.add("joueur_1");
        noms.add("joueur_2");
        C.initialiserjeu(2, noms,3);
        
        
        /*G = new Grille(1);
        initialiserGrille();
        jeu = new vuejeu(G);
        creationJoueur(nbJoueur, nomsJoueurs);
        premiereInondations();*/
        
    }
    
}
