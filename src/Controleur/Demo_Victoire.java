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
public class Demo_Victoire extends Controleur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Demo_Victoire C = new Demo_Victoire();

        ArrayList<String> noms = new ArrayList<>();
        noms.add("joueur_1");
        noms.add("joueur_2");
        noms.add("joueur_3");
        noms.add("joueur_4");
        C.initialiserjeu(4, noms, 1);
        C_tresor calyce = new C_tresor(Tresor.CALYCE);
        C_tresor pierre = new C_tresor(Tresor.PIERRE);
        C_tresor crystal = new C_tresor(Tresor.CRYSTAL);
        C_tresor statue = new C_tresor(Tresor.STATUE);

        C.getJoueurs().get(0).getCartes().clear();
        C.getJoueurs().get(0).addCarte(calyce);
        C.getJoueurs().get(0).addCarte(calyce);
        C.getJoueurs().get(0).addCarte(calyce);
        C.getJoueurs().get(0).addCarte(calyce);

        C.getJoueurs().get(1).getCartes().clear();
        C.getJoueurs().get(1).addCarte(pierre);
        C.getJoueurs().get(1).addCarte(pierre);
        C.getJoueurs().get(1).addCarte(pierre);
        C.getJoueurs().get(1).addCarte(pierre);

        C.getJoueurs().get(2).getCartes().clear();
        C.getJoueurs().get(2).addCarte(crystal);
        C.getJoueurs().get(2).addCarte(crystal);
        C.getJoueurs().get(2).addCarte(crystal);
        C.getJoueurs().get(2).addCarte(crystal);

        C.getJoueurs().get(3).getCartes().clear();
        C.getJoueurs().get(3).addCarte(statue);
        C.getJoueurs().get(3).addCarte(statue);
        C.getJoueurs().get(3).addCarte(statue);
        C.getJoueurs().get(3).addCarte(statue);

        C.finTour();

    }

}
