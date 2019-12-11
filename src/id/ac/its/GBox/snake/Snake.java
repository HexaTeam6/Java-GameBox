package id.ac.its.GBox.snake;

import java.awt.*;
import java.awt.event.*;
import static java.lang.String.format;
import java.util.*;
import java.util.List;
import javax.swing.*;

import id.ac.its.GBox.menu.Menu;
 
public class Snake extends JPanel implements Runnable {
	//Tipe data ENUM merupakan tipe data yang khusus untuk kolom dimana nilai datanya
	//sudah kita tentukan sebelumnya.
	//Pilihan ini dapat berisi 1 sampai dengan 65,535 pilihan string.
	//Dimana kolom yang didefinisikan sebagai ENUM hanya dapat memilih satu diantara
	//pilihan string yang tersedia.
	
	enum Dir {
		up(0, -1), right(1, 0), down(0, 1), left(-1, 0);
		Dir(int x, int y) {
			this.x = x; this.y = y;
		}
		
		final int x, y;
	
	}
	//ngerandom makanan
	static final Random rand = new Random();
	//tembok
	static final int WALL = -1;
	//max energi
	static final int MAX_ENERGY = 1500;
	//ukuran
	private final int B_WIDTH = 640;
	private final int B_HEIGHT = 640;
	//background
	private ImageIcon background;
	
	//I use volatile fields when said field is ONLY UPDATED by its owner thread
	//and the value is only read by other threads,
	//you can think of it as a publish/subscribe scenario
	//where there are many observers but only one publisher. 
	volatile boolean gameOver = true;
	
	Thread gameThread;
	//skor, highskor
	private int score, hiScore;
	
	//buat gridnya entar, kan 10 pixel, kanvasnya 640, jadi ada 64x64 grid
	private int nRows = 64;
	private int nCols = 64;
	//direksion
	Dir dir;
	//energi
	private int energy;
 
   int[][] grid;
   //inisiasi snake sama makannya
   List<Point> snake, treats;
   Font smallFont;
 
   public Snake() {
	  //set ukuran
      setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
      //setfont
      setFont(new Font("Helvetica", Font.BOLD, 30));
      //pas tak ilangin gabisa digerakin
      setFocusable(true);
      smallFont = getFont().deriveFont(Font.BOLD, 18);
      initGrid();
      
      //restart
      addMouseListener(
         new MouseAdapter() {
        	 
        	 //buat klik mulai
        	 @Override
        	 public void mousePressed(MouseEvent e) {
               if (gameOver) {
                  startNewGame();
                  repaint();
               }
            }
         });
 
      addKeyListener(
         new KeyAdapter() {
        	 
        	 //buat gerakin
        	 @Override
        	 public void keyPressed(KeyEvent e) {
 
               switch (e.getKeyCode()) {
 
                  case KeyEvent.VK_UP:
                     if (dir != Dir.down)
                        dir = Dir.up;
                     break;
 
                  case KeyEvent.VK_LEFT:
                     if (dir != Dir.right)
                        dir = Dir.left;
                     break;
 
                  case KeyEvent.VK_RIGHT:
                     if (dir != Dir.left)
                        dir = Dir.right;
                     break;
 
                  case KeyEvent.VK_DOWN:
                     if (dir != Dir.up)
                        dir = Dir.down;
                     break;
               }
               repaint();
            }
         });
   }
   
   //awal
   void startNewGame() {
	   
	   gameOver = false;
	   //pas tak ilangin gak ngefek, yaudah ku komen
	   //stop();
	   //inisiasi gridnya
	   initGrid();
	   
	   //buat makanan
	   treats = new LinkedList<>();
	   
	   //awalnya gerak ngiri
	   dir = Dir.left;
	   //energi max
	   energy = MAX_ENERGY;
	   
	   //inisiasi highskor
	   if (score > hiScore)
		   hiScore = score;
	   score = 0;
	   
	   //buat snake
	   snake = new ArrayList<>();
	   //awalnya 7 kotak
	   for (int x = 0; x < 3; x++)
		   snake.add(new Point(nCols / 2 + x, nRows / 2));
 
	   do
		   addTreat();
	   while(treats.isEmpty());
 
      (gameThread = new Thread(this)).start();
   }
 
   	void stop() {
   		if (gameThread != null) {
   			Thread tmp = gameThread;
   			gameThread = null;
   			tmp.interrupt();
   		}
   	}
 
   	void initGrid() {
   		//buat grid
   		grid = new int[nRows][nCols];
   		
   		//buat temboknya, ujung kanan kiri atas bawah
   		for (int r = 0; r < nRows; r++) {
   			for (int c = 0; c < nCols; c++) {
   				if (c == 0 || c == nCols - 1 || r == 0 || r == nRows - 1)
   				grid[r][c] = WALL;
   			}
   		}
   	}
 
   	//biar runnable
   	@Override
   	public void run() {
   		//returns a reference to the currently executing thread object.
   		while (Thread.currentThread() == gameThread) {
   			
   			try {
   				//  Block of code to try
   				//thread will sleep untuk 75-score / 25 ms, dicari paling max
   				Thread.sleep(Math.max(75 - score, 25));
   				
   			} catch (InterruptedException e) {
   			//  Block of code to handle errors
   				return;
   			}
   			
   			//mati kalo energi abis, kena tembok, kena badan
   			if (energyUsed() || hitsWall() || hitsSnake()) {
   				gameOver();
   			}
   			
   			//kalo gamati
   			else {
   				if (eatsTreat()) {
   					score++;
   					energy = MAX_ENERGY;
   					growSnake();
   				}
            moveSnake();
            addTreat();
   			}
   			repaint();
   		}
   	}

   	//energi nya terus berkurang
   	boolean energyUsed() {
   		energy -= 10;
   		return energy <= 0;
   	}
 
   	//kalo kena tembok
   	boolean hitsWall() {
   		Point head = snake.get(0);
   		int nextCol = head.x + dir.x;
   		int nextRow = head.y + dir.y;
   		return grid[nextRow][nextCol] == WALL;
   	}
   	
   	//kalo kena badan
   	boolean hitsSnake() {
	   Point head = snake.get(0);
	   int nextCol = head.x + dir.x;
	   int nextRow = head.y + dir.y;
	   for (Point p : snake)
		   if (p.x == nextCol && p.y == nextRow)
			   return true;
	   return false;
   	}

   //makan makanan
   boolean eatsTreat() {
      Point head = snake.get(0);
      int nextCol = head.x + dir.x;
      int nextRow = head.y + dir.y;
      for (Point p : treats)
         if (p.x == nextCol && p.y == nextRow) {
            return treats.remove(p);
         }
      return false;
   }
 
   void gameOver() {
      gameOver = true;
      stop();
   }
 
   void moveSnake() {
      for (int i = snake.size() - 1; i > 0; i--) {
         Point p1 = snake.get(i - 1);
         Point p2 = snake.get(i);
         p2.x = p1.x;
         p2.y = p1.y;
      }
      Point head = snake.get(0);
      head.x += dir.x;
      head.y += dir.y;
   }
   
   //biar ulernya makin panjang
   void growSnake() {
      Point tail = snake.get(snake.size() - 1);
      int x = tail.x + dir.x;
      int y = tail.y + dir.y;
      snake.add(new Point(x, y));
   }
   
   //nambah makanan
   void addTreat() {
	   //max makanan = 3
	   if (treats.size() < 3) {
		   //random makanan
		   if (rand.nextInt(10) == 0) { // 1 in 10
 
			   if (rand.nextInt(4) != 0) {  // 3 in 4
				   int x, y;
				   
				   while (true) {
 
					   x = rand.nextInt(nCols);
					   y = rand.nextInt(nRows);
					   if (grid[y][x] != 0)
						   continue;
 
					   Point p = new Point(x, y);
					   if (snake.contains(p) || treats.contains(p))
						   continue;
 
					   treats.add(p);
					   break;
				   }
			   } else if (treats.size() > 1)
				   treats.remove(0);
		   }
	   }
   }
 
   //gambar tembok
   void drawGrid(Graphics2D g) {
      g.setColor(Color.white);
      for (int r = 0; r < nRows; r++) {
         for (int c = 0; c < nCols; c++) {
            if (grid[r][c] == WALL)
               g.fillRect(c * 10, r * 10, 10, 10);
         }
      }
   }
   
   //gambar ular
   void drawSnake(Graphics2D g) {
	   //badan
	   g.setColor(Color.orange);
	   //fillRect = isi kotak, 1 kotak = 10 pixel = 1 buah kepala = 1 buah badan = 1 buah makanan
	   for (Point p : snake)
		   g.fillRect(p.x * 10, p.y * 10, 10, 10);
 
	   //kepala
	   g.setColor(energy < 500 ? Color.red : Color.green);
	   Point head = snake.get(0);
	   g.fillRect(head.x * 10, head.y * 10, 10, 10);
   }
   
   //makanan
   void drawTreats(Graphics2D g) {
      g.setColor(Color.magenta);
      for (Point p : treats)
         g.fillRect(p.x * 10, p.y * 10, 10, 10);
   }
 
   void drawStartScreen(Graphics2D g) {
      g.setColor(Color.white);
      g.setFont(getFont());
      //posisi tulisan
      g.drawString("Snake", B_WIDTH / 2 - 40,B_HEIGHT / 2);
      g.setColor(Color.white);
      g.setFont(smallFont);
      g.drawString("(Click To START)", B_WIDTH / 2 - 70,B_HEIGHT / 2 + 20);
      g.drawString("Press 'BackSpace' to Menu", B_WIDTH / 2 - 110,B_HEIGHT / 2 + 100);
   }
 
   void drawScore(Graphics2D g) {
      int h = getHeight();
      g.setFont(smallFont);
      g.setColor(Color.white);
      String s = format("Hi-Score: %d    Score: %d", hiScore, score);
      g.drawString(s, 30, h - 30);
      g.drawString(format("Energy: %d", energy), getWidth() - 150, h - 30);
   }
 
   @Override
   public void paintComponent(Graphics gg) {
      super.paintComponent(gg);
      Graphics2D g = (Graphics2D) gg;
      background = new ImageIcon("src/resources/grass.png");
      background.paintIcon(this, g, 0, 0);
      //kukomen ga ngefek, yaudah
      //setOpaque(false);
      
      //Sets the value of a single preference for the rendering algorithms.
      //kukomen ga masalah :(
      //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
             //RenderingHints.VALUE_ANTIALIAS_ON);
 
      drawGrid(g);
 
      if (gameOver) {
         drawStartScreen(g);
      } else {
         drawSnake(g);
         drawTreats(g);
         drawScore(g);
      }
   	}
 
   public static void main(String[] args) {
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
}