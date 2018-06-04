/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import Enums.Iles;

/**
 *
 * @author geitnert
 */
public class Carte_Innondation {
    
    private Iles nomIle;

    public Carte_Innondation(Iles nomIle) {
        this.nomIle = nomIle;
    }
    
    public void innondeTuile (Grille g) {
        g.getTuile(nomIle).innonde();
    }
    
}
