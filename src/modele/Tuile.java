/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import Enums.Iles;
import Enums.Tresor;
import java.awt.Color;
import java.util.ArrayList;
import util.Utils;

/**
 *
 * @author geitnert
 */
public class Tuile {
    private Iles nom;
    private Utils.EtatTuile etat;
    private int X;
    private int Y;
    private Tresor tresor = null;
    private ArrayList<Aventurier> aventurierssur;

    public Tuile(Iles nom, Utils.EtatTuile etat, int X, int Y) {
        this.nom = nom;
        this.etat = etat;
        this.X = X;
        this.Y = Y;
        aventurierssur = new ArrayList<>();
        
    }

    public Iles getNom() {
        return nom;
    }

    public Utils.EtatTuile getEtat() {
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

    public ArrayList<Aventurier> getAventurierssur() {
        return aventurierssur;
    }
    
    public Color getCouleur (){
        if (this.getEtat() == Utils.EtatTuile.ASSECHEE) {
            return new Color(167, 103, 38);         //marron
        } else if (this.getEtat() == Utils.EtatTuile.COULEE) {
            return new Color(44, 117, 255);         //bleu
        } else {
            return new Color(254, 227, 71);         //jaune
        }
    }

    public void setNom(Iles nom) {
        this.nom = nom;
    }

    public void setEtat(Utils.EtatTuile etat) {
        this.etat = etat;
    }

    public void setTresor(Tresor tresor) {
        this.tresor = tresor;
    }
    public void addAventurier(Aventurier av){
        aventurierssur.add(av);
    }
    
    public void innonde() {
        if (this.getEtat() == Utils.EtatTuile.ASSECHEE) {
            this.setEtat(Utils.EtatTuile.INONDEE);
        } else if (this.getEtat() == Utils.EtatTuile.INONDEE) {
            this.setEtat(Utils.EtatTuile.COULEE);
        }
    }
    
    public void asseche(){
        this.setEtat(Utils.EtatTuile.ASSECHEE);
    }    
}
