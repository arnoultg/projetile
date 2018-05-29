/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import Enums.Etat;
import Enums.Iles;
import Enums.Tresor;

/**
 *
 * @author geitnert
 */
public class Tuile {
    private Iles nom;
    private Etat etat;
    private int X;
    private int Y;
    private Tresor tresor = null;

    public Tuile(Iles nom, Etat etat, int X, int Y) {
        this.nom = nom;
        this.etat = etat;
        this.X = X;
        this.Y = Y;
        this.tresor = tresor;
        
    }

    public Iles getNom() {
        return nom;
    }

    public Etat getEtat() {
        return etat;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public Tresor getTresor() {
        return tresor;
    }

    public void setNom(Iles nom) {
        this.nom = nom;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public void setTresor(Tresor tresor) {
        this.tresor = tresor;
    }
    
    public void innonde() {
        if (this.getEtat() == Etat.ASSECHEE) {
            this.setEtat(Etat.INNONDE);
        } else if (this.getEtat() == Etat.INNONDE) {
            this.setEtat(Etat.COULEE);
        }
    }
    
}
