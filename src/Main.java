import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Main
{
	private static int x = 640, y = 480;
	private static JFrame frame;
	private static SnakePanel snakeArea;
	private static int direction = 0;
	private static int score = 0;
	private static boolean pause = false;
	private static int addSize = 0;
	private static long keyDelay = System.currentTimeMillis();
	private static boolean again = true;
	private static boolean gameOver = false;
	
	private static Point createFood()
	{
		Point res;
		Random rand = new Random();
		mainloop: while (true)
		{
			res = new Point(rand.nextInt(x-10), rand.nextInt(y-10));
			for (Point point : Constants.snake.snakePoints)
			{
				if (Math.abs(res.x-point.x)>5 & Math.abs(res.y-point.y)>5
						& res.x>10 & res.y>10)
					break mainloop;
			}
		}
		return res;
	}
	
	public static void main(String[] args) throws InterruptedException 
	{	
		frame = new JFrame("Snake");
		frame.setSize(x+5, y+65);
		frame.setLocationRelativeTo(null);
		x-=10; y-=10; 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setLayout(null);
		frame.setBackground(Color.black);
		frame.setResizable(false);
		
		JPanel scorePanel = new JPanel();
		scorePanel.setSize(x, 50);
		scorePanel.setLocation(0, 0);
		final JLabel scoreLabel = new JLabel("Score: 0");
		scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		scorePanel.add(scoreLabel);
		
		frame.add(scorePanel);
		
		snakeArea = new SnakePanel(x,y);
		snakeArea.setLocation(5, 45);
		frame.add(snakeArea);
		frame.setVisible(true);
		frame.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{	
				long temp = System.currentTimeMillis();
				if (temp-keyDelay<100)
				{
					try
					{
						Thread.sleep(20);
					} 
					catch (InterruptedException e1)
					{}
				}
				keyDelay = temp;
				pause = false;
				scoreLabel.setText("Score: "+score);
				if (!gameOver)
				switch (e.getKeyCode()) 
				{
					case KeyEvent.VK_UP:
						if (direction!=3)
							direction = 1;
						break;
					case KeyEvent.VK_DOWN:
						if (direction!=1)
							direction = 3;
						break;
					case KeyEvent.VK_LEFT:
						if (direction!=0)
							direction = 2;	
						break;
					case KeyEvent.VK_RIGHT:
						if (direction!=2)
							direction = 0;
						break;
					case KeyEvent.VK_ESCAPE:
						scoreLabel.setText("PAUSE");
						pause = true;
						break;
				}
				else
					again = true;
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{}
			@Override
			public void keyTyped(KeyEvent e) 
			{}
		});
		while (true)
		{
			if (again)
			{
				direction = 0;
				score = 0;
				pause = false;
				addSize = 0;
				again = false;
				gameOver = false;
				scoreLabel.setText("Score: 0");
					
				Snake snake = new Snake();
				Constants.snake = snake;
				Constants.food = createFood();
		
				mainloop: while (true)
				{
					Thread.sleep(4);			
					if (pause) continue;
					if (addSize == 0)
					{
						Constants.snake.snakePoints.remove(0);
					}
					else 
						addSize--;
					int headX = Constants.snake.snakePoints.get(Constants.snake.snakePoints.size()-1).x;
					int headY = Constants.snake.snakePoints.get(Constants.snake.snakePoints.size()-1).y;
					
					headX = headX>x-2 ? 2 : headX;
					headY = headY>y-2 ? 2 : headY;
					headX = headX<2 ? x-2 : headX;
					headY = headY<2 ? y-2 : headY;
					
					Point head = new Point(headX, headY);
					
					switch (direction)
					{
						case 0:
							head = new Point(++headX, headY);
							break;
						case 1:
							head = new Point(headX, --headY);
							break;
						case 2:
							head = new Point(--headX, headY);
							break;
						case 3:
							head = new Point(headX, ++headY);
							break;
					}
					
					for (Point point : Constants.snake.snakePoints)
					{
						if (point.x == headX & point.y == headY)
						{
							scoreLabel.setText("Game over. "+scoreLabel.getText()+". Press any key to restart.");
							gameOver = true;
							break mainloop;
						}
					}
					
					if (Math.abs(headX-Constants.food.x)<5 & Math.abs(headY-Constants.food.y)<5)
					{
						score += 1;
						scoreLabel.setText("Score: "+score);
						addSize += 20;
						Constants.food = createFood();
					}
					
					Constants.snake.snakePoints.add(head);			
					snakeArea.repaint();
				}
			}	
		}
	}
 }
