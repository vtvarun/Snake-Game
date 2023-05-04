package SnakeGame;

import javax.swing.JFrame;

public class Frame extends JFrame {
    
    Frame(){
        this.setTitle("Snake Game");
        this.add(new Easel());
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    
}
