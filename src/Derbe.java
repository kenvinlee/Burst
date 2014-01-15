import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.*;


public class Derbe implements KeyListener
{
	private int x, y, yTemp;
	private int score;
	private boolean[] direction;
	private final int UP = 3, DOWN = 2, RIGHT = 0, LEFT = 1, NODIRECTION = 4, DELAY = 5;
	private final int SPEED = 15;
	private boolean collide, jumpCheck, gameOver, reset, hasStart;
	private float vel = 0;
	private Rectangle landingBox;
	private Physics p;

	//constructor
	public Derbe (JFrame f)
	{
		f.addKeyListener(this);
		x = 267;
		y = 700;
		score = 0;
		gameOver = false;
		p = new Physics (x, y);
		direction = new boolean[4];
		
	}


	// bunch of getters for animation and drawings...
	public int getDirection()
	{
		int iAmLazy = -1;
		for (int i = 0; i < direction.length; i++)
		{
			if (direction[i] == true)
				iAmLazy = i;
			if (direction[0] == false && direction[1] == false && direction[2] == false && 
					direction[3] == false)
			{
				iAmLazy = NODIRECTION;
			}
		}
		return iAmLazy;
	}

	public boolean jump()
	{
		return jumpCheck;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public float getVel()
	{
		return vel;
	}
	
	public boolean gameOver()
	{
		return gameOver;
	}

	public boolean getReset()
	{
		return reset;
	}
	
	public void hasReset()
	{
		reset = false;
	}
	
	public boolean getCollide()
	{
		return collide;
	}

	//movement methods and collision detection + handling


	public void sideMove(int direction, int speed)
	{
		if(direction == RIGHT)
		{
			x += speed;
			if (x >= 608)
				x = -30;
		}

		if(direction == LEFT)
		{
			x -= speed;
			if (x < -30)
				x = 607;
		}

		if (direction == UP)
		{
			jumpCheck = true;
			jump(jumpCheck);
		}
	}

	//checks if player jumped
	public void jump(boolean check)
	{
		if (check == true)
			vel = p.jump();
		collide = false;
		
	}

	/*balloon collisions... faulty as hell
	 */
	public boolean collisionBalloon(Rectangle balloon)
	{
		if (getDirection() == LEFT)
			landingBox = new Rectangle(x+18, y+47, 8, 8);
		if (getDirection() == RIGHT)
			landingBox = new Rectangle(x+7, y+46, 8, 8);
		if (getDirection() == NODIRECTION)
			landingBox = new Rectangle(x+7, y+44, 19, 8);
		
		return (landingBox.intersects(balloon));
	}
	
	//deals with collide boolean to better manage balloon collisions. doesn't quite
	//work as well as I want it to.
	public void collideMutate(boolean change)
	{
		collide = change;
	}
	
	//handles balloon collision. does not work perfectly
	public void balloonCollisionHandle(int newY)
	{
		
		vel = newY - 51;
		
		collide = true;
		vel = 0;
		jumpCheck = false;	
	}

	//checks for collision with ground. works perfectly
	public boolean collisionGround()
	{
		Rectangle derbe = new Rectangle(x, y, 30, 50);
		Rectangle ground = new Rectangle(-1000, 740, 2000, 1000);

		return (derbe.intersects(ground));
	}
	
	//checks if game has started so to enable/disable controls
	public void checkStart(int menu)
	{
		if (menu != 1)
		{
			hasStart = false;
		}
		else
		{
			hasStart = true;
		}
	}
	
	//edge case
	public void atEdge()
	{
		y += vel;
		gameOver = true;
		
	}

	//update method
	public void update()
	{
		//direction
		for(int i = 0; i < 4; i++)
		{
			if(direction[i])
			{
				sideMove(i, SPEED);
			}
		}
		
		//jump velocity management
		if (y > 350)		
			y += vel;
		
		if (collisionGround() == false && collide == false)
		{
			vel += p.gravity();
		}
		else if (collisionGround() == true)
		{
			vel = 700 - y;
		}
		if (vel > 0)
		{
			jumpCheck = false;
		}
	}
	@Override
	//methods for key input
	public void keyPressed(KeyEvent e) {
		if (gameOver == false && hasStart == true)
		{		
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				if (jumpCheck == false && collisionGround() == true || collide == true)
					direction[UP] = true; 
				else direction[UP] = false; break;
			case KeyEvent.VK_LEFT:
				direction[LEFT] = true; break;
			case KeyEvent.VK_RIGHT:
				direction[RIGHT] = true; break;
			}
		}
		if (gameOver == true)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_SPACE:
				gameOver = false; 
				reset = true; break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			direction[UP] = false; break;
		case KeyEvent.VK_LEFT:
			direction[LEFT] = false; break;
		case KeyEvent.VK_RIGHT:
			direction[RIGHT] = false; break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
