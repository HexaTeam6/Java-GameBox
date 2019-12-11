package id.ac.its.GBox.breakout;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {

    private Timer timer;					//inisiasi variabel waktu
    private String message = "Game Over";	//inisiasi variabel string untuk menampilkan inGame false
    private Ball ball;						//inisiasi variabel bola
    private Paddle paddle;					//inisiasi variabel papan pemantul
    private Brick[] bricks;					//inisiasi variabel kotak bata yang akan dihancurkan
    private boolean inGame = true;			//inisiasi dimulainya game

    public Board() {

        initBoard(); // menjalankan fungsi untuk menginisiasi class board arena utama
    }

    private void initBoard() {

        addKeyListener(new TAdapter()); //fungsi agar bisa menggunakan input tombol dari keyboard
        setFocusable(true);				//fungsi untuk memfokuskan peran tombol perintah
        
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT)); //fungsi untuk mengatur panjang dan lebar arena

        gameInit(); //fungsi untuk menginisiasi game
    }

    private void gameInit() {

        bricks = new Brick[Commons.N_OF_BRICKS];	//membuat bata dari array
        ball = new Ball();							//menjalankan fungsi bola
        paddle = new Paddle();						//menjalankan fungsi papan pemantul

        int k = 0;									//menyusun bata dari array of integer

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 6; j++) {

                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                k++;
            }
        }

        timer = new Timer(Commons.PERIOD, new GameCycle()); //fungsi untuk mengatur waktu
//        System.out.println(Commons.PERIOD);
        timer.start(); 								//memulai timer
    }

    @Override
    public void paintComponent(Graphics g) {					//menggambar komponen
        super.paintComponent(g);								//super constructor

        var g2d = (Graphics2D) g;								//inisiasi variabel 2D

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	//megatur objek 2D
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,		//mengatur objek 2D
                RenderingHints.VALUE_RENDER_QUALITY);

        if (inGame) {											//jika masih inGame

            drawObjects(g2d);									//menjalankan fungsi menggambar objek
            
        } else {												

            gameFinished(g2d);									//fungsi game berakhir
        }

        Toolkit.getDefaultToolkit().sync();						//mengembalikan ke kondisi awal
    }

    private void drawObjects(Graphics2D g2d) {							//fungsi menggambar objek game

        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),		//menggambar bola
                ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),	//menggambar papan pemantul
                paddle.getImageWidth(), paddle.getImageHeight(), this);

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if (!bricks[i].isDestroyed()) {

                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),	//menggambar bata sesuai posisi dari array
                        bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }
    }

    private void gameFinished(Graphics2D g2d) {								//fungsi game berakhir
    	var font = new Font("Chiller", Font.BOLD, 18);						//mengatur jenis, style, dan ukuran pesan
    	FontMetrics fontMetrics = this.getFontMetrics(font);				


        g2d.setColor(Color.BLACK);											//mengatur warna font
        g2d.setFont(font);													//mengatur font pesan
        g2d.drawString(message,												//menampilkan pesan game berakhir
                (Commons.WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.WIDTH / 2);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	
            paddle.keyReleased(e);											//fungsi untuk papan pemantul saat tidak menekan tombol
        }																	

        @Override
        public void keyPressed(KeyEvent e) {

            paddle.keyPressed(e);											//fungsi untuk papan pemantul saat menekan tombol
        }
    }

    private class GameCycle implements ActionListener {						//funsi saat game berjalan, mengimplementasikan daftar perintah						

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();													//fungsi game berjalan
        }
    }

    private void doGameCycle() {											//fungsi saat game berjalan

        ball.move();														//fungsi pergerakan bola
        paddle.move();														//fungsi pergerakan papan pemantul
        checkCollision();													//memeriksa tabrakan bola
        repaint();															//menggambar ulang
    }

    private void stopGame() {												//fungsi untuk menyelesaikan permainan

        inGame = false;														//variabel inGame dibuat false
        timer.stop();														//fungsi timer berhenti
    }

    private void checkCollision() {											//memeriksa tabrakan bola

        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {				//ketika posisi bola sudah dibawah batas papan pemantul

            stopGame();														//fungsi game berakhir
        }

        for (int i = 0, j = 0; i < Commons.N_OF_BRICKS; i++) {				//menginisiasi variabel array sejumlah banyaknya bata

            if (bricks[i].isDestroyed()) {									//jika ada bata yang hancur tertabrak

                j++;														//counter penhitung jumlah bata hancur bertambah
            }

            if (j == Commons.N_OF_BRICKS) {									//jika counter bata yg hancur sudah sama dengan bata total saat awal inisisasi

                message = "Victory";										//bata habis, menampilkan pesan menang
                stopGame();													//fungsi game selesai
            }
        }

        if ((ball.getRect()).intersects(paddle.getRect())) {				//mengecek bola dan pemantul saling berpotongan

            int paddleLPos = (int) paddle.getRect().getMinX();				//posisi permukaan papan pemantul
            int ballLPos = (int) ball.getRect().getMinX();					//posisi perkenaan bola dengan pemantul

            int first = paddleLPos + 8;										//inisiasi variabel posisi 25% permukaan pemantul dari kiri
            int second = paddleLPos + 16;									//inisiasi variabel posisi 50% permukaan pemantul dari kiri
            int third = paddleLPos + 24;									//inisiasi variabel posisi 75% permukaan pemantul dari kiri
            int fourth = paddleLPos + 32;									//inisiasi variabel posisi 75% - 100% permukaan pemantul dari kiri

            if (ballLPos < first) {											//perkenaan diarea <first

                ball.setXDir(-1);											//arah pantul bola ke kiri
                ball.setYDir(-1);											//arah pantul bola ke atas
            }

            if (ballLPos >= first && ballLPos < second) {					//perkenaan di area >=first dan <second

                ball.setXDir(-1);											//arah pantul bola ke kiri
                ball.setYDir(-1 * ball.getYDir());							//arah pantul bola ke atas * besarnya 

            }

            if (ballLPos >= second && ballLPos < third) {					//perkenaan di area >=second dan <third

                ball.setXDir(0);											//arah pantul bola lurus keatas, tidak dipengaruhi sumbu x
                ball.setYDir(-1);											//arah pantul bola ke atas
            }

            if (ballLPos >= third && ballLPos < fourth) {					//perkenaan di area >=third dan <fourth

                ball.setXDir(1);											//arah pantul bola mengikuti arah datang sumbu x ke kanan
                ball.setYDir(-1 * ball.getYDir());							//arah pantul bola ke atas * besarnya
//                System.out.println(ball.getYDir());
            }

            if (ballLPos > fourth) {										//perkenaan di area <fourth

                ball.setXDir(1);											//arah pantul bola mengikuti arah datang sumbu x ke kanan						
                ball.setYDir(-1);											//arah pantul bola ke atas
            }
        }

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {							//loop sebanyak jumlah awal bata

            if ((ball.getRect()).intersects(bricks[i].getRect())) {				//ketika bola bertabrakan dengan bata

                int ballLeft = (int) ball.getRect().getMinX();					//sisi kiri
                int ballHeight = (int) ball.getRect().getHeight();				//sisi kanan
                int ballWidth = (int) ball.getRect().getWidth();				//sisi bawah
                int ballTop = (int) ball.getRect().getMinY();					//sisi atas

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);		
                var pointLeft = new Point(ballLeft - 1, ballTop);
                var pointTop = new Point(ballLeft, ballTop - 1);
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);	

                if (!bricks[i].isDestroyed()) {

                    if (bricks[i].getRect().contains(pointRight)) {

                        ball.setXDir(-1);
                    } else if (bricks[i].getRect().contains(pointLeft)) {

                        ball.setXDir(1);
                    }

                    if (bricks[i].getRect().contains(pointTop)) {

                        ball.setYDir(1);
                    } else if (bricks[i].getRect().contains(pointBottom)) {

                        ball.setYDir(-1);
                    }

                    bricks[i].setDestroyed(true);
                }
            }
        }
    }
}