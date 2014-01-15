/*Burst
 *Created by Elisa Almeida and Kevin Lee
 *version 0.9 (not even release yet :P)
 *June 6th, 2011
 *Further info in credits
 */

import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;

/* LIST OF KNOWN BUGS
 * 
 * 1)Superjump caused by holding the up button and pushing either left or right
 * 2)Inability to walk off a balloon once every while
 * 3)Balloons don't burst. I know. Sorry. That was a bit above my capabilities.
 * 4)Imperfect collision detection
 */

public class Main extends JFrame {

	static DrawingPane dPane;

	public Main() {

		super("Burst");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(578, 1000);
		setVisible(true);

	}


	public static void main(String[] args) {
		Main main = new Main();
		dPane = new DrawingPane(main);

		main.add(dPane);
		main.setResizable(false);
		class MyAbstractAction extends AbstractAction
		{
			public void actionPerformed(ActionEvent e)
			{
				dPane.update();
			}
		}
		Action updateActionListener = new MyAbstractAction();
		//Timer tick spacing determines FPS of game
		Timer timer = new Timer(42, updateActionListener);
		timer.start();
	}
}