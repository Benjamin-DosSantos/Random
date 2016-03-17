import java.util.ArrayList;

public class Snake
{
	public  ArrayList<Point> snakePoints = new ArrayList<Point>();
	
	public Snake()
	{
		for (int i=0; i<70; i++)
		{
			snakePoints.add(new Point(Constants.x/2+i, Constants.y/2));
		}
	}
}
