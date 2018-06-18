/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author geitnert
 */
public class MonteDesEaux extends CarteTresor {
    public MonteDesEaux() {
        super.setNom("monté des eaux");
    }
    public void MonteEau(Grille g){
        g.setNiveauEau(g.getNiveauEau()+1); //monte le niveau d'eau de 1
        g.reinitPaquetInnond(); //reinitialise le paquet avec la defausse mélangée sur le dessus du paquet
        
    }
}
