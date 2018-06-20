/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.ArrayList;
import modele.Aventurier;
import modele.CarteTresor;
import modele.Tuile;

/**
 *
 * @author lavierp
 */
public class Message {
    TypesMessage type;
    Tuile tuile;
    String action;
    int carte;
    int nbjoueurs;
    ArrayList<String> nomsJoueurs;
    CarteTresor carteTr;
    Aventurier destinataire;

    
    public CarteTresor getCarteTr() {
        return carteTr;
    }

    public Aventurier getDestinataire() {
        return destinataire;
    }
    
    public Message(TypesMessage type) {
        this.type = type;
    }

    public TypesMessage getType() {
        return type;
    }

    public Tuile getTuile() {
        return tuile;
    }

    public String getAction() {
        return action;
    }

    public void setTuile(Tuile tuile) {
        this.tuile = tuile;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    public void setCarte(int carte) {
        this.carte = carte;
    }

    public int getCarte() {
        return carte;
    }

    public int getNbjoueurs() {
        return nbjoueurs;
    }

    public ArrayList<String> getNomsJoueurs() {
        return nomsJoueurs;
    }
    
}
