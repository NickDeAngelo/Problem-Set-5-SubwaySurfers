/* Description: This code runs a simple version of the game Subway Surfers. In this game, the objective is to run
                as far as possible without running into objects. In this version, the surfer, who is the red box
                located at the bottom of the panel, moves left (a) and right (s) to avoid getting hit by falling
                blocks and balls. Once the user is hit, the game ends and the score is shown. To start this game, from
                the start screen or gameover screen, all the user must do is hit any key.
                Authors: Nick Yang and Zak Klaiman
 */

package Subwaysurfer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

public class Surfer implements ActionListener, KeyListener {

    public static Surfer subwaySurfer;

    public final int WIDTH = 800, HEIGHT = 800;  // Sets consistent dimensions and allows the board to be split neatly

    public Renderer renderer;

    public Rectangle surfer;

   // public Rectangle ballSub;

    public int ticks, xMotion;

    public ArrayList<Rectangle> blocks;

    public boolean gameOver, started;

    public Random rand;

    public int score;

    public boolean isJump = false;

    public double speed;

    public Subwaysurfer.ball b = new Subwaysurfer.ball();

    public Surfer (){
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);

        renderer = new Renderer();
        rand = new Random();

        jframe.add(renderer);
        jframe.setTitle("Subway Surfer");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setVisible(true);

        surfer = new Rectangle(WIDTH / 2 - 800/6  + 75, HEIGHT - 800 / 6 + 50, 800/6 - 25, 800/6 - 25);
        // Sets the surfer's dimensions
        blocks = new ArrayList<Rectangle>();


        addBlock(true);
        addBall(true);


        timer.start();
    }


    public void addBlock(boolean ready){ // method for adding a block
            int n = rand.nextInt(100);
            int space = 300;
            int width = WIDTH / 3;
            int height = HEIGHT / 3;

            if (!ready) { // changes the placement of the block in one of three columns.
                if (n % 3 == 0) {
                    blocks.clear();
                    blocks.add(new Rectangle(0, 0, width, height));
                }
                if (n % 3 == 1) {
                    blocks.clear();
                    blocks.add(new Rectangle(width, 0, width, height));
                }
                if (n % 3 == 2) {
                    blocks.clear();
                    blocks.add(new Rectangle(width * 2, 0, width, height));
                }
            }
            else {
                if (score % 3 == 0) {
                    blocks.clear();
                    blocks.add(new Rectangle(0, 0, width, height));
                }
                if (score % 3 == 1) {
                    blocks.clear();
                    blocks.add(new Rectangle(width, 0, width, height));
                }
                if (score % 3 == 2) {
                    blocks.clear();
                    blocks.add(new Rectangle(width * 2, 0, width, height));
                }
            }


    }

    public void paintBlock(Graphics g, Rectangle column){ //this method paints the block after it is added
        g.setColor(Color.gray);
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void addBall(boolean ready){ // this method calls the ball class to create and move a ball.
        if (score % 5 == 0 && score % 8 != 0) {
          //  int o = rand.nextInt(99);
            b.prepRadius(score);
            b.prepX((score + 1) % 3, score);
            b.prepY(score);
            int horizontal, vertical, radius;
        }
    }

    public void shiftRight() { // this method moves the surfer (user) to the right
        if (gameOver) {
            surfer.x = surfer.x + 800/3;

            surfer = new Rectangle(surfer.x, surfer.y, surfer.width, surfer.height);
            blocks.clear();
            xMotion = 0;

            score = 0;

            addBlock(true);
            addBlock(true);
            addBall(true);
            addBall(true);

            gameOver = false;
        }

        if (!started) {
            started = true;
        }

        surfer.x += WIDTH / 3;

        if (surfer.x > WIDTH - surfer.width) {
            surfer.x = WIDTH / 2 - 800/6  + 75 + WIDTH / 3;
        }
    }

    public void shiftLeft() { // this method moves the surfer (user) to the left.
        if (gameOver) {
            surfer.x = surfer.x - WIDTH / 3;

            surfer = new Rectangle(surfer.x, surfer.y, surfer.width, surfer.height);
            blocks.clear();
            xMotion = 0;
            //isJump = false;

            score = 0;

            addBlock(true);
            addBlock(true);
            addBall(true);
            addBall(true);

            gameOver = false;
        }

        if (!started) {
            started = true;
        }
        surfer.x -= WIDTH / 3;
        if (surfer.x < 0){
            surfer.x = WIDTH / 2 - 800/6  + 75 - WIDTH / 3;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        //Creates the motion of the blocks falling and measures if the block intersects

        ticks++;

        speed = 10;

        if (started) {
            b.moveBall(score);
            for (int i = 0; i < blocks.size(); i++) {
                Rectangle block = blocks.get(i);

                block.y += speed;
                if (block.y > HEIGHT){
                    block.y =-block.height;
                   addBlock(false);
                   addBall(true);
                }
            }

            for (int i = 0; i < blocks.size(); i++) {
                Rectangle block = blocks.get(i);

                if (block.y + block.height < 0) {
                    blocks.remove(block);
                    addBlock(false);
                    addBall(true);
                }
                block.x += xMotion;
            }

            for (Rectangle block : blocks) {
                if (block.intersects(surfer)) { //measures if the block intersects
                    gameOver = true;
                }
                else if (block.y == HEIGHT){//if the block does not intersect but passes the surfer (user).
                    if (!gameOver) {
                        score++;
                    }
                }
            }

            if (b.getX() - b.getRadius() <= surfer.x && b.getX() + b.getRadius() >= surfer.x
                    && (b.getY() + b.getRadius() >= surfer.y) && b.getY() - b.getRadius() <= surfer.y){
                //measures if the ball intersects
                gameOver = true;
            }
        }

        renderer.repaint();
    }

    public void repaint(Graphics g) {//paints the background, starting, and ending pages.
        g.setColor(Color.yellow);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.black);
        g.fillRect(WIDTH / 3 - 12, 0, 24, HEIGHT);
        g.fillRect(2 * WIDTH / 3 - 12, 0, 24, HEIGHT);

        g.setColor(Color.red);
        g.fillRect(surfer.x, surfer.y, surfer.width, surfer.height);

        for (Rectangle blocks : blocks) {
            paintBlock(g, blocks);
        }

        if (!started) { // starting page
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 75));
            g.drawString("Click to start!", WIDTH / 4, HEIGHT / 2 - 75);
        }

        if (gameOver) { // page after the user is hit by a block or ball
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 100));
            g.drawString("Your score: " + score, WIDTH / 4 - 100, HEIGHT / 2 - 100);
        }

        if (!gameOver && started) { // writes the score at the top of the game (records it).
            b.paintBall(g, b);
            g.setFont(new Font("Comic Sans", 1, 35));
            g.drawString(String.valueOf(score), WIDTH / 2 - 1, 100);
        }
    }


    public static void main(String[] args) {
        subwaySurfer = new Surfer();
    }



    @Override
    public void keyReleased(KeyEvent e) { //measure A and S for right and left respectively.
        if (e.getKeyCode() == KeyEvent.VK_A) {
            shiftLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            shiftRight();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
}