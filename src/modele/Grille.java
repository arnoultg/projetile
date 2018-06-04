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

    public Grille(int niveauEau) {
        this.niveauEau = niveauEau;
        grilleTuile = new Tuile[6][6];
        paquetCTresor = new ArrayList<>();
        paquetCInnond = new ArrayList<>();
        initialiserPaquet(paquetCTresor);
        //initialiserPaquet(paquetCInnond);
        
    }

    public ArrayList<CarteTresor> getPaquetCTresor() {
        return paquetCTresor;
    }

    public ArrayList<Carte_Innondation> getPaquetCInnond() {
        return paquetCInnond;
    }

    public int getNiveauEau() {
        return niveauEau;
    }
    

    public void setPaquetCTresor(ArrayList<CarteTresor> paquetCTresor) {
        this.paquetCTresor = paquetCTresor;
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
    
    
    
    public void initialiserPaquet(ArrayList<CarteTresor> paquetCTresor) {
        for (int i = 1; i <= 3; i++) {
            paquetCTresor.add(new Helicoptere());
        }
        for (int i = 1; i <= 2; i++) {
            paquetCTresor.add(new SacDeSable());
        }
        for (int i = 1; i <= 3; i++) {
            paquetCTresor.add(new MonteDesEaux());
        }
        for (Tresor i : Tresor.values()) {
            for (int j = 1; j <= 5; j++) {
                paquetCTresor.add(new C_tresor(i));
            }
        }
        Collections.shuffle(paquetCTresor);
    }

}
