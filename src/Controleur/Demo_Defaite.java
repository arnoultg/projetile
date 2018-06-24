/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Enums.Iles;
import Enums.Tresor;
import java.util.ArrayList;
import modele.Carte_Innondation;
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
        super.getGrille().getTuile(Iles.Le_Temple_De_La_Lune).innonde();
        super.getGrille().getTuile(Iles.Le_Temple_De_La_Lune).innonde();
        super.getGrille().getTuile(Iles.Le_Temple_Du_Soleil).innonde();
        super.getGrille().getTuile(Iles.Le_Jardin_Des_Hurlements).innonde();
        super.getGrille().getTuile(Iles.Le_Jardin_Des_Hurlements).innonde();
        super.getGrille().getTuile(Iles.Le_Jardin_Des_Murmures).innonde();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Demo_Defaite C = new Demo_Defaite();
        
        ArrayList<String> noms = new ArrayList<>();
        noms.add("joueur_1");
        noms.add("joueur_2");
        C.initialiserjeu(2, noms,2);
        C.getTresorRecupere().add(Tresor.STATUE);
        
        C.getGrille().getPaquetCInnond().clear();
        C.getGrille().getPaquetCInnond().add(0, new Carte_Innondation(Iles.Le_Jardin_Des_Murmures));
        C.getGrille().getPaquetCInnond().add(1, new Carte_Innondation(Iles.La_Porte_De_Fer));
        C.getGrille().getPaquetCInnond().add(2, new Carte_Innondation(Iles.Le_Temple_Du_Soleil));
        C.getGrille().getPaquetCInnond().add(3, new Carte_Innondation(Iles.La_Caverne_Des_Ombres));
        
    }
    
}
