/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import util.Utils;

/**
 *
 * @author geitnert
 */
public class Pilote extends Aventurier {
    
    private boolean pouvoir;            //booleen permettant de savoir si le pilote a deja utilisé son pouvoir une fois dans le tour

    public Pilote(Utils.Pion nomRole, String nomjoueur, Tuile pos) {
        super(nomRole, nomjoueur, pos);
    }
    
    @Override
    public ArrayList<Tuile> tuilesDispoAv(Grille g) {           // si le pouvoir a pas été utilisé le pilote peut aller sur toute tuile non coullée sinon il a le deplacement d'un Aventurier quelconque
        ArrayList<Tuile> liste = new ArrayList();
        
        for (Tuile[] i : g.getGrilleTuile()) {
            for (Tuile j : i){
                if (j.getEtat() != Utils.EtatTuile.COULEE){
                    liste.add(j);
                }
            }
        }
        liste.remove(super.getPos());
        if (!this.isPouvoir()) {
            return liste;
        }else {
            return super.tuilesAutour(new ArrayList(), super.getPos().getX(), super.getPos().getY(), g.getGrilleTuile());
        }
        
    }

    public boolean isPouvoir() {
        return pouvoir;
    }

    public void setPouvoir(boolean pouvoir) {
        this.pouvoir = pouvoir;
    }
    
    
    
}
