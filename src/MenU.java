import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.font.*;

//menu class
public class MenU extends JFrame implements MouseListener
{
	private int x,y;
	private int menuOption;
	boolean gameStart;

	//constructor
	public MenU(JFrame j)
	{
		menuOption = 0;
		j.addMouseListener(this);
	}

	//getter for which menu option was selected
	public int getMenuOption()
	{
		return menuOption;
	}
	
	//mouse listener stuff.
	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		if (menuOption != 1)
		{
			if (x > 490 && x < 578 && y > 378 && y < 500)
			{
				menuOption = 1;
			}
			else if (x > 490 && x < 578 && y > 490 && y <= 638)
			{
				menuOption = 2;
			}
			else if (x > 490 && x < 578 && y >= 639 && y < 740)
			{
				menuOption = 3;
			}
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}
	
	public void update()
	{
		getMenuOption();
	}
	
	class Drawing extends JComponent
	{

		public Drawing()
		{
		}

	}

}