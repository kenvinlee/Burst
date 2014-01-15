import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.SwingUtilities.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Balloons extends Thread
{
	private int x, y, scaleY, width;
	private int positioning, positionMod, scaleLimit;
	private int image;
	private int originalY;
	private float yShift;
	private int scale;
	private int spawnRate, spawnCount;
	private int burstTimer;
	private boolean burst, hasStepped;
	private BufferedImage[] yellow, green, orange, pink;
	private Random r;
	private Rectangle encasedNeutralBalloon, encasedStepBalloon;
	private Physics p;

	/*largest -> smallest
	 * 4) Green
	 * 3) Pink
	 * 2) Orange
	 * 1) Yellow
	 * 
	 */

	public void init()
	{
		p = new Physics (x, y);
		yShift = p.jump();

		//balloon initiations
		yellow = new BufferedImage[4];
		green = new BufferedImage[4];
		pink = new BufferedImage[4];
		orange = new BufferedImage[4];	

		
		//green working
		//orange working
		try{
			pink[0]= ImageIO.read(new File("pink balloon/pBalloon.gif"));
			pink[1]= ImageIO.read(new File("pink balloon/pBalloon 2.gif"));
			pink[2]= ImageIO.read(new File("pink balloon/pBalloon 3.gif"));
			pink[3]= ImageIO.read(new File("pink balloon/pBalloon 4.gif"));		

			green[0]= ImageIO.read(new File("green balloon/gBalloon.gif"));
			green[1]= ImageIO.read(new File("green balloon/gBalloon 2.gif"));
			green[2]= ImageIO.read(new File("green balloon/gBalloon 3.gif"));
			green[3]= ImageIO.read(new File("green balloon/gBalloon 4.gif"));

			orange[0]= ImageIO.read(new File("orange balloon/oBalloon.gif"));
			orange[1]= ImageIO.read(new File("orange balloon/oBalloon 2.gif"));
			orange[2]= ImageIO.read(new File("orange balloon/oBalloon 3.gif"));
			orange[3]= ImageIO.read(new File("orange balloon/oBalloon 4.gif"));

			yellow[0]= ImageIO.read(new File("yellow balloon/yBalloon.gif"));
			yellow[1]= ImageIO.read(new File("yellow balloon/yBalloon 2.gif"));
			yellow[2]= ImageIO.read(new File("yellow balloon/yBalloon 3.gif"));
			yellow[3]= ImageIO.read(new File("yellow balloon/yBalloon 4.gif"));

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//constructor
	public Balloons(JFrame F,int start, int mod)
	{
		init();
		r = new Random();
		image = 0;
		burstTimer = 1000;
		spawnRate = 1000;
		positionMod = mod;
		positioning = start;
		scaleLimit = 4;
		
		//initial spawning
		if (spawnCount <=10)
		{
			y = (r.nextInt(positioning)+ positionMod);
			x = (r.nextInt(500));
			scale = (r.nextInt(scaleLimit) + 1);
		}
		
		//I know there's a repeat of this code, but I kept getting a nullpointer without
		//this here
		if (scale == 1)
		{
			scaleY = y+1;
			width = 51;
			encasedNeutralBalloon = new Rectangle(x+8, scaleY, 32, 5);
			encasedStepBalloon = new Rectangle(x+10, y+11, 51, 5);
			burstTimer *= 2;
		}
		else if (scale == 2)
		{
			scaleY = y + 3;
			width = 72;
			encasedNeutralBalloon = new Rectangle(x+13, scaleY, 45, 10);
			encasedStepBalloon = new Rectangle(x+15, y+12, 72, 10);
			burstTimer *= 3;
		}
		else if (scale == 3)
		{
			scaleY = y + 13;
			width = 100;
			encasedNeutralBalloon = new Rectangle(x+20, scaleY, 87, 10);
			encasedStepBalloon = new Rectangle(x+33, y+27, 100, 10);
			burstTimer *= 4;
		}
		else if (scale == 4)
		{
			scaleY = y + 12;
			width = 138;
			encasedNeutralBalloon = new Rectangle(x+26, scaleY, 100, 10);
			encasedStepBalloon = new Rectangle(x+39, y+8, 138, 10);
			burstTimer *= 5;
		}		

		//for accurate balloon drawing
		originalY = y;

	}

	// moves the balloons
	public void changeY(int vel)
	{
		yShift = vel;

		y -= vel;

		if ((y -= vel) < originalY)
		{
			yShift = 0;
			y = originalY;
		}
		/*okay, uhh... You feed in d.vel. And you change shit. THis is like the update
		method, just better*/
	}

	//getters
	public int getX()
	{
		return x;
	}
	
	public int getWidth()
	{
		return width;
	}

	public int getY()
	{	
		return y;
	}

	public int getScale()
	{
		return scale;
	}
	

	public int getScaleY()
	{
		return scaleY;
	}

	public Rectangle neutral()
	{
		return encasedNeutralBalloon;
	}

	public Rectangle stepped()
	{
		return encasedStepBalloon;
	}

	public int getYCount()
	{
		return originalY;
	}
	
	//draw method
	public void draw(Graphics g)
	{
		spawnCount++;
		g.drawImage(feedImage(), getX(), getY(), null);
	}

	//determines characteristics of balloons
	public BufferedImage feedImage()
	{
		if (getScale() == 1)
		{
			scaleY = y+1;
			width = 51;
			encasedNeutralBalloon = new Rectangle(x+8, scaleY, 32, 8);
			encasedStepBalloon = new Rectangle(x+10, y+11, 51, 8);
			burstTimer *= 2;
			return yellow[image];
		}
		else if (getScale() == 2)
		{
			scaleY = y + 3;
			width = 72;
			encasedNeutralBalloon = new Rectangle(x+13, scaleY, 45, 15);
			encasedStepBalloon = new Rectangle(x+15, y+12, 72, 15);
			burstTimer *= 3;
			return orange[image];
		}
		else if (getScale() == 3)
		{
			scaleY = y + 13;
			width = 100;
			encasedNeutralBalloon = new Rectangle(x+20, scaleY, 87, 20);
			encasedStepBalloon = new Rectangle(x+33, y+27, 100, 20);
			burstTimer *= 4;
			return pink[image];
		}
		else if (getScale() == 4)
		{
			scaleY = y + 12;
			width = 148;
			encasedNeutralBalloon = new Rectangle(x+26, scaleY, 100, 20);
			encasedStepBalloon = new Rectangle(x+39, y+8, 148, 20);
			burstTimer *= 5;
			return green[image];
		}		
		else
			return null;
	}

	//limits the scale of the balloons to increase difficulty at higher levels
	public void changeLimit(int limiter)
	{
			scaleLimit = limiter;
	}
	
	//changes the image when the balloon is stepped on
	public void imageControl()
	{
		image += 1;
		
		if ((image += 1) > 1)
			image = 1;
	}
	
	//recycles the balloon.
	public void respawn()
	{
		y = (r.nextInt(300));
		x = (r.nextInt(500));
		scale = (r.nextInt(scaleLimit) + 1);
		image = 0;
		
		originalY = y;
	}
	
}

