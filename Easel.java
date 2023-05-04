package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Random;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Easel extends JPanel implements ActionListener{
    
    static int GAME_WIDTH = 900;
    static int GAME_HEIGHT = 600;
    static int UNIT_SIZE = 30;

    int fx;
    int fy;

    int score = 0;

    int xSnake[] = new int[400];
    int ySnake[] = new int[400];

    boolean hasGameStarted = false;

    int length = 3;
    char dir = 'R';

    Random random;

    Timer timer;

    Easel(){
        this.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));    
        this.setBackground(Color.black);
        this.addKeyListener(new Key());
        this.setFocusable(true);
        
        random = new Random();
        gameStart();
        
    }



    private void gameStart() {
        spawnFood();
        hasGameStarted = true;
        timer = new Timer(160,this);
        timer.start();
    }



    private void spawnFood() {
        fx = random.nextInt(UNIT_SIZE)*(UNIT_SIZE);
        fy = random.nextInt(GAME_HEIGHT/UNIT_SIZE) * UNIT_SIZE;
    }

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }


    private void draw(Graphics graphics) {
        // draw the food
        if(hasGameStarted){
            graphics.setColor(Color.red);
        graphics.fillOval(fx, fy, UNIT_SIZE-10, UNIT_SIZE-10);

        // create snake
        for(int i=0; i<length ; i++){
            graphics.setColor(Color.gray);
            graphics.fillRect(xSnake[i], ySnake[i], UNIT_SIZE , UNIT_SIZE);
        }

        // putting score on the board
        graphics.setColor(Color.blue);
        graphics.setFont(new Font("Comic Sans",java.awt.Font.BOLD, UNIT_SIZE));
        FontMetrics fme = getFontMetrics(graphics.getFont());
        graphics.drawString("Score : "+score, GAME_WIDTH/2 - fme.stringWidth("Score : "+score)/2, UNIT_SIZE);

        } else {

            // game over 
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Comic Sans",Font.BOLD,80));
            FontMetrics fme = getFontMetrics(graphics.getFont());
            graphics.drawString("GAME OVER", GAME_WIDTH/2 - fme.stringWidth("GAME OVER")/2, GAME_HEIGHT/2 -50 );


            // final score
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Comic Sans",Font.BOLD,UNIT_SIZE));
            FontMetrics fme1 = getFontMetrics(graphics.getFont());
            graphics.drawString("Final Score : "+score, GAME_WIDTH/2 - fme1.stringWidth("Final Score : "+score)/2, GAME_HEIGHT/2);
        
            // press R to replay
            graphics.setColor(Color.green);
            graphics.setFont(new Font("Comic Sans",Font.BOLD,UNIT_SIZE));
            FontMetrics fme2 = getFontMetrics(graphics.getFont());
            graphics.drawString("Press ' R ' to Replay", GAME_WIDTH/2 - fme2.stringWidth("Press ' R ' to Replay")/2, GAME_HEIGHT/2+50);
        
        }
        

    }

    public void eat(){
        if(xSnake[0] == fx && fy == ySnake[0]){
            score++;
            length++;
            spawnFood();
        }

    }

    public void hit(){

        for(int i=length ; i > 0 ;i--){
            if(xSnake[i] == xSnake[0] && ySnake[i] == ySnake[0]){
                hasGameStarted = false;
            }
        }
        if(xSnake[0] > GAME_WIDTH || xSnake[0] < 0 || ySnake[0] > GAME_HEIGHT || ySnake[0] < 0){
            hasGameStarted = false;
        }

        if(hasGameStarted == false){
            timer.stop();
        }
    }

    public void move(){

        for(int i = length ; i > 0 ; i--){
            xSnake[i] = xSnake[i-1];
            ySnake[i] = ySnake[i-1];
        }

        switch(dir){
            case 'D' : ySnake[0] = ySnake[0] + UNIT_SIZE;
            break;
            case 'R' : xSnake[0] = xSnake[0] + UNIT_SIZE;
            break;
            case 'L' : xSnake[0] = xSnake[0] - UNIT_SIZE;
            break;
            case 'U' : ySnake[0] = ySnake[0] - UNIT_SIZE;
        }

        

        
    }

    public class Key extends KeyAdapter{

        
        public void keyPressed(KeyEvent e){
            super.keyPressed(e);
            switch(e.getKeyCode()){
                case KeyEvent.VK_RIGHT:
                if(dir != 'L'){
                    dir = 'R';
                } 
                
                break;
                case KeyEvent.VK_DOWN : 
                if(dir != 'U'){
                    dir = 'D';
                }
                
                break;
                case KeyEvent.VK_LEFT :
                if(dir != 'R'){
                    dir = 'L';
                }
                break;
                case KeyEvent.VK_UP   :
                if(dir != 'D'){
                    dir =  'U';
                } 
                break;
                case KeyEvent.VK_R :
                if(hasGameStarted == false){
                    score = 0;
                    length = 3;
                    dir = 'R';
                    Arrays.fill(xSnake, 0);
                    Arrays.fill(ySnake, 0);
                    gameStart();
                }
            }
            
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        eat();
        hit();
        repaint();
    }
    
}
