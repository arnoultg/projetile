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
public abstract class Aventurier {
    private final Utils.Pion nomRole;
    private final String nomjoueur;
    private Tuile pos;
    private ArrayList<CarteTresor> cartes; 

    public Aventurier(Utils.Pion nomRole, String nomjoueur, Tuile pos) {
        this.nomRole = nomRole;
        this.nomjoueur = nomjoueur;
        this.pos = pos;
    }

    public Utils.Pion getNomRole() {
        return nomRole;
    }

    public String getNomjoueur() {
        return nomjoueur;
    }

    public Tuile getPos() {
        return pos;
    }

    public void setPos(Tuile pos) {
        this.pos = pos;
    }
    
    
    
    public void Avancer (){
        
    }
    
    public void Assecher(){
        
    }
    
    public void tirerCarteTresor() {
        
    }
    
    
}
