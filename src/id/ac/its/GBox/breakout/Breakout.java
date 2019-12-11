package id.ac.its.GBox.breakout;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class Breakout extends JFrame { 				//mainapp

    public Breakout() {

        initUI();									//fungsi init
    }

    private void initUI() {

        add(new Board());							//membuat board arena
        setTitle("Breakout Mantap");				//menambahkan judul 

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var game = new Breakout();
            game.setVisible(true);
        });
    }
}