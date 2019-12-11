package id.ac.its.GBox.breakout;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class Breakout extends JFrame { 				//mainapp

    public Breakout() {

        initUI();									//fungsi init user interface
    }

    private void initUI() {

        add(new Board());							//membuat board arena
        setTitle("Breakout Mantap");				//menambahkan judul game

        setDefaultCloseOperation(EXIT_ON_CLOSE);	//perintah selesai
        setLocationRelativeTo(null);				//fungsi mengatur relative 
        setResizable(false);						//arena ukurannya baku
        pack();										//fungsi pack
    }
   
    public static void main(String[] args) {		//mainapp

        EventQueue.invokeLater(() -> {				

            var game = new Breakout();				//menjalankan fungsi breakout
            game.setVisible(true);					//set terlihat
        });
    }
}