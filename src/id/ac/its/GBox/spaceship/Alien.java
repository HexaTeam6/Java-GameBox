package id.ac.its.GBox.spaceship;

import javax.swing.ImageIcon;

public class Alien extends Sprite {

    private final int INITIAL_X = 400;

    private Bomb bomb;

    public Alien(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {
        bomb = new Bomb(x, y);

        loadImage("resources/spaceship/alien.png");
        getImageDimensions();
    }

    public void move() {

        if (x < 0) {
            x = INITIAL_X;
        }

        x -= 1;
    }

    public Bomb getBomb() {

        return bomb;
    }

    public class Bomb extends Sprite {

        private boolean destroyed;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);

            this.x = x;
            this.y = y;

            var bombImg = "resources/spaceship/bomb.png";
            var ii = new ImageIcon(bombImg);
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }
    }
}