package id.ac.its.GBox.breakout;

import javax.swing.ImageIcon;

public class Brick extends Sprite {

    private boolean destroyed;

    public Brick(int x, int y) {					

        initBrick(x, y);											//menginisiasi bata
    }

    private void initBrick(int x, int y) {

        this.x = x;													//panjang	
        this.y = y;													//lebar

        destroyed = false;											//belum hancur

        loadImage();												//menjalankan fungsi loadimage
        getImageDimensions();										//fungsi ukuran gambar
    }

    private void loadImage() {

        var ii = new ImageIcon("resources/breakout/brick.png");		//load gambar dari direktori
        image = ii.getImage();										
    }

    boolean isDestroyed() {											//bool hancur

        return destroyed;											//return kondisi menjadi hancur
    }

    void setDestroyed(boolean val) {								//kondisi hancur/belum

        destroyed = val;											//set nilai variable
    }
}