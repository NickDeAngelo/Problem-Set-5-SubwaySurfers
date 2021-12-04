//This class creates, returns, paints, and moves a ball the surfer must avoid.

package Subwaysurfer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class ball<b> {

    public int x1, y1, radius1;
    public int WIDTH = 800, HEIGHT = 800;
    //Dimensions are the same as surfer class so everything is positioned correctly
    public Random rand = new Random();

    public int[] x = new int[10000]; //stores x values.
    public int[] y = new int[10000]; //stores y values.
    public int[] radius = new int[10000]; // stores radius

    public ball() { // if no parameters are given
        x1 = -1;
        y1 = -1;
        radius1 = -1;
    }
    public ball(int flat, int up, int across){ // constructor if parameters are given
        x1 = flat;
        y1 = up;
        radius1 = across;
    }



    public void storeBall(){
        // stores the balls numbers so if someone fills up all 99 spots, then random balls will still be generated.
        for (int i = 0; i < 99; i++){
            if (x[i] == 0){
                x[i] = x1;
            }
            if (y[i] == 0){
                y[i] = y1;
            }
            if (radius[i] == 0){
                radius[i] = radius1;
            }
        }
    }

    public void prepX(int n, int score){ // places the X value in the correct spot (middle of the column - radius / 2
        int temp = x[score];
        if (n == 0) {
            temp = WIDTH / 6 - radius[score] / 2;
        }
        if (n == 1) {
            temp = WIDTH / 2 - radius[score] / 2;
        }
        if (n == 2) {
            temp = 5 * WIDTH / 6 - radius[score] / 2;
        }
        x[score] = temp;
        x1 = x[score];
    }

    public void prepY(int n){ //sets y to the correct spot.
        y[n] = radius[n] / 2;
        y1 = y[n];
    }

    public void prepRadius(int n){ // sets radius with a max (got from randInt in class surfer) and min value.
        if (radius[n] == 0){
            radius[n] = rand.nextInt(WIDTH / 3 - 10);
        }
        if (radius[n] < 40){
            radius[n] = 50 + rand.nextInt(25);
        }
        radius1 = radius[n];
    }

    public int getRadius(){ // returns radius.
        return radius1;
    }
    public int getX(){ // returns x
        return x1;
    }
    public int getY(){ // returns y
        return y1;
    }


    public void paintBall(Graphics g, ball b){ // paints the ball
        g.setColor(Color.blue.darker());
        g.fillOval(x1, y1, radius1, radius1);
    }
    public void moveBall(int score){ // moves the ball
        int speed = score; // progressive difficulty with ball moves increasingly fast.
        if (speed < 5){ // ball will never move if score = 0. Fixes this bug.
            speed = 5;
        }
        y1 += speed;
    }
}
