/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.JFrame;

/**
 *
 * @author lavierp
 */
public class TestVue {
        private JFrame frame;


    /**
     * @param args the command line arguments
     */
    public TestVue(){
       frame = new JFrame();
       frame.setTitle("Ma carte");
       frame.setSize(800, 800);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);  
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new TestVue();
    }
    
}
