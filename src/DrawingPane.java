import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


public class DrawingPane extends JPanel
{
	private BufferedImage background;
	private JFrame f;
	private int backgroundY1, backgroundY2, backgroundShift, animationCount;
	private int memory;
	private int level;
	private int score, backgroundScore;
	private int trace;
	private int scoreGroundY2;
	private int scoreShift;
	private boolean collideBalloon;
	private BufferedImage start;
	private BufferedImage[] left, right, jumpLeft, jumpRight;
	private BufferedImage backgroundMenu, pic1, pic2, pic3;
	private final int UP = 3, DOWN = 2, RIGHT = 0, LEFT = 1, FALSE = 4;
	private ArrayList<Balloons> balloons;
	private Derbe d;
	private Balloons b,b1,b2,b3,b4,b5,b6,b7;
	private MenU m;
	private Scenery s;
	
	//constructor
	public DrawingPane(JFrame f)
	{
		super();
		this.f = f;
		this.setDoubleBuffered(true);
		this.setIgnoreRepaint(true);
		init();
	}

	//initializer
	public void init()
	{
		m = new MenU(f);
		d = new Derbe(f);
		s = new Scenery(f);
		level = 0;
		score = 0;
		collideBalloon = false;
		scoreGroundY2 = 5235;

		//balloon initializations
		balloons = new ArrayList<Balloons>();
		b = new Balloons(f, 140, 0);
		b1 = new Balloons(f, 140, 140);
		b2 = new Balloons(f, 140, 280);
		b3 = new Balloons(f, 140, 420);
		b4 = new Balloons(f, 90, 560);
		b5 = new Balloons(f, 180, 0);
		b6 = new Balloons(f, 180, 140);
		b7 = new Balloons(f, 180, 280);
		balloons.add(b);
		balloons.add(b1);
		balloons.add(b2);
		balloons.add(b3);
		balloons.add(b4);
		balloons.add(b5);
		balloons.add(b6);
		balloons.add(b7);


		//background and player initializations
		backgroundY1 = 4435;
		backgroundY2 = 5235;
		backgroundShift = 0;
		animationCount = 0;

		jumpLeft = new BufferedImage[2];
		jumpRight = new BufferedImage[2];
		right = new BufferedImage[3];
		left = new BufferedImage[3];

		try {
			background = ImageIO.read(new File("full background.jpg"));

			start = ImageIO.read(new File("Derbe front.gif"));

			jumpLeft[0] = ImageIO.read(new File("derbe jumps/pre left jump.gif"));
			jumpLeft[1] = ImageIO.read(new File("derbe jumps/left jump.gif"));

			jumpRight[0] = ImageIO.read(new File("derbe jumps/pre right jump.gif"));
			jumpRight[1] = ImageIO.read(new File("derbe jumps/right jump.gif"));

			left[0] = ImageIO.read(new File("derbe facing left/Derbe left still.gif"));
			left[1] = ImageIO.read(new File("derbe facing left/Derbe right foot.gif"));
			left[2] = ImageIO.read(new File("derbe facing left/Derbe left foot.gif"));

			right[0] = ImageIO.read(new File("derbe facing right/Derbe right still.gif"));
			right[1] = ImageIO.read(new File("derbe facing right/Derbe right foot.gif"));
			right[2] = ImageIO.read(new File("derbe facing right/Derbe left foot.gif"));

			//menu images
			backgroundMenu = ImageIO.read(new File("final.jpg"));
			pic1 = ImageIO.read(new File("Derbe front.gif"));
			pic2 = ImageIO.read(new File("oBalloon.gif"));
			pic3 = ImageIO.read(new File("keys.jpg"));


		} catch (IOException e) {	  
			e.printStackTrace();
		}

	}

	//main paint method
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		Font font1 = new Font("Segoe Print", Font.BOLD,  14);
		Font font2 = new Font("Calibri", Font.BOLD, 24);
		Font font3 = new Font("Calibri", Font.BOLD, 14);
		Font font4 = new Font("Bauhaus 93", Font.BOLD, 18);

		//just the starting screen
		if (m.getMenuOption() != 1)
		{
			g2.drawImage(backgroundMenu, 0, 350, null);
			g2.setColor(Color.green);
			g2.fillRect(490, 350, 88, 130);
			g2.setColor(Color.YELLOW);
			g2.fillRect(490, 480, 88, 130);
			g2.setColor(Color.RED);
			g2.fillRect(490, 610, 88, 130);
			g2.setColor(Color.black);
			g2.setFont(font2);
			g2.drawString("Play", 510, 420);
			g2.drawString("Credits", 498, 680);
			g2.setFont(font3);
			g2.drawString("Instructions", 495, 550);

		}

		//draws the instructions for the game
		if (m.getMenuOption() == 2)
		{
			g2.setFont(font1);
			g2.drawString("You are Derbe (the cute little character beside).", 30, 45);
			g2.drawString("The point of the game is to jump from balloon to balloon to see how high", 30, 105);
			g2.drawString("you can get. The higher you get the higher your score will be. As you get", 30, 120); 
			g2.drawString("higher the balloons will get progressively smaller. They will also become", 30, 135);
			g2.drawString("fewer and farther between. The balloons look like the example below.", 30, 150);
			g2.drawString("To move Derbe use the arrows keys found on the keyboard. The up arrow", 30, 285);
			g2.drawString("key will make Derbe jump while the left and right arrow keys will make", 30, 300);
			g2.drawString("him run left and right.", 30, 315);
			g2.drawImage(pic2, 30, 155, null);
			g2.drawImage(pic1, 370, 15, null);
			g2.drawImage(pic3, 370, 155, null);
		}

		//draws the credits for the game
		else if (m.getMenuOption() == 3)
		{
			g.setFont(font1);
			g.drawString("Kevin Lee - Inventor of the idea for a balloon platform game and", 30, 45);
			g.drawString("creator of most of the code contained in Burst.", 119, 60);
			g.drawString("Elisa Almeida - Drawing and editing sprites and helped with some", 30, 75);
			g.drawString("areas of the code.", 148, 90);
			g.drawString("Tal Friedman & Nikita Leonidov - Contributors to development of", 30, 105);
			g.drawString("the code.", 280, 120);
		}

		//starts the game
		if (m.getMenuOption() == 1)
		{
			//some scenery stuff
			if (level == 2 || level == 3)
			{
				s.render(g2);
			}
			
			//drawing the background
			g2.drawImage(background, 0, -110, 578, 1000, 0, backgroundY1, 578, backgroundY2, null);
			g2.setFont(font4);
			g2.drawString(score + "", 0, 15);
			
			//balloons
			spawn(g);

			//player drawings
			//when he's landed on something
			if (d.getDirection() == FALSE && (d.collisionGround() == true || d.getCollide() == true))
				g2.drawImage(start, d.getX(), d.getY(), null);

			if (d.getDirection() == LEFT && (d.collisionGround() == true|| d.getCollide() == true))
			{
				for (int i = 0; i < left.length; i++)
				{
					g2.drawImage(left[i], d.getX(), d.getY(), null);
				}
				memory = LEFT;
			}

			if (d.getDirection() == RIGHT && (d.collisionGround() == true || d.getCollide() == true))
			{
				for (int i = 0; i < right.length; i++)
				{
					g2.drawImage(right[i], d.getX(), d.getY(), null);
				}
				memory = RIGHT;
			}
			
			//for visual consistency
			if (d.getDirection() == RIGHT)
				memory = RIGHT;

			if (d.getDirection() == LEFT)
				memory = LEFT;

			//for jumping
			if (d.collisionGround() == false && d.getCollide() == false && memory == LEFT)
			{
				for (int i = 0; i < jumpLeft.length; i ++)
					g2.drawImage(jumpLeft[i], d.getX(), d.getY(), null);
			}

			if (d.collisionGround() == false && d.getCollide() == false && memory == RIGHT)
			{
				for (int i = 0; i < jumpRight.length; i ++)
					g2.drawImage(jumpRight[i], d.getX(), d.getY(), null);
			}

			//if game is over
			if (d.gameOver() == true)
			{
				g2.setColor(Color.GRAY);
				g2.fillRect(145, 250, 289, 250);
				g2.setColor(Color.BLACK);
				g2.drawRect(145, 250, 289, 250);
				g2.setFont(font4);
				g2.drawString("Game Over", 235, 350);
				g2.drawString(score+"", 277, 375);
				g2.drawString("Press space to try again", 190, 420);
				level = 1;
			}
		}
	}

	//method to spawn the balloons
	public void spawn(Graphics g)
	{
		for (int i = 0; i < balloons.size(); i++)
		{
			balloons.get(i).draw(g);

			//respawn conditions. made different so balloons will spawn somewhat usefully
			if (balloons.get(i).getY() > 700 && balloons.get(i).getScale() == 4)
			{
				balloons.get(i).respawn();
			}
			if (balloons.get(i).getY() > 680 && balloons.get(i).getScale() == 3)
			{
				balloons.get(i).respawn();
			}
			if (balloons.get(i).getY() > 650 && balloons.get(i).getScale() == 2 && backgroundY1 < 4235)
			{
				balloons.get(i).respawn();
			}
			if (balloons.get(i).getY() > 620 && balloons.get(i).getScale() == 1 && backgroundY1 < 4235)
			{
				balloons.get(i).respawn();
			}
		}

	}

	//changes the background. also calculates score.
	public void backgroundChange()
	{
		trace = 5235;
		
		if (d.collisionGround() == false)
		{
			//calculates the score and a background score for level settings
			if (d.getReset() == false)
			{
				scoreShift = (int)d.getVel();
				scoreGroundY2 += scoreShift;
			}

			if (Math.abs(scoreGroundY2 - trace) > score)
			{
				score = Math.abs(scoreGroundY2 - trace);
			}

			backgroundScore = Math.abs(scoreGroundY2 - trace);
			
			//scrolls through the background
			backgroundShift = (int)d.getVel();

			backgroundY1 += backgroundShift;
			backgroundY2 += backgroundShift; 

			//edge case
			if ((backgroundY1 += backgroundShift) > 4435 && 
					(backgroundY2 += backgroundShift) > 5235)
			{
				backgroundShift = 0;

				backgroundY1 = 4435;
				backgroundY2 = 5235;

				d.atEdge();

			}

			checkLevels();
		}	
	}
	
	//checks for the levels and loops background so the background doesn't run out
	public void checkLevels()
	{	
		if (level == 1)
		{
			if ((backgroundY1) < 3653)
			{
				backgroundShift = 0;
				
				backgroundY1 = 3583;
				backgroundY2 = 4515;
				}

		}	
		else if (level == 2)
		{
			if ((backgroundY1) < 1896)
			{
				backgroundShift = 0;
				
				backgroundY1 = 1896;
				backgroundY2 = 2828;
			}

		}
		else if (level == 3)
		{
			if ((backgroundY1) < 364)
			{
				backgroundShift = 0;
				
				backgroundY1 = 364;
				backgroundY2 = 1337;
			}
		}
	}
	
	//updates balloon coordinates so it draws right
	public void balloonUpdate(Balloons balloon)
	{	
		balloon.changeY((int) d.getVel());
	}

	//checks if the player walks off a balloon. is very faulty.
	public void balloonRecheck(int bx1, int bx2)
	{
		if (d.getX() < bx1)
			d.collideMutate(false);
		if (d.getX() > (bx1 + bx2))
			d.collideMutate(false);
		if (d.getX() < (bx1 + bx2) && d.getX() > bx1)
			d.collideMutate(true);
	}

	//update method, enough said.
	public void update()
	{
		//draws the balloons in relation to the background
		if (d.collisionGround() == false)
		{
			for (int i = 0; i < balloons.size(); i++)
			{
				balloonUpdate(balloons.get(i));
			}
		}

		//handles the balloon stuff
		for (int i = 0; i < balloons.size(); i++)
		{
			if (d.collisionBalloon(balloons.get(i).stepped()) == true)
			{
				balloons.get(i).imageControl();
				d.balloonCollisionHandle(balloons.get(i).getScaleY());
				balloonRecheck(balloons.get(i).getX(), balloons.get(i).getWidth());
			}
		}
		d.update();
		d.checkStart(m.getMenuOption());
		backgroundChange();
		this.repaint();

		//check if player chose to play again
		if (d.getReset() == true)
		{
			init();
			d.hasReset();
		}

		//checks for and sets the levels
		if (backgroundScore >= 500)
		{
			level = 1;
			for (int i = 0; i < balloons.size(); i++)
			{
				balloons.get(i).changeLimit(3);
			}

			balloons.remove(b);
		}
		if (backgroundScore >= 5000)
		{
			level = 2;
			for (int i = 0; i < balloons.size(); i++)
			{
				balloons.get(i).changeLimit(2);
			}
			balloons.remove(b2);
		}
		if (backgroundScore >= 15000)		
		{
			level = 3;
			for (int i = 0; i < balloons.size(); i++)
			{
				balloons.get(i).changeLimit(1);
			}
			balloons.remove(b4);

		}
		if (backgroundScore < 500)
			level = 0;

	}

}
