/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Enums.Iles;
import Enums.Tresor;
import java.util.ArrayList;
import modele.C_tresor;
import modele.Carte_Innondation;
import modele.Helicoptere;
import modele.Tuile;

/**
 *
 * @author geitnert
 */
public class Demo_Victoire extends Controleur{

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Demo_Defaite C = new Demo_Defaite();
        
        ArrayList<String> noms = new ArrayList<>();
        noms.add("joueur_1");
        noms.add("joueur_2");
        C.initialiserjeu(2, noms,2);
        C.getTresorRecupere().add(Tresor.STATUE);
        C.getTresorRecupere().add(Tresor.CRYSTAL);
        C.getTresorRecupere().add(Tresor.PIERRE);
        
        C.getJoueurs().get(0).setPos(C.getGrille().getTuile(Iles.Le_Palais_De_Corail));
        C.getJoueurs().get(1).setPos(C.getGrille().getTuile(Iles.Heliport));
        C.getJeu().afficherPion();
        
        C.getJoueurs().get(0).addCarte(new C_tresor(Tresor.CALYCE));
        C.getJoueurs().get(0).addCarte(new C_tresor(Tresor.CALYCE));
        C.getJoueurs().get(0).addCarte(new C_tresor(Tresor.CALYCE));
        C.getJoueurs().get(0).addCarte(new C_tresor(Tresor.CALYCE));
        C.getJoueurs().get(1).addCarte(new Helicoptere());
        
        C.getJeu().MiseaJourCartes(C.getJoueurs().get(0));
        C.getJeu().MiseaJourCartes(C.getJoueurs().get(1));
        
        for (Tuile[] i : C.getGrille().getGrilleTuile()){
            for (Tuile t : i){
                C.getJeu().MiseaJourTuile(t);
            }
        }
        
        
    }
    
}
