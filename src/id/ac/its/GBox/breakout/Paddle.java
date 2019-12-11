package id.ac.its.GBox.breakout;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Paddle extends Sprite  {

    private int dx;

    public Paddle() {

        initPaddle();
    }

    private void initPaddle() {

        loadImage();
        getImageDimensions();

        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("resources/breakout/paddle.png");
        image = ii.getImage();
    }

    void move() {

        x += dx;

        if (x <= 0) {												//mentok kiri

            x = 0;
        }

        if (x >= Commons.WIDTH - imageWidth) {						//mentok kanan

            x = Commons.WIDTH - imageWidth;
        }
    }

    void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {								//gerak ke kiri
        	
            dx = -5;
            
        }

        if (key == KeyEvent.VK_RIGHT) {								//gerak ke kanan

            dx = 5;

        }
    }

    void keyReleased(KeyEvent e) {									//tidak memencet tombol
    																//tidak melakukan apa-apa
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }

    private void resetState() {										//kondisi posisi awal papan pemantul

        x = Commons.INIT_PADDLE_X;							
        y = Commons.INIT_PADDLE_Y;
    }
}