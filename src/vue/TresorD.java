/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import modele.Aventurier;
import modele.Tuile;

/**
 *
 * @author arnoultg
 */
public class TresorD extends JPanel {

    private int centreX, centreY;
    private Color couleur;
    private Tuile tuile;

    public Tuile getTuile() {
        return tuile;
    }

    public TresorD(int centreX, int centreY, Color couleur,Tuile t) {
        setBackground(Color.white);
        setDoubleBuffered(true);

        this.centreX = centreX;
        this.centreY = centreY;
        this.couleur = couleur;
        this.tuile = t;

    }

    public void draw(Graphics g) {
        g.setColor(couleur);
        g.fillRect(centreX, centreY, 20, 20);

    }

    public void paintComponent(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.draw(g);
    }
}
