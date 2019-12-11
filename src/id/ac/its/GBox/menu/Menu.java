package id.ac.its.GBox.menu;

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
       JButton pongButton = new JButton("Pong Game");
       pongButton.setBounds(20, 10, 580, 185);
       spaceButton.setBounds(20, 210, 580, 180);
       snakeButton.setBounds(20, 410, 580, 180);
       snakeButton.setIcon(new ImageIcon("resources/menu/snakebutton.png"));
       spaceButton.setIcon(new ImageIcon("resources/menu/spacebutton.png"));
       pongButton.setIcon(new ImageIcon("resources/menu/pongbutton.png"));
       
       pongButton.addActionListener(new ActionListener() {
    	   public void actionPerformed(ActionEvent event) {
       
       
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

          }
       });
       
       panel.add(pongButton);
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