package id.ac.its.GBox.breakout;

import javax.swing.ImageIcon;

public class Ball extends Sprite {

    private int xdir;
    private int ydir;
    
    public Ball() {

        initBall();
    }

    private void initBall() {

        xdir = 1;												//gerak bola ke kanan
        ydir = -1;												//gerak bola ke atas

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("resources/breakout/ball.png");	//load gambar dari direktori
        image = ii.getImage();
    }

    void move() {			

        x += xdir;												//gerak sumbu x bergantung variable xdir
        y += ydir;												//gerak sumbu y bergantung variable ydir

        														//kalau menyentuh pojok kiri arena
        if (x == 0) {

            setXDir(1);											//ke kanan
        }

        														//kalau menyentuh pojok kanan arena
        if (x == Commons.WIDTH - imageWidth) {

            setXDir(-1);										//ke kiri
        }

        if (y == 0) {											//kalau bola menyentuh atas arena

            setYDir(1);											//ke bawah
        }
    }

    private void resetState() {									//kondisi awal bola

        x = Commons.INIT_BALL_X;								
        y = Commons.INIT_BALL_Y;
    }

    void setXDir(int x) {										//gerak sumbu x

        xdir = x;
    }

    void setYDir(int y) {										//gerak sumbu y
  
        ydir = y;
    }

    int getYDir() {												//mendapatkan arah gerakan bola sumbu y

        return ydir;
    }
    
    int getXDir() {												//mendapatkan arah gerakan bola sumbu x

        return ydir;
    }
}