package id.ac.its.GBox.spaceship;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class SpaceShipPlay extends JFrame {

    public SpaceShipPlay() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setResizable(false);
        pack();

        setTitle("Collision");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SpaceShipPlay ex = new SpaceShipPlay();
            ex.setVisible(true);
        });
    }
}