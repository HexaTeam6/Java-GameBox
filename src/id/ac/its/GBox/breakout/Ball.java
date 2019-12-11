package id.ac.its.GBox.breakout;

import javax.swing.ImageIcon;

public class Ball extends Sprite {

    private int xdir;
    private int ydir;

    public Ball() {

        initBall();
    }

    private void initBall() {

        xdir = 1;
        ydir = -1;

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("resources/breakout/ball.png");
        image = ii.getImage();
    }

    void move() {

        x += xdir;
        y += ydir;

        if (x == 0) {

//        	System.out.println(imageWidth);
            setXDir(1);
        }

//      kalau menyentuh pojok kanan arena
        if (x == Commons.WIDTH - imageWidth) {

//        	 cek 
//        	System.out.println(ball.getYDir());

//        	 System.out.println(Commons.WIDTH);
            setXDir(-1);
        }

        if (y == 0) {

            setYDir(1);
        }
    }

    private void resetState() {

        x = Commons.INIT_BALL_X;
        y = Commons.INIT_BALL_Y;
    }

    void setXDir(int x) {

        xdir = x;
    }

    void setYDir(int y) {

        ydir = y;
    }

    int getYDir() {

        return ydir;
    }
}