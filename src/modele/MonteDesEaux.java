/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author geitnert
 */
public class MonteDesEaux extends CarteTresor {

    private Grille g;

    public MonteDesEaux() {
    }

    public void MonteeEaux() {
        g.setNiveauEau(g.getNiveauEau()+ 1);
    }
}
