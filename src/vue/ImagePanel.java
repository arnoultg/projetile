/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private BufferedImage image;
    private int x;
    private int y;
    private int larg;
    private int longu;
    private int constru;

    public ImagePanel(String cheminImage, int x, int y) {
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(new File(cheminImage));
        } catch (IOException ex) {
            System.out.println("error");
        }
        constru = 0;
        this.setPreferredSize(new Dimension(this.getWidth(),this.getHeight())); 
    }

    public ImagePanel(String cheminImage, int x, int y,int largeur, int longueur) {
        this.x = x;
        this.y = y;
        this.larg = largeur;
        this.longu = longueur;

        try {
            image = ImageIO.read(new File(cheminImage));
        } catch (IOException ex) {
            System.out.println("error");
        }
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); 
        constru = 1;
       
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (constru) {
            case 0: {
                g.drawImage(image, x, y,this.getWidth(),this.getHeight(), this); // see javadoc for more info on the parameters
            }
            case 1: {
                g.drawImage(image, x, y, larg, longu, this);

            }

        }

    }

}
