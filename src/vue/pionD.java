/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author grego
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class pionD extends JPanel {

    private int centreX, centreY, rayon;
    private Color couleur;

    public pionD(int centreX, int centreY, int rayon, Color couleur) {
        setBackground(Color.white);
        setDoubleBuffered(true);
        
        this.centreX = centreX;
        this.centreY = centreY;
        this.rayon = rayon;
        this.couleur = couleur;

        //System.out.println("constructeur");
    }

    public void draw(Graphics g) {
        g.setColor(couleur);
        g.fillOval(centreX - rayon, centreY - rayon, 2 * rayon, 2 * rayon);
        
    }

    public void paintComponent(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.draw(g);
    }
}
