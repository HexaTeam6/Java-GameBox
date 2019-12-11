package id.ac.its.GBox.menu;

import id.ac.its.GBox.breakout.Breakout;
import id.ac.its.GBox.snake.Snake;
import id.ac.its.GBox.spaceship.SpaceShipPlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    public Menu() {
        initUI();
    }

    public final void initUI() {

       JPanel panel = new JPanel();
       getContentPane().add(panel);

       panel.setLayout(null);

       JButton snakeButton = new JButton("Snake Game");
       JButton spaceButton = new JButton("Space Invader");
       JButton bmButton = new JButton("Breakout Mantap");
       
       bmButton.setBounds(20, 10, 580, 185);
       spaceButton.setBounds(20, 210, 580, 180);
       snakeButton.setBounds(20, 410, 580, 180);
       snakeButton.setIcon(new ImageIcon("resources/menu/snakebutton.png"));
       spaceButton.setIcon(new ImageIcon("resources/menu/spacebutton.png"));
       bmButton.setIcon(new ImageIcon("resources/menu/pongbutton.png"));
       
       bmButton.addActionListener(new ActionListener() {
    	   public void actionPerformed(ActionEvent event) {
               EventQueue.invokeLater(() -> {

                   var game = new Breakout();
                   game.setVisible(true);
               });
    	   }
       });
       spaceButton.addActionListener(new ActionListener() {
    	   public void actionPerformed(ActionEvent event) {
               EventQueue.invokeLater(() -> {
                   SpaceShipPlay ex = new SpaceShipPlay();
                   ex.setVisible(true);
               });
    	   }
       });
       snakeButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent event) {
               SwingUtilities.invokeLater(
                       () -> {
                           JFrame mainFrame = new JFrame();
                           mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                           //judul
                           mainFrame.setTitle("Snake");
                           //biar gabisa fullscreen
                           mainFrame.setResizable(false);
                           mainFrame.add(new Snake(), BorderLayout.CENTER);
                           mainFrame.pack();
                           //pas ku komen ga ditengah
                           mainFrame.setLocationRelativeTo(null);
                           //biar keliatan
                           mainFrame.setVisible(true);
                       });
          }
       });
       
       panel.add(bmButton);
       panel.add(spaceButton);
       panel.add(snakeButton);

       setTitle("Menu");
       setSize(640, 640);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
           Menu ex = new Menu();
           ex.setVisible(true);
    }
}