import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SnakePanel extends JPanel 
{
	
	/** Colors **/
	
	Color snakecolor = Color.RED;
	
	private static final long serialVersionUID = 1L;
	
	public SnakePanel(int x, int y)
	{
		setBorder(BorderFactory.createBevelBorder(1));
		setSize(x, y);
		setVisible(true);
		
	}
	
	public void update(Graphics g)
	{
		
		paintComponent(g);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(0xCCCCCC));
		g.fillRect(0, 0, Constants.x, Constants.y);
		try
		{
			

			int r = (int)(Math.random()*256);
			int gr = (int)(Math.random()*256);
			int b = (int)(Math.random()*256);
			
			snakecolor = new Color(r, gr, b);
			
			g.setColor(snakecolor);
			int i = 0;
			for (Point point = Constants.snake.snakePoints.get(i);
					i < Constants.snake.snakePoints.size()-4;
					point = Constants.snake.snakePoints.get(i))
			{
				g.fillRect(point.x-2, point.y-2, 4, 4);
				i+=4;
			}	
			g.setColor(new Color(0x009900));
			g.fillRect(Constants.snake.snakePoints.get(i).x-3, 
						Constants.snake.snakePoints.get(i).y-3, 6, 6);	
			g.setColor(new Color(0x990000));
			g.fillRect(Constants.food.x-3, Constants.food.y-3, 6, 6);
		}
		catch (Exception e){}
	}
}
