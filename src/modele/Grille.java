/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import Enums.Iles;
import Enums.Tresor;
import java.util.ArrayList;
import java.util.Collections;
import util.Utils;

/**
 *
 * @author lavierp
 */
public class Grille {

    private int niveauEau;
    private Tuile[][] grilleTuile;
    private ArrayList<Carte_Innondation> paquetCInnond;
    private ArrayList<CarteTresor> paquetCTresor;
    private ArrayList<Carte_Innondation> defausseInnond;
    private ArrayList<CarteTresor> defausseTresor;

    public Grille(int niveauEau) {
        this.niveauEau = niveauEau;
        grilleTuile = new Tuile[6][6];
        paquetCTresor = new ArrayList<>();
        paquetCInnond = new ArrayList<>();
        defausseInnond = new ArrayList<>();
        defausseTresor = new ArrayList<>();
        paquetCTresor = initialiserPaquetTresor();
        paquetCInnond = initialiserPaquetInnond();

    }

    public int getNiveauEau() {
        return niveauEau;
    }

    public void setNiveauEau(int niveauEau) {
        this.niveauEau = niveauEau;
    }

    public void setTuile(int x, int y, Tuile T) {
        grilleTuile[x][y] = T;
    }

    public Tuile getTuile(Iles nom) {
        for (Tuile[] i : grilleTuile) {
            for (Tuile j : i) {
                if (j.getNom() == nom) {

                    return j;
                }
            }
        }
        return null;
    }

    public Tuile[][] getGrilleTuile() {
        return grilleTuile;
    }

    public ArrayList<CarteTresor> getPaquetCTresor() {
        return paquetCTresor;
    }

    public ArrayList<Carte_Innondation> getPaquetCInnond() {
        return paquetCInnond;
    }

    public void tirerCTresor(CarteTresor c) {
        paquetCTresor.remove(c);
        defausseTresor.add(c);
    }

    public void tirerCInnonde(Carte_Innondation c) {
        paquetCInnond.remove(c);
        if (getTuile(c.getNomIle()).getEtat() != Utils.EtatTuile.COULEE){
            defausseInnond.add(c);
        };
    }

    public void addDefausseCTresor(CarteTresor c) {
        defausseTresor.add(c);
    }

    public ArrayList<CarteTresor> initialiserPaquetTresor() {    //crée le paquet de carte trésor
        for (int i = 1; i <= 3; i++) {
            paquetCTresor.add(new Helicoptere());    //3 Hélicopteres
        }
        for (int i = 1; i <= 2; i++) {
            paquetCTresor.add(new SacDeSable());     // 2 Sacs de sable
        }
        for (int i = 1; i <= 2; i++) {
            paquetCTresor.add(new MonteDesEaux());   //2 Montees des eaux
        }
        for (Tresor i : Tresor.values()) {
            for (int j = 1; j <= 5; j++) {
                paquetCTresor.add(new C_tresor(i));  //5 trésors de chaques types
            }
        }
        Collections.shuffle(paquetCTresor);  //paquet mélangé
        return paquetCTresor;
    }

    /*public void setPaquetCInnond(ArrayList<Carte_Innondation> paquetCInnond) {
        this.paquetCInnond = paquetCInnond;
    }*/
    public ArrayList<Carte_Innondation> initialiserPaquetInnond() {    //crée le paquet de carte innondation (début de partie)
        for (Iles i : Iles.values()) {
            Carte_Innondation carteinnond = new Carte_Innondation(i);
            paquetCInnond.add(carteinnond);
        }
        Collections.shuffle(paquetCInnond);
        return paquetCInnond;

    }

    public void reinitPaquetInnond() {
        if (defausseInnond.size() != 0) {
            Collections.shuffle(defausseInnond);
            for (Carte_Innondation c : defausseInnond) {
                paquetCInnond.add(0, c);
            }
        }
    }

    public void reinitPaquetTresor() {
        Collections.shuffle(defausseTresor);
        for (CarteTresor i : defausseTresor){
            paquetCTresor.add(i);
        }
        defausseTresor.clear();
    }
    /*
    public ArrayList<Carte_Innondation> nouveauPaquetInnond(ArrayList<Carte_Innondation> paquetInnond) {    //crée un paquet de carte innondation sans les tuiles déjà coulée (milieu de partie)
        paquetInnond.clear();
        for (int i = 0; i <= 23; i++) {
            Carte_Innondation carteinnond = new Carte_Innondation(Iles.values()[i]);
            if ((this.getTuile(carteinnond.getNomIle())).getEtat() != Utils.EtatTuile.COULEE) {
                paquetInnond.add(carteinnond);
            }
        }

        Collections.shuffle(paquetInnond);
        return paquetInnond;

    }*/

}
