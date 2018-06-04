/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.Tuile;

/**
 *
 * @author lavierp
 */
public class Message {
    TypesMessage type;
    Tuile tuile;
    String action;

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
    
    
}
