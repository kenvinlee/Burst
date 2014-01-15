import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;

//just some stars for the 2nd and 3rd level. a bit lacklustre due to time constraints...
public class Scenery 
{
	private Random r;
	private int[] starX, starY;
	
	public Scenery (JFrame f)
	{
		init();
	}

	public void init()
	{
		r = new Random();
		
		starX = new int[100];
		starY = new int[100];
		
		for (int i = 0; i <= 99; i++)
		{
			starX[i] = r.nextInt(570);
			starY[i] = r.nextInt(800);
		}
	}
	
	public void render(Graphics g)
	{	
		g.setColor(Color.white);
		
		for (int i = 0; i <= 99; i++)
		{
			g.fillRect(starX[i], starY[i], 4, 4);
		}
	}
	
}
