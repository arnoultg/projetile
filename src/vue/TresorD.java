/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import javax.swing.JPanel;
import modele.Aventurier;

/**
 *
 * @author arnoultg
 */
public class TresorD extends JPanel {

    private int centreX, centreY, rayon;
    private Color couleur;
    private Aventurier aventurier;

    public TresorD(int centreX, int centreY, Color couleur) {
        setBackground(Color.white);
        setDoubleBuffered(true);

        this.centreX = centreX;
        this.centreY = centreY;
        this.rayon = rayon;
        this.couleur = couleur;
        this.aventurier = aventurier;

    }
}
