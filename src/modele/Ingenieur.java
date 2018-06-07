/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import util.Utils;

/**
 *
 * @author geitnert
 */
public class Ingenieur extends Aventurier {

    private boolean pouvoirEnCours;
    public Ingenieur(Utils.Pion nomRole, String nomjoueur, Tuile pos) {
        super(nomRole, nomjoueur, pos);
    }

    public boolean isPouvoirEnCours() {
        return pouvoirEnCours;
    }

    public void setPouvoirEnCours(boolean pouvoir) {
        this.pouvoirEnCours = pouvoir;
    }
    
    
    
}
